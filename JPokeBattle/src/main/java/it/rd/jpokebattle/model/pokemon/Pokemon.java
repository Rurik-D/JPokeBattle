package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.model.move.Move;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static it.rd.jpokebattle.model.pokemon.Stats.*;

public class Pokemon implements Serializable {
    protected HashMap<String, Integer> moveSet = new HashMap<>();
    protected HashMap<Stats, Integer> ivMap = PokemonManager.getRandomIVsMap();
    protected HashMap<Stats, Integer> evMap = PokemonManager.getVoidStatsMap();
    protected HashMap<Stats, Integer> statsMap = PokemonManager.getVoidStatsMap();
    protected ArrayList<Move> moves = new ArrayList<>(4);
    protected ArrayList<Integer> PPs = new ArrayList<>(4);
    protected Breed breed;
    protected String name;
    protected int currLevel;
    protected int currHP;

    protected transient IntegerProperty currLevelProperty;
    protected transient IntegerProperty currHPProperty;
    protected transient IntegerProperty maxHPProperty;



    public Pokemon(Breed breed, int lv) {
        this.breed = breed;
        this.name = breed.getName();
        refreshProperties();
        setCurrLevel(lv);
        updateStats();
        setCurrHP(getMaxHP());
    }

    public Pokemon(Pokemon pkmn) {
        this.breed = pkmn.getBreed();
        this.name = breed.getName();
        this.moveSet = pkmn.getMoveSet();
        this.ivMap = pkmn.getIVMap();
        this.evMap = pkmn.getEVMap();
        this.statsMap = pkmn.getStatsMap();
        this.moves = pkmn.getMoves();
        this.PPs = pkmn.getPPs();
        this.currLevel= pkmn.getLevel();
        this.currHP= pkmn.getCurrHP();
        refreshProperties();
    }

    protected Pokemon() {}


    public Breed getBreed() {
        return breed;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {return currLevel;}

    public int getCurrHP() {
        return currHP;
    }

    public int getMaxHP() {
        return statsMap.get(HP);
    }

    public int getStat(Stats stat) {
        return statsMap.get(stat);
    }

    public HashMap<String, Integer> getMoveSet() {
        return moveSet;
    }

    public HashMap<Stats, Integer> getIVMap() {
        return ivMap;
    }

    public HashMap<Stats, Integer> getEVMap() {
        return evMap;
    }

    public HashMap<Stats, Integer> getStatsMap() {
        return statsMap;
    }

    public ArrayList<Integer> getPPs() {
        return PPs;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public Move getMove(int index) {
        return moves.get(index);
    }

    public int getPP(int index) {
        return PPs.get(index);
    }


    protected void setCurrHP(int value) {
        currHP = value;
        currHPProperty.set(value);
    }

    public void setCurrLevel(int lv) {
        currLevel = lv;
        currLevelProperty.set(lv);

    }

    public void setMove(Move move, int index) {
        assert index < 4;

        if (moves.size() > index) {
            moves.set(index, move);
            PPs.set(index, move.getPP());
        } else {
            moves.addFirst(move);
            PPs.addFirst(move.getPP());
        }
    }

    public void setMoves(ArrayList<Move> moves) {
        int size = Math.min(moves.size(), 4);
        for (int i=0; i<size; i++) {
            setMove(moves.get(i), i);
        }
    }


    public IntegerProperty currHPProperty() {
        return currHPProperty;
    }

    public IntegerProperty currLevelProperty() {
        return currLevelProperty;
    }

    public IntegerProperty maxHPProperty() {
        return maxHPProperty;
    }

    protected void refreshProperties() {
        currHPProperty = new SimpleIntegerProperty(currHP);
        currLevelProperty = new SimpleIntegerProperty(currLevel);
        maxHPProperty = new SimpleIntegerProperty(getMaxHP());
    }

    protected void updateStats(){
        int newValue;
        int baseStat;
        int iv;
        int ev;

        for (Stats stat : ivMap.keySet()) {
            baseStat = getBreed().baseValueOf(stat);
            iv = ivMap.get(stat);
            ev = evMap.get(stat);

            newValue = (int) ((baseStat + iv) * 2 +  Math.sqrt(ev) / 4 ) * currLevel / 100 + 5;

            if (stat.equals(HP)) {
                newValue += currLevel + 5;
                if (currHP > 0)
                    currHP += newValue - getStat(HP);

                maxHPProperty.set(newValue);
                currHPProperty.set(currHP);
            }

            statsMap.replace(stat, newValue);
        }
    }

    public void increaseEV(Pokemon pkmn) {
        HashMap<Stats, Integer> enemyStats = pkmn.getStatsMap();
        int value;

        for (Stats stat : evMap.keySet()) {
            value = evMap.get(stat) + enemyStats.get(stat);
            evMap.replace(stat, value);
        }
    }

    public void takeDamage(int dmg) {
        setCurrHP(Math.max(currHP - dmg, 0));
    }

    public boolean isFainted() {
        return currHP <= 0;
    }

}
