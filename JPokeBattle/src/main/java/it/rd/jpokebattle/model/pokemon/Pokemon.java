package it.rd.jpokebattle.model.pokemon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;

import static it.rd.jpokebattle.model.pokemon.Stats.*;

public class Pokemon implements Serializable {
    protected HashMap<String, Integer> moveSet = new HashMap<>();
    protected HashMap<Stats, Integer> ivMap = PokemonManager.getVoidStatsMap();
    protected HashMap<Stats, Integer> evMap = PokemonManager.getVoidStatsMap();
    protected HashMap<Stats, Integer> statsMap = PokemonManager.getVoidStatsMap();
    protected Breed breed;
    protected String name;
    protected int currLevel;
    protected int currHP;

    protected Pokemon() {}

    public Pokemon(Breed breed, int lv) {
        this.breed = breed;
        this.name = breed.getName();
        this.currLevel = lv;

        generateRandomIVs();
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
    }


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


    private void generateRandomIVs() {
        Random rand = new Random();

        for (Stats stat : ivMap.keySet()) {
            ivMap.replace(stat, rand.nextInt(0, 15));
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

            if (stat.equals(HP))
                newValue += currLevel + 5;

            statsMap.replace(stat, newValue);

            System.out.println(newValue);
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
