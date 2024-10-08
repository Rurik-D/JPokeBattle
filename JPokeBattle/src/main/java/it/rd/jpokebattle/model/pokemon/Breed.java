package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.util.file.DataMapLoader;
import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
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
    private int baseXp;
    private HashMap<String, Integer> movesPerLevel;


    public int baseValueOf(Stats stat) {
        return switch (stat) {
            case HP -> baseHP;
            case ATK -> baseAtk;
            case DEF -> baseDef;
            case SPEC_ATK -> baseSpecAtk;
            case SPEC_DEF -> baseSpecDef;
            case SPEED -> baseSpeed;
            case XP -> baseXp;
            default -> 100;
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

    public String getFirstTypeName() {
        return firstTypeName;
    }

    public String getSecondTypeName() {
        return secondTypeName;
    }

    public HashMap<String, Integer> getMovesPerLevel() {
        return movesPerLevel;
    }

    /**
     * Dato il livello di un pokemon di una certa razza, viene restituita, se esiste la
     * mossa imparata a quel livello. Restituisce null se tale mossa non esiste
     *
     * @param level Livello del pokemon
     * @return Nome della mossa
     */
    public Move getMoveFromLevel(int level) {
        for (String move : movesPerLevel.keySet()) {
            if (movesPerLevel.get(move) == level)
                return Move.fromName(move);
        }
        return null;
    }

    public ArrayList<Move> getMovesBelowLevel(int level) {
        ArrayList<Move> moves = new ArrayList<>();

        for (String move : movesPerLevel.keySet()) {
            if (movesPerLevel.get(move) <= level)
                moves.add(Move.fromName(move));
        }

        return moves;
    }

    public static Breed fromName(String breedName) {
        return AREA_MAP.getOrDefault(breedName, null);
    }


}
