package it.rd.jpokebattle.model.profile;

import it.rd.jpokebattle.model.area.Area;
import javafx.scene.image.Image;

import java.io.Serializable;


/**
 * I profili consentono all'utente di interfacciarsi con il gioco. Grazie alle istanze di
 * questa classe si può memorizzare sempre lo stato corrente del gioco, sia per il pvp che
 * per l'arcade.
 */
public class Profile implements Serializable {
    public final int ID;
    private String name;
    private String avatarSrcName;
    private String currentArea;
    private String narratorTextHistory;
    /*
    private HashMap<String, Integer> itemsMap;
    private ArrayList<String> pkmnTeam;
    private ArrayList<String> pkmnStorage;
    */


    protected Profile(int id, String name, Image avatar) {
        this.ID = id;
        this.name = name;
        this.currentArea = "start";
        this.narratorTextHistory = getCurrentArea().getDescription();
//        this.currentArea = Area.START.NAME_ID;
//        this.narratorTextHistory = Area.START.getDescription();
//        setItemsMap();
        setAvatarSrcName(avatar.getUrl());
    }


    public String getAvatarSrcName() {
        return avatarSrcName;
    }

    public Area getCurrentArea() {
        return Area.fromName(currentArea);
    }

    public String getName() {
        return name;
    }

    public String getNarratorTextHistory() {
        return narratorTextHistory;
    }
/*
    public int getItemCounter(String itemName) {
        return itemsMap.getOrDefault(itemName, 0);
    }

 */

    public void setAvatarSrcName(String avatarPath) {
        String[] path = avatarPath.split("jpokebattle/images/avatar/");
        this.avatarSrcName = path[1];
    }

    public void setCurrentArea(String areaName) {
        this.currentArea = areaName;
    }

    /*
    private void setItemsMap() {
        this.itemsMap.put("potion", 5);
        this.itemsMap.put("pokeball", 5);
    }

     */

    public void setName(String name) {
        this.name = name;
    }

    public void setNarratorTextHistory(String narratorTextHistory) {
        this.narratorTextHistory = narratorTextHistory;
    }

    /*
    public void addToItemCounter(String itemName, int valueToAdd) {
        int total = getItemCounter(itemName) + valueToAdd;

        if (total < 0 || total > 99) {  // TODO DA GESTIRE IN LOGICA
            System.out.println("\nVALORE NON VALIDO, SUPERIORE A 100 O INFERIORE A 0: " + total);
        } else {
            itemsMap.replace(itemName, total);
        }
    }

     */

    /**
     * Viene impostata l'area corrente al nome dell'area successiva.
     *
     * @see Area
     */
    public void goToNextArea() {
        setCurrentArea(Area.fromName(this.currentArea).getNextAreaName());
    }

    /**
     * Viene impostata l'area corrente al nome dell'area successiva.
     *
     * @see Area
     */
    public void goToPrevArea() {
        setCurrentArea(Area.fromName(this.currentArea).getPrevAreaName());
    }

    /**
     * Viene impostata l'area corrente al nome dell'area successiva.
     *
     * @see Area
     */
    public void goToSpecialArea() {
        setCurrentArea(Area.fromName(this.currentArea).getSpecialAreaName());
    }


}



/*
    private HashMap<String, Boolean> visitedAreas;


    private void setVisitedAreas() {
        for(Area area : Area.values()) {
            visitedAreas.put(area.getName(), false);
        }
    }

    public void setVisited(Area area) {
        if (!isVisited(area)) {
            visitedAreas.replace(area.getName(), true);
            save();
        }
    }

    public boolean isVisited(Area area) {
        return visitedAreas.get(area.getName());
    }
*/