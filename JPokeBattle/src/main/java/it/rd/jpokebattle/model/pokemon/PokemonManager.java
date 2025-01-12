package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.model.SerializableHandler;
import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.DataMapLoader;
import it.rd.jpokebattle.util.file.SerManager;

import java.util.*;

/**
 * Classe per la gestione dei Pokémon nel gioco.
 * Fornisce metodi per generare, salvare, aggiornare ed eliminare Pokémon,
 * oltre a gestire statistiche, IV e spawn casuali.
 */
public class PokemonManager implements SerializableHandler {
    private static Map<String, Map<String, Integer[]>> pkmnSpawnMap = DataMapLoader.loadSpawnMap();
    private static HashMap<Integer, OwnedPokemon> pkmnMap;
    private static final String SER_SRC_NAME = "ser.pkmn";
    private static int maxID = SerializableHandler.calcMaxID(SER_SRC_NAME);
    private static Random rand = new Random();

    /**
     * Genera un Pokémon di una determinata razza e livello con mosse casuali valide.
     *
     * @param breed Razza del Pokémon da generare.
     * @param lv    Livello del Pokémon da generare.
     * @return Istanza del Pokémon generato.
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
     * Carica un Pokémon posseduto dalla mappa a partire dal suo ID e aggiorna le sue proprietà.
     *
     * @param id ID del Pokémon da caricare.
     * @return Istanza del Pokémon posseduto.
     */
    public static OwnedPokemon loadPokemonFromID(int id) {
        OwnedPokemon pkmn =  pkmnMap.get(id);
        pkmn.refreshProperties();
        return pkmn;
    }

    /**
     * Restituisce un Pokémon posseduto dalla mappa a partire dal suo ID.
     *
     * @param id ID del Pokémon da ottenere.
     * @return Istanza del Pokémon posseduto, o null se non esiste.
     */
    public static OwnedPokemon getPokemonFromID(int id) {
        return pkmnMap.get(id);
    }

    /**
     * Converte un Pokémon generico in un Pokémon posseduto e lo aggiunge alla mappa.
     *
     * @param pkmn Pokémon da convertire.
     * @return Istanza del Pokémon posseduto generato.
     */
    public static OwnedPokemon toOwnedPokemon(Pokemon pkmn) {
        OwnedPokemon ownPkmn =  new OwnedPokemon(getNextID(), pkmn);
        pkmnMap.put(maxID, ownPkmn);
        return ownPkmn;
    }

    /**
     * Genera e restituisce un Pokémon casuale basato sulla zona di spawn.
     *
     * @param spawn Zona di spawn del Pokémon.
     * @return Pokémon generato casualmente.
     */
    public static Pokemon spawnPokemon(SpawnZone spawn) {
        Map<String, Integer[]> pkmnAreaMap = pkmnSpawnMap.get(spawn.getNameID());
        List<String> tmpBreeds = new ArrayList<>(pkmnAreaMap.keySet());

        while (tmpBreeds.size() > 1)
            tmpBreeds.remove(rand.nextInt(0, tmpBreeds.size()));

        String breedName = tmpBreeds.getFirst();
        Integer[] lvRange = pkmnAreaMap.get(breedName);

        Breed breed = Breed.fromName(breedName);
        int lv = rand.nextInt(lvRange[0], lvRange[1]);

        return generatePokemon(breed, lv);
    }

    
    /**
     * Genera e restituisce un nuovo Pokémon posseduto basato sulla zona di spawn.
     *
     * @param spawn Zona di spawn del Pokémon.
     * @return Pokémon generato casualmente.
     */
    public static Pokemon spawnOwnedPokemon(SpawnZone spawn) {
        Map<String, Integer[]> pkmnAreaMap = pkmnSpawnMap.get(spawn.getNameID());

        List<String> tmpBreeds = switch (spawn) {
            case SPAWN_R1 -> new ArrayList<>(Arrays.asList("pidgey"));
            case SPAWN_R2 -> new ArrayList<>(Arrays.asList("spearow", "ekans"));
            case SPAWN_R3 -> new ArrayList<>(Arrays.asList("butterfree", "beedrill"));
            case SPAWN_R4 -> new ArrayList<>(Arrays.asList("raticate"));
            case SPAWN_R5 -> new ArrayList<>(Arrays.asList("pikachu"));
        };

        while (tmpBreeds.size() > 1)
            tmpBreeds.remove(rand.nextInt(0, tmpBreeds.size()));

        String breedName = tmpBreeds.getFirst();
        Integer[] lvRange = pkmnAreaMap.get(breedName);

        Breed breed = Breed.fromName(breedName);
        int lv = rand.nextInt(lvRange[0], lvRange[1]);

        return generatePokemon(breed, lv);
    }


    /**
     * Calcola la soglia di esperienza necessaria per passare al livello successivo.
     *
     * @param lv Livello attuale.
     * @return Soglia di esperienza necessaria.
     */
    public static int getXPTreshold(int lv) {
        return (int) Math.pow(lv, 3.0);
    }

    /**
     * Crea una mappa delle statistiche con tutti i valori inizializzati a 0.
     *
     * @return Mappa delle statistiche vuota.
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
     * Genera una mappa casuale di IV per le statistiche di un Pokémon.
     *
     * @return Mappa degli IV generata casualmente.
     */
    public static HashMap<Stats, Integer> getRandomIVsMap() {
        Random rand = new Random();
        HashMap<Stats, Integer> ivMap = getVoidStatsMap();

        for (Stats stat : ivMap.keySet())
            ivMap.replace(stat, rand.nextInt(0, 15));

        return ivMap;
    }

    /**
     * Incrementa e restituisce il prossimo ID disponibile.
     *
     * @return Prossimo ID disponibile.
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
     * Salva un Pokémon specifico nella mappa e aggiorna il file di serializzazione.
     *
     * @param p Pokémon da salvare.
     */
    public static void save(OwnedPokemon p) {
        pkmnMap.put(p.getID(), p);
        save();
    }

    /**
     * Elimina un Pokémon dalla mappa e opzionalmente aggiorna il file di serializzazione.
     *
     * @param p    Pokémon da eliminare.
     * @param save Se true, aggiorna il file di serializzazione.
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

    /**
     * Vengono aggiornate le variabili properties di tutti i pokemon del giocatore.
     * Tali variabili consentono di rendere "ascoltabili" determinati parametri del pokemon, come gli HP o i PP.
     * Queste variabili non sono però serializzabili, di consequenza necessitano di un aggiornamento manuale
     * (e quindi di un istanziamento) in determinati punti chiave dell'esecuzione del programma, come ad esempio
     * l'inizializzazione della scena di arcade.
     *
     * @param player Profilo da cui selezionare i pokemon di cui si vogliono aggiornare le variabili properties
     */
    public static void refreshPlayerPkmnsProperties(Profile player) {
        for (OwnedPokemon pkmn : player.getTeam()) {
            pkmn.refreshProperties();
        }
    }
}
