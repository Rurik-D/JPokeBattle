package it.rd.jpokebattle.model.profile;

import it.rd.jpokebattle.model.area.Area;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.Pokemon;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 *
 */
public class Profile implements Serializable {
    private final int ID;
    private String name;
    private String avatarSrcName;
    private String currentArea;
    private String narratorTextHistory;
    private ArrayList<Integer> ownedPkmns = new ArrayList<>();
    private HashMap<String, Integer> itemsMap;


    protected Profile(int id, String name, Image avatar) {
        this.ID = id;
        this.name = name;
        this.currentArea = "start";
        this.narratorTextHistory = getCurrentArea().getDescription();
//        setItemsMap();
        setAvatarSrcName(avatar.getUrl());
    }


    public String getAvatarSrcName() {
        return avatarSrcName;
    }

    public Area getCurrentArea() {
        return Area.fromName(currentArea);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getNarratorTextHistory() {
        return narratorTextHistory;
    }

    public ArrayList<Integer> getOwnedPokemons() {
        return ownedPkmns;
    }

    public void setAvatarSrcName(String avatarPath) {
        String[] path = avatarPath.split("jpokebattle/images/avatar/");
        this.avatarSrcName = path[1];
    }

    public void setCurrentArea(String areaName) {
        this.currentArea = areaName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNarratorTextHistory(String narratorTextHistory) {
        this.narratorTextHistory = narratorTextHistory;
    }

    public void addToOwned(OwnedPokemon pkmn) {
        ownedPkmns.add(pkmn.getID());
    }

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
    public int getItemCounter(String itemName) {
        return itemsMap.getOrDefault(itemName, 0);
    }

 */



    /*
    private void setItemsMap() {
        this.itemsMap.put("potion", 5);
        this.itemsMap.put("pokeball", 5);
    }

     */

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

