package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.util.file.DataMapLoader;
import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.HashMap;

public class Breed implements Serializable {
    private static final HashMap<String, Breed> AREA_MAP = DataMapLoader.loadMap("json.breed", Breed.class);
    private String name;
    private String firstTypeName;
    private String secondTypeName;
    private String predBreedName;
    private String succBreedName;
    private String avatarSrcName;
    private String frontGifSrcName;
    private String backGifSrcName;
    private int nextEvoThreshold;
    private int baseHP;
    private int baseAtk;
    private int baseDef;
    private int baseSpecAtk;
    private int baseSpecDef;
    private int baseSpeed;


    public int baseValueOf(Stats stat) {
        return switch (stat) {
            case HP -> baseHP;
            case ATK -> baseAtk;
            case DEF -> baseDef;
            case SPEC_ATK -> baseSpecAtk;
            case SPEC_DEF -> baseSpecDef;
            case SPEED -> baseSpeed;
        };
    }

    public int getNextEvoThresh() {
        return nextEvoThreshold;
    }

    public String getSuccBreedName() {
        return succBreedName;
    }

    public String getName() {
        return name;
    }

    public Image getAvatar() {
        return ResourceLoader.loadImage(avatarSrcName);
    }

    public Image getFrontGif() {
        return ResourceLoader.loadImage(frontGifSrcName);
    }

    public Image getBackGif() {
        return ResourceLoader.loadImage(backGifSrcName);
    }

    public static Breed fromName(String breedName) {
        return AREA_MAP.getOrDefault(breedName, null);
    }


}


//@Override
//public String toString() {
//    return  "\nfirstType: " + firstTypeName +
//            "\nsecondType: " + secondTypeName +
//            "\npredBreedName: " + predBreedName +
//            "\nsuccBreedName: " + succBreedName +
//            "\nnextEvoThreshold: " + nextEvoThreshold +
//            "\nbaseHP: " + baseHP +
//            "\nbaseAtk: " + baseAtk +
//            "\nbaseDef: " + baseDef +
//            "\nbaseSpecAtk: " + baseSpecAtk +
//            "\nbaseSpecDef: " + baseSpecDef +
//            "\nbaseSpeed: " + baseSpeed;
//}