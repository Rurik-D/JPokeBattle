package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.util.file.DataMapLoader;

import java.util.HashMap;

public class Breed {
    private static final HashMap<String, Breed> AREA_MAP = DataMapLoader.loadMap("json.breed", Breed.class);
    private String firstTypeName;
    private String secondTypeName;
    private String predBreedName;
    private String succBreedName;
    private int nextEvoThreshold;
    private int baseHP;
    private int baseAtk;
    private int baseDef;
    private int baseSpecAtk;
    private int baseSpecDef;
    private int baseSpeed;


    public int baseValueOf(Stats stat) {
        return switch (stat) {
            case Stats.HP -> baseHP;
            case Stats.ATK -> baseAtk;
            case Stats.DEF -> baseDef;
            case Stats.SPEC_ATK -> baseSpecAtk;
            case Stats.SPEC_DEF -> baseSpecDef;
            case Stats.SPEED -> baseSpeed;
        };
    }

    public int getNextEvoThreshold() {
        return nextEvoThreshold;
    }

    public String getSuccBreedName() {
        return succBreedName;
    }

    public static Breed fromName(String breedName) {
        return AREA_MAP.getOrDefault(breedName, null);
    }

    @Override
    public String toString() {
        return  "\nfirstType: " + firstTypeName +
                "\nsecondType: " + secondTypeName +
                "\npredBreedName: " + predBreedName +
                "\nsuccBreedName: " + succBreedName +
                "\nnextEvoThreshold: " + nextEvoThreshold +
                "\nbaseHP: " + baseHP +
                "\nbaseAtk: " + baseAtk +
                "\nbaseDef: " + baseDef +
                "\nbaseSpecAtk: " + baseSpecAtk +
                "\nbaseSpecDef: " + baseSpecDef +
                "\nbaseSpeed: " + baseSpeed;
    }
}
