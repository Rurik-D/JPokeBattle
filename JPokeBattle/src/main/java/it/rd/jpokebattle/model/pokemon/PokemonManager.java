package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.model.SerializableHandler;
import it.rd.jpokebattle.util.file.SerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PokemonManager implements SerializableHandler {
    private static final String SER_SRC_NAME = "ser.pkmn";
    private static HashMap<Integer, OwnedPokemon> pkmnMap;
    private static int maxID = SerializableHandler.calcMaxID(SER_SRC_NAME);

    public static void updatePkmnMap() {
        pkmnMap = SerManager.loadSER(SER_SRC_NAME);
        if (pkmnMap == null)
            pkmnMap = new HashMap<Integer, OwnedPokemon>();
    }

    public static OwnedPokemon fromID(int id) {
        return pkmnMap.get(id);
    }

    /**
     *
     */
    public static Pokemon generatePokemon(Breed breed, int lv) {
        return new Pokemon(breed, lv);
    }



    public static int getXPTreshold(int lv) {
        return (int) Math.pow(lv, 3.0);
    }


    /**
     *
     */
    public static OwnedPokemon toOwnedPokemon(Pokemon pkmn) {
        OwnedPokemon ownPkmn =  new OwnedPokemon(getNextID(), pkmn);
        pkmnMap.put(maxID, ownPkmn);
        return ownPkmn;
    }

    /**
     *
     */
    public static HashMap<Stats, Integer> getVoidStatsMap() {
        HashMap<Stats, Integer> map = new HashMap<>(
                Map.of(
                        Stats.HP, 0,
                        Stats.ATK, 0,
                        Stats.DEF, 0,
                        Stats.SPEC_ATK, 0,
                        Stats.SPEC_DEF, 0,
                        Stats.SPEED, 0
                )
        );
        return map;
    }

    public static HashMap<Stats, Integer> getRandomIVsMap() {
        Random rand = new Random();
        HashMap<Stats, Integer> ivMap = getVoidStatsMap();

        for (Stats stat : ivMap.keySet()) {
            ivMap.replace(stat, rand.nextInt(0, 15));
        }

        return ivMap;
    }


    public static int getNextID() {
        return ++maxID;
    }


    /**
     *
     */
    public static void save() {
        SerializableHandler.save(pkmnMap, SER_SRC_NAME);
    }


    /**
     *
     */
    public static void save(OwnedPokemon p) {
        pkmnMap.put(p.getID(), p);
        SerializableHandler.save(p, p.getID(), SER_SRC_NAME);
    }

    /**
     *
     */
    public static void delete(OwnedPokemon p, boolean save) {
        if (save)
            SerializableHandler.delete(p.getID(), SER_SRC_NAME);
        else
            pkmnMap.remove(p.getID());
    }
}
