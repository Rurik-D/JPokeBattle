package it.rd.jpokebattle.model.move;

import it.rd.jpokebattle.model.pokemon.Type;
import it.rd.jpokebattle.util.file.DataMapLoader;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Classe che rappresenta una mossa utilizzabile dai Pokémon in battaglia.
 * Contiene dettagli sulla potenza, precisione, effetti speciali e modificatori
 * per le statistiche del Pokémon utilizzatore o avversario.
 */
public class Move implements Serializable {
    /**
     * Mappa contenente tutte le mosse caricate dal relativo file json.
     * Le chiavi sono i nomi delle mosse e i valori sono istanze di {@link Move}.
     */
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

    /**
     * Restituisce il nome della mossa.
     *
     * @return Il nome della mossa.
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce la descrizione della mossa.
     *
     * @return La descrizione della mossa.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Restituisce il tipo della mossa.
     *
     * @return Un'istanza di {@link Type} rappresentante il tipo della mossa.
     */
    public Type getType() {
        return Type.fromName(type);
    }

    /**
     * Restituisce la categoria della mossa (ad esempio "Fisico" o "Speciale").
     *
     * @return La categoria della mossa.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Restituisce una mappa delle statistiche avversarie modificate dalla mossa
     * e i relativi moltiplicatori.
     *
     * @return Una mappa delle statistiche avversarie modificate.
     */
    public HashMap<String, Double> getOpponentStats() {
        return opponentStats;
    }

    /**
     * Restituisce una mappa delle statistiche del Pokémon utilizzatore modificate dalla mossa
     * e i relativi moltiplicatori.
     *
     * @return Una mappa delle statistiche del Pokémon utilizzatore modificate.
     */
    public HashMap<String, Double> getUserStats() {
        return userStats;
    }

    /**
     * Restituisce l'effetto speciale associato alla mossa, se presente.
     *
     * @return Un'istanza di {@link MoveSpecialEffect} rappresentante l'effetto speciale.
     */
    public MoveSpecialEffect getSpecialEffect() {
        return MoveSpecialEffect.fromName(specialEffect);
    }

    /**
     * Restituisce la potenza della mossa.
     *
     * @return La potenza della mossa.
     */
    public int getPower() {
        return power;
    }

    /**
     * Restituisce la priorità della mossa.
     *
     * @return La priorità della mossa (valore numerico).
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Restituisce la precisione della mossa come percentuale (valore tra 0 e 1).
     *
     * @return La precisione della mossa.
     */
    public double getPrecision() {
        return precision;
    }

    /**
     * Restituisce il numero massimo di utilizzi (PP) della mossa.
     *
     * @return Il numero massimo di PP della mossa.
     */
    public int getPP() {
        return pp;
    }

    /**
     * Restituisce un'istanza di {@link Move} basata sul nome fornito.
     *
     * @param name Il nome della mossa.
     * @return L'istanza corrispondente della mossa, oppure null se il nome non è valido.
     */
    public static Move fromName(String name) {
        return movesMap.get(name);
    }
}
