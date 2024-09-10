package it.rd.jpokebattle.model.profile;

import it.rd.jpokebattle.model.area.Area;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.PokemonManager;
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
    private String currentAreaName;
    private String lastSafeAreaName;
    private String narratorTextHistory;
    private ArrayList<Integer> ownedPkmnIDs = new ArrayList<>();
    private HashMap<String, Integer> itemsMap;


    protected Profile(int id, String name, Image avatar) {
        this.ID = id;
        this.name = name;
        this.currentAreaName = "start";
        this.narratorTextHistory = getCurrentArea().getDescription();
//        setItemsMap();
        setAvatarSrcName(avatar.getUrl());
    }


    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getAvatarSrcName() {
        return avatarSrcName;
    }

    public ArrayList<Integer> getOwnedPokemonIDs() {
        return ownedPkmnIDs;
    }

    public String getCurrentAreaName() {
        return currentAreaName;
    }

    public Area getCurrentArea() {
        return Area.fromName(currentAreaName);
    }

    public OwnedPokemon getFirstPokemon() {
        int id = ownedPkmnIDs.getFirst();
        return PokemonManager.getPokemonFromID(id);
    }

    public String getNarratorTextHistory() {
        return narratorTextHistory;
    }

    public void setAvatarSrcName(String avatarPath) {
        String[] path = avatarPath.split("jpokebattle/images/avatar/");
        this.avatarSrcName = path[1];
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentArea(String areaName) {
        currentAreaName = areaName;
    }

    /**
     * Viene impostata l'area corrente al nome dell'area successiva.
     *
     * @see Area
     */
    public void goToNextArea(int areaIndex) {
        lastSafeAreaName = currentAreaName;
        Area currArea = Area.fromName(currentAreaName);
        setCurrentArea(currArea.getNextAreaName(areaIndex));
    }

    public void goToSpecialArea() {
        lastSafeAreaName = currentAreaName;
        Area currArea = Area.fromName(currentAreaName);
        setCurrentArea(currArea.getSpecialAreaName());
    }

    public void goToLastSafeArea() {
        setCurrentArea(lastSafeAreaName);
    }

    public void setNarratorTextHistory(String narratorTextHistory) {
        int length = narratorTextHistory.length();
        this.narratorTextHistory = narratorTextHistory.substring(Math.max(0, length - 2000));
    }

    public void addToOwned(OwnedPokemon pkmn) {
        ownedPkmnIDs.add(pkmn.getID());
    }

    public void healTeam() {
        for (int id : ownedPkmnIDs) {
            PokemonManager.getPokemonFromID(id).heal();
        }
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

