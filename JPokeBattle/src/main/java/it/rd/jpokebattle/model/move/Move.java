package it.rd.jpokebattle.model.move;

import it.rd.jpokebattle.model.pokemon.Type;
import it.rd.jpokebattle.util.file.DataMapLoader;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Move implements Serializable {
    public static HashMap<String, Move> movesMap = DataMapLoader.loadMap("json.move", Move.class);
    private String name;
    private String description;
    private String type;
    private String category;
    private HashMap<String, Double> opponentStats;
    private HashMap<String, Double> userStats;
    private String specialEffect;
    private int power;
    private int priority;
    private double precision;
    private int pp;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Type getType() {
        return Type.fromName(type);
    }

    public String getCategory() {
        return category;
    }

    public HashMap<String, Double> getOpponentStats() {
        return opponentStats;
    }

    public HashMap<String, Double> getUserStats() {
        return userStats;
    }

    public MoveSpecialEffect getSpecialEffect() {
        return MoveSpecialEffect.fromName(specialEffect);
    }


    public int getPower() {
        return power;
    }

    public int getPriority() {
        return priority;
    }

    public double getPrecision() {
        return precision;
    }

    public int getPP() {
        return pp;
    }

    public static Move fromName(String name) {
        return movesMap.get(name);
    }
}
