package it.rd.jpokebattle.model.area;

import it.rd.jpokebattle.util.file.DataMapLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Area {
    private static HashMap<String, Area> AREA_MAP = DataMapLoader.loadMap("json.area", Area.class);
    private String nameID;
    private String title;
    private String description;
    private String musicSrcName;
    private String imageSrcName;
    private ArrayList<Map<String, String>> nextAreas;
    private String specialArea;


    public String getNameID() {
        return nameID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMusicSrcName() {
        return musicSrcName;
    }

    public String getImageSrcName() {
        return imageSrcName;
    }

    public String getNextAreaName(int areaIndex) {
        if (areaIndex >= nextAreas.size())
            return "";
        return nextAreas.get(areaIndex).get("name");
    }

    public String getNextAreaBtnText(int areaIndex) {
        if (areaIndex >= nextAreas.size())
            return "";
        return nextAreas.get(areaIndex).get("btnText");
    }


    public AreaType getNextAreaType(int areaIndex) {
        if (areaIndex >= nextAreas.size())
            return null;

        String type = nextAreas.get(areaIndex).get("type");
        return AreaType.fromName(type);
    }

    public String getSpecialAreaName() {
        return specialArea;
    }

    /**
     * A partire dal nome di un'area ritorna (se esiste) l'istanza dell'area con quel nome.
     *
     * @param areaName Nome dell'area di cui si vuole l'istanza
     */
    public static Area fromName(String areaName) {
        return AREA_MAP.getOrDefault(areaName, null);
    }
}
