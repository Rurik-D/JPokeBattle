package it.rd.jpokebattle.model.area;

import it.rd.jpokebattle.util.file.DataMapLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe che rappresenta un'area nel mondo del gioco (modalità arcade).
 * Ogni area ha un nome identificativo, un titolo, una descrizione, collegamenti a aree successive
 * e risorse multimediali come musica e immagini.
 */
public class Area {
    /**
     * Mappa contenente tutte le aree caricate dal relativo file json.
     * Le chiavi sono i nomi delle aree e i valori sono istanze di {@link Area}.
     */
    private static HashMap<String, Area> AREA_MAP = DataMapLoader.loadMap("json.area", Area.class);
    private String nameID;
    private String title;
    private String description;
    private String musicSrcName;
    private String imageSrcName;
    private ArrayList<Map<String, String>> nextAreas;
    private String specialArea;

    /**
     * Restituisce l'ID univoco dell'area.
     *
     * @return Il nome identificativo dell'area.
     */
    public String getNameID() {
        return nameID;
    }

    /**
     * Restituisce il titolo dell'area.
     *
     * @return Il titolo dell'area.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Restituisce la descrizione dell'area.
     *
     * @return La descrizione dell'area.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Restituisce il nome della risorsa musicale associata all'area.
     *
     * @return Il nome del file musicale.
     */
    public String getMusicSrcName() {
        return musicSrcName;
    }

    /**
     * Restituisce il nome della risorsa immagine associata all'area.
     *
     * @return Il nome del file immagine.
     */
    public String getImageSrcName() {
        return imageSrcName;
    }

    /**
     * Restituisce il nome dell'area successiva in base all'indice fornito.
     *
     * @param areaIndex L'indice dell'area successiva nella lista.
     * @return Il nome dell'area successiva o una stringa vuota se l'indice è fuori limite.
     */
    public String getNextAreaName(int areaIndex) {
        if (areaIndex >= nextAreas.size())
            return "";
        return nextAreas.get(areaIndex).get("name");
    }

    /**
     * Restituisce il testo del pulsante per accedere all'area successiva
     * in base all'indice fornito.
     *
     * @param areaIndex L'indice dell'area successiva nella lista.
     * @return Il testo del pulsante o una stringa vuota se l'indice è fuori limite.
     */
    public String getNextAreaBtnText(int areaIndex) {
        if (areaIndex >= nextAreas.size())
            return "";
        return nextAreas.get(areaIndex).get("btnText");
    }

    /**
     * Restituisce il tipo dell'area successiva in base all'indice fornito.
     *
     * @param areaIndex L'indice dell'area successiva nella lista.
     * @return Un'istanza di {@link AreaType} rappresentante il tipo dell'area
     *         o null se l'indice è fuori limite.
     */
    public AreaType getNextAreaType(int areaIndex) {
        if (areaIndex >= nextAreas.size())
            return null;

        String type = nextAreas.get(areaIndex).get("type");
        return AreaType.fromName(type);
    }

    /**
     * Restituisce il nome di un'area speciale associata a quest'area.
     *
     * @return Il nome dell'area speciale.
     */
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
