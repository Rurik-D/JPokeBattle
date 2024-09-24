package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.model.SerializableHandler;
import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.util.file.DataMapLoader;
import it.rd.jpokebattle.util.file.SerManager;

import java.util.*;

public class PokemonManager implements SerializableHandler {
    private static Map<String, Map<String, Integer[]>> pkmnSpawnMap = DataMapLoader.loadSpawnMap();
    private static HashMap<Integer, OwnedPokemon> pkmnMap;
    private static final String SER_SRC_NAME = "ser.pkmn";
    private static int maxID = SerializableHandler.calcMaxID(SER_SRC_NAME);
    private static Random rand = new Random();

    /**
     * A partire da una razza e da un livello viene generato casualmente un pokemon di
     * quella razza e quel livello.
     *
     * @param breed  Razza del pokemon da generare
     * @param lv     Livello del pokemon da generare
     */
    public static Pokemon generatePokemon(Breed breed, int lv) {
        Pokemon pkmn =  new Pokemon(breed, lv);
        ArrayList<Move> moves = pkmn.getBreed().getMovesBelowLevel(lv);

        while (moves.size() > 4) {
            moves.remove(rand.nextInt(0, moves.size()));
        }

        pkmn.setMoves(moves);

        return pkmn;
    }

    /**
     *
     * @param id
     * @return
     */
    public static OwnedPokemon loadPokemonFromID(int id) {
        OwnedPokemon pkmn =  pkmnMap.get(id);
        pkmn.refreshProperties();
        return pkmn;
    }

    /**
     * Restituisce dalla mappa (se presente) un oggetto OwnedPokemon a partire dal suo ID.
     *
     * @param id  Codice intero identificativo del pokemon che si vuole caricare
     */
    public static OwnedPokemon getPokemonFromID(int id) {
        return pkmnMap.get(id);
    }

    /**
     * A partire da un'istanza della classe Pokemon, viene generata e restituita un'istanza
     * della classe OwnedPokemon che sia coerente con l'istanza passata in input.
     *
     * @param pkmn   Istanza della classe Pokemon che si vuole convertire
     */
    public static OwnedPokemon toOwnedPokemon(Pokemon pkmn) {
        OwnedPokemon ownPkmn =  new OwnedPokemon(getNextID(), pkmn);
        pkmnMap.put(maxID, ownPkmn);
        return ownPkmn;
    }

    public static Pokemon spawnPokemon(SpawnZone spawn) {
        Map<String, Integer[]> pkmnAreaMap = pkmnSpawnMap.get(spawn.getNameID());
        List<String> breeds = new ArrayList<>(pkmnAreaMap.keySet());

        while (breeds.size() > 1) {
            breeds.remove(rand.nextInt(0, breeds.size()));
        }

        String breedName = breeds.getFirst();
        Integer[] lvRange = pkmnAreaMap.get(breedName);

        Breed breed = Breed.fromName(breedName);
        int lv = rand.nextInt(lvRange[0], lvRange[1]);

        return generatePokemon(breed, lv);
    }

    /**
     * Dato un livello calcola la soglia xp per passare al livello successivo.
     */
    public static int getXPTreshold(int lv) {
        return (int) Math.pow(lv, 3.0);
    }

    /**
     * Genera una mappa delle statistiche con tutti i valori impostati a 0.
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

    /**
     * Genera una mappa casuale degli IV.
     * Per ogni pokemon tale mappa viene calcolata una volta sola al momento della creazione.
     */
    public static HashMap<Stats, Integer> getRandomIVsMap() {
        Random rand = new Random();
        HashMap<Stats, Integer> ivMap = getVoidStatsMap();

        for (Stats stat : ivMap.keySet())
            ivMap.replace(stat, rand.nextInt(0, 15));

        return ivMap;
    }

    /**
     * Incrementa e ritorna il valore dell'attuale ID più grande generato.
     */
    public static int getNextID() {
        return ++maxID;
    }

    /**
     * Aggiorna la mappa dei pokemon caricando i valori dal relativo file ser.
     * Se il file è vuoto genera una nuova HashMap vuota.
     */
    public static void updatePkmnMap() {
        pkmnMap = SerManager.loadSER(SER_SRC_NAME);
        if (pkmnMap == null)
            pkmnMap = new HashMap<>();
    }

    /**
     * Salva sul relativo file ser la mappa attuale dei pokemon.
     */
    public static void save() {
        SerializableHandler.save(pkmnMap, SER_SRC_NAME);
    }

    /**
     * Aggiunge o aggiorna un pokemon nella mappa, dopodiché salva sul relativo file ser
     * la mappa attuale dei pokemon.
     */
    public static void save(OwnedPokemon p) {
        pkmnMap.put(p.getID(), p);
        save();
    }

    /**
     * Elimina un pokemon dalla mappa
     */
    public static void delete(OwnedPokemon p, boolean save) {
        pkmnMap.remove(p.getID());

        if (save)
            SerializableHandler.delete(p.getID(), SER_SRC_NAME);
    }

    /**
     * Dato un pokemon ritorna la mossa imparata nel livello corrente (se esiste)
     *
     * @param pkmn Pokemon posseduto da cui estrarre razza e livello
     * @return Mossa imparata al livello corrente
     */
    public static Move getNewMove(OwnedPokemon pkmn) {
        int lv = pkmn.getLevel();
        return pkmn.getBreed().getMoveFromLevel(lv);
    }
}
