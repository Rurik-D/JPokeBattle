package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.model.move.Move;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static it.rd.jpokebattle.model.pokemon.Stats.HP;

/**
 * Classe che rappresenta un Pokémon. Contiene informazioni sulle statistiche,
 * mosse, livello attuale e stato corrente, oltre a metodi per interagire con
 * il Pokémon durante il gioco.
 */
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
    protected transient ArrayList<IntegerProperty> ppProperties;

    /**
     * Costruttore che inizializza un Pokémon a partire da una specie e un livello.
     *
     * @param breed La specie del Pokémon.
     * @param lv    Il livello iniziale del Pokémon.
     */
    public Pokemon(Breed breed, int lv) {
        this.breed = breed;
        this.name = breed.getName();
        refreshProperties();
        setCurrLevel(lv);
        updateStats();
        setCurrHP(getMaxHP());
    }

    /**
     * Costruttore che crea una copia di un Pokémon esistente.
     *
     * @param pkmn Il Pokémon da copiare.
     */
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

    /**
     * Restituisce la specie del Pokémon.
     *
     * @return La specie del Pokémon.
     */
    public Breed getBreed() {
        return breed;
    }

    /**
     * Restituisce il nome del Pokémon.
     *
     * @return Il nome del Pokémon, seguito dalla dicitura "selvatico".
     */
    public String getName() {
        return name + " selvatico";
    }

    /**
     * Restituisce il livello attuale del Pokémon.
     *
     * @return Il livello del Pokémon.
     */
    public int getLevel() {return currLevel;}

    /**
     * Restituisce gli HP correnti del Pokémon.
     *
     * @return Gli HP attuali del Pokémon.
     */
    public int getCurrHP() {
        return currHP;
    }

    /**
     * Restituisce gli HP massimi del Pokémon.
     *
     * @return Gli HP massimi calcolati in base alle statistiche.
     */
    public int getMaxHP() {
        return statsMap.get(HP);
    }

    /**
     * Restituisce il valore di una statistica specificata. Per precisione
     * ed elusione, restituisce 100 se non sono presenti.
     *
     * @param stat La statistica desiderata.
     * @return Il valore della statistica.
     */
    public int getStat(Stats stat) {
        return statsMap.getOrDefault(stat, 100);
    }

    /**
     * Restituisce la mappa delle mosse del Pokémon.
     *
     * @return Una mappa con il nome delle mosse e i relativi utilizzi (PP).
     */
    public HashMap<String, Integer> getMoveSet() {
        return moveSet;
    }

    /**
     * Restituisce la mappa dei valori IV (Individual Values) del Pokémon.
     *
     * @return La mappa degli IV.
     */
    public HashMap<Stats, Integer> getIVMap() {
        return ivMap;
    }

    /**
     * Restituisce la mappa dei valori EV (Effort Values) del Pokémon.
     *
     * @return La mappa degli EV.
     */
    public HashMap<Stats, Integer> getEVMap() {
        return evMap;
    }

    /**
     * Restituisce la mappa delle statistiche correnti del Pokémon.
     *
     * @return La mappa delle statistiche.
     */
    public HashMap<Stats, Integer> getStatsMap() {
        return statsMap;
    }

    /**
     * Restituisce la lista degli utilizzi rimanenti (PP) delle mosse.
     *
     * @return La lista dei PP.
     */
    public ArrayList<Integer> getPPs() {
        return PPs;
    }

    /**
     * Restituisce la lista delle mosse conosciute dal Pokémon.
     *
     * @return La lista delle mosse.
     */
    public ArrayList<Move> getMoves() {
        return moves;
    }

    /**
     * Restituisce una mossa in base all'indice specificato.
     *
     * @param index L'indice della mossa.
     * @return La mossa all'indice specificato.
     */
    public Move getMove(int index) {
        return moves.get(index);
    }

    /**
     * Restituisce i PP rimanenti per una mossa specificata.
     *
     * @param index L'indice della mossa.
     * @return I PP rimanenti.
     */
    public int getPP(int index) {
        return PPs.get(index);
    }

    /**
     * Imposta gli HP correnti del Pokémon.
     *
     * @param value I nuovi HP del Pokémon.
     */
    protected void setCurrHP(int value) {
        currHP = value;
        currHPProperty.set(value);
    }

    /**
     * Imposta il livello corrente del Pokémon.
     *
     * @param lv Il nuovo livello del Pokémon.
     */
    public void setCurrLevel(int lv) {
        currLevel = lv;
        currLevelProperty.set(lv);

    }

    /**
     * Imposta una mossa specifica in uno slot specificato.
     *
     * @param move  La mossa da assegnare.
     * @param index Lo slot in cui assegnare la mossa.
     */
    public void setMove(Move move, int index) {
        assert index < 4;

        if (moves.size() > index) {
            moves.set(index, move);
            PPs.set(index, move.getPP());
            ppProperties.get(index).set(move.getPP());
        } else {
            moves.addFirst(move);
            PPs.addFirst(move.getPP());
            ppProperties.add(new SimpleIntegerProperty(move.getPP()));
        }
    }

    /**
     * Imposta un insieme di mosse per il Pokémon, limitato a 4.
     *
     * @param moves Una lista di mosse.
     */
    public void setMoves(ArrayList<Move> moves) {
        int size = Math.min(moves.size(), 4);
        for (int i=0; i<size; i++) {
            setMove(moves.get(i), i);
        }
    }

    /**
     * Restituisce la proprietà osservabile degli HP.
     *
     * @return La proprietà di HP.
     */
    public IntegerProperty currHPProperty() {
        return currHPProperty;
    }

    /**
     * Restituisce la proprietà osservabile del livello corrente.
     *
     * @return La proprietà del livello.
     */
    public IntegerProperty currLevelProperty() {
        return currLevelProperty;
    }

    /**
     * Restituisce la proprietà osservabile degli HP massimi.
     *
     * @return La proprietà di HP massimi.
     */
    public IntegerProperty maxHPProperty() {
        return maxHPProperty;
    }

    /**
     * Restituisce l'array delle proprietà osservabili dei PP di ciascuna mossa.
     *
     * @return L'array delle proprietà dei PP correnti di tutte le mosse conosciute.
     */
    public ArrayList<IntegerProperty> getPpProperties() {
        return ppProperties;
    }

    /**
     * Resetta tutte le variabili properties della classe alla variabile associata.
     */
    protected void refreshProperties() {
        currHPProperty = new SimpleIntegerProperty(currHP);
        currLevelProperty = new SimpleIntegerProperty(currLevel);
        maxHPProperty = new SimpleIntegerProperty(getMaxHP());
        ppProperties = new ArrayList<>(4);

        for (int pp : PPs) {
            ppProperties.add(new SimpleIntegerProperty(pp));
        }
    }

    /**
     * Aggiorna le proprietà delle statistiche del Pokémon, inclusi gli HP massimi
     * e correnti.
     */
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
                statsMap.replace(HP, newValue);  // Per gli HP ho bisogno di aggiornare prima MAX e poi CURR
                currHPProperty.set(currHP);
            } else {
                statsMap.replace(stat, newValue);
            }
        }
    }

    /**
     * Incrementa i valori EV del Pokémon in base a un altro Pokémon sconfitto.
     *
     * @param pkmn Il Pokémon sconfitto.
     */
    public void increaseEV(Pokemon pkmn) {
        HashMap<Stats, Integer> enemyStats = pkmn.getStatsMap();
        int value;

        for (Stats stat : evMap.keySet()) {
            value = evMap.get(stat) + enemyStats.get(stat);
            evMap.replace(stat, value);
        }
    }

    /**
     * Decrementa di 1 i PP dopo l'utilizzo della mossa.
     *
     * @param i L'indice della mossa per la quale si vogliono ridurre i PP
     */
    public void decresePP(int i) {
        int pp = PPs.get(i);
        PPs.set(i, pp - 1);
        ppProperties.get(i).set(pp - 1);
    }

    /**
     * Infligge danno al Pokémon, riducendo gli HP correnti.
     *
     * @param dmg Il danno da infliggere.
     */
    public void takeDamage(int dmg) {
        setCurrHP(Math.max(currHP - dmg, 0));
    }

    /**
     * Infligge danno al Pokémon, riducendo gli HP correnti di un valore da arrotondare.
     *
     * @param v Il danno da infliggere.
     */
    public void takeDamage(double v) {
        takeDamage((int) Math.round(v));
    }

    /**
     * Verifica se il Pokémon è esausto (fainted).
     *
     * @return True se gli HP correnti sono pari o inferiori a 0, altrimenti false.
     */
    public boolean isFainted() {
        return currHP <= 0;
    }
}
