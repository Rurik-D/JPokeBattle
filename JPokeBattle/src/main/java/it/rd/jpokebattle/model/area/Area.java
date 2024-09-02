package it.rd.jpokebattle.model.area;

import it.rd.jpokebattle.util.file.DataMapLoader;

import java.util.HashMap;

public class Area {
    private static final HashMap<String, Area> AREA_MAP = DataMapLoader.loadMap("json.area", Area.class);
    private final String title;
    private final String description;
    private final String musicSrcName;
    private final String imageSrcName;
    private final String nextAreaName;            // nome dell'area successiva
    private final String nextAreaButtonName;      // nome del bottone che consente il passaggio all'area successiva
    private final String prevAreaName;            // nome dell'area precedente (se esiste)
    private final String prevAreaButtonName;      // nome del bottone che consente il passaggio all'area precedente (se esiste)
    private final String specialAreaName;         // nome dell'area speciale (se esiste)
    private final String specialAreaButtonName;   // nome del bottone che consente il passaggio all'area precedente (se esiste)
    private final String areaType;


    private Area(String title, String description, String musicSrcName, String imageSrcName, String nextAreaName,
                 String nextAreaButtonName, String prevAreaName, String prevAreaButtonName, String specialAreaName,
                 String specialAreaButtonName, String areaType) {
        this.title = title;
        this.description = description;
        this.musicSrcName =  musicSrcName;
        this.imageSrcName = imageSrcName;
        this.nextAreaName = nextAreaName;
        this.nextAreaButtonName = nextAreaButtonName;
        this.prevAreaName = prevAreaName;
        this.prevAreaButtonName = prevAreaButtonName;
        this.specialAreaName = specialAreaName;
        this.specialAreaButtonName = specialAreaButtonName;
        this.areaType = areaType;
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

    public String getNextAreaName() {
        return this.nextAreaName;
    }

    public String getPrevAreaName() {
        return this.prevAreaName;
    }

    public String getSpecialAreaName() {
        return this.specialAreaName;
    }

    public String getNextAreaButtonName() {
        return this.nextAreaButtonName;
    }

    public String getPrevAreaButtonName() {
        return this.prevAreaButtonName;
    }

    public String getSpecialAreaButtonName() {
        return this.specialAreaButtonName;
    }

    public AreaType getAreaType() {
        return AreaType.fromName(areaType);
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
