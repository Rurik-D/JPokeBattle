package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.model.SerializableHandler;

import java.util.HashMap;
import java.util.Map;

public class PokemonManager implements SerializableHandler {
    private static final String SER_SRC_NAME = "ser.pkmn";
    private static int maxID = SerializableHandler.getMaxID(SER_SRC_NAME);

    /**
     *
     */
    public static void getRandomPokemon(Breed breed) {
        Pokemon pkmn = new Pokemon(breed, 5);
    }


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

    /**
     *
     */
    public static void save(OwnedPokemon p) {
        SerializableHandler.save(p, p.getID(), SER_SRC_NAME);
    }


    /**
     *
     */
    public static void delete(OwnedPokemon p) {
        SerializableHandler.delete(p.getID(), SER_SRC_NAME);
    }
}
