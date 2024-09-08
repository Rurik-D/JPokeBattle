package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.model.move.Move;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public Pokemon(Breed breed, int lv) {
        this.breed = breed;
        this.name = breed.getName();
        this.currLevel = lv;
        updateStats();
        currHP = getStat(HP);
    }

    public Pokemon(Pokemon pkmn) {
        this.breed = pkmn.getBreed();
        this.name = breed.getName();
        this.moveSet = pkmn.getMoveSet();
        this.ivMap = pkmn.getIVMap();
        this.evMap = pkmn.getEVMap();
        this.statsMap = pkmn.getStatsMap();
        this.currLevel = pkmn.getLevel();
        this.currHP = pkmn.getStat(HP);
        this.moves = pkmn.getMoves();
        this.PPs = pkmn.getPPs();
    }


    protected Pokemon() {}


    public Breed getBreed() {
        return breed;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {return this.currLevel;}

    public int getCurrHP() {
        return currHP;
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

    protected void updateStats(){
        int newValue;
        int baseStat;
        int iv;
        int ev;

        for (Stats stat : statsMap.keySet()) {
            baseStat = getBreed().baseValueOf(stat);
            iv = ivMap.get(stat);
            ev = evMap.get(stat);

            newValue = (int) ((baseStat + iv) * 2 +  Math.sqrt(ev) / 4 ) * currLevel / 100 + 5;

            if (stat.equals(HP)) {
                newValue += currLevel + 5;
                if (currHP > 0)
                    currHP += newValue - getStat(HP);
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
        currHP = Math.max(currHP - dmg, 0);
    }

    public boolean isFainted() {
        return currHP <= 0;
    }

}
