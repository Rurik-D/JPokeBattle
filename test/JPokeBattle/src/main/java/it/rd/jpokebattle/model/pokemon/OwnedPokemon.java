package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.model.move.Move;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;

import static it.rd.jpokebattle.model.pokemon.PokemonManager.getXPTreshold;

/**
 * Classe che rappresenta un Pokémon posseduto da un giocatore. Estende la classe {@link Pokemon}
 * e aggiunge funzionalità relative all'esperienza (XP), livelli e meccaniche di evoluzione.
 */
public class OwnedPokemon extends Pokemon implements Serializable {
    private int ID;
    private int xp;
    private int nextLvXPTresh;

    protected transient IntegerProperty xpProperty;

    /**
     * Costruttore che inizializza un Pokémon posseduto con ID, nome della specie e livello.
     *
     * @param id        L'identificativo unico del Pokémon.
     * @param breedName Il nome della specie del Pokémon.
     * @param lv        Il livello iniziale del Pokémon.
     */
    public OwnedPokemon(int id, String breedName, int lv) {
        super(Breed.fromName(breedName), lv);
        this.ID = id;
        setXp();
    }

    /**
     * Costruttore che crea un Pokémon posseduto a partire da un Pokémon generico.
     *
     * @param id   L'identificativo unico del Pokémon.
     * @param pkmn Il Pokémon da convertire in un Pokémon posseduto.
     */
    public OwnedPokemon(int id, Pokemon pkmn) {
        super(pkmn);
        this.ID = id;
        setXp();
    }

    protected OwnedPokemon() {
        super();
    }

    /**
     * Restituisce l'identificativo unico del Pokémon.
     *
     * @return L'ID del Pokémon.
     */
    public int getID() {
        return ID;
    }

    /**
     * Restituisce l'esperienza attuale del Pokémon.
     *
     * @return L'XP del Pokémon.
     */
    public int getXp() {
        return xp;
    }

    /**
     * Restituisce la soglia di XP necessaria per il prossimo livello.
     *
     * @return La soglia di XP per il prossimo livello.
     */
    public int getNextLvXPTresh() {
        return nextLvXPTresh;
    }

    /**
     * Calcola l'XP rimanente necessario per il prossimo livello.
     *
     * @return La quantità di XP mancante per il prossimo livello.
     */
    public int getXpToNextLv() {
        return Math.max(nextLvXPTresh - xp, 0);
    }

    /**
     * Restituisce il nome del Pokémon.
     *
     * @return Il nome del Pokémon.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Inizializza l'XP e la soglia XP per il Pokémon.
     */
    private void setXp() {
        updateXp(getXPTreshold(getLevel()));
        nextLvXPTresh = getXPTreshold(getLevel() + 1);
    }

    /**
     * Imposta la successiva soglia di livello mediante la funzione {@code getXPTreshold}
     */
    private void setXpTresh() {
        nextLvXPTresh = getXPTreshold(getLevel() + 1);
    }

    /**
     * Restituisce la proprietà osservabile dell'XP.
     *
     * @return La proprietà di XP.
     */
    public IntegerProperty xpProperty() {
        return xpProperty;
    }

    /**
     * Aumenta l'XP del Pokémon di una quantità specificata e gestisce il passaggio
     * di livello e l'evoluzione se applicabile.
     *
     * @param addedXP La quantità di XP da aggiungere.
     */
    public void increaseXP(int addedXP) {
        xp += addedXP;

        while (xp >= nextLvXPTresh) {
            increaseLevel();
            setXpTresh();
            updateStats();
        }

        xpProperty.set(xp);

        if (canEvolve())
            breed = Breed.fromName(breed.getSuccBreedName());
    }

    /**
     * Aggiorna l'XP del Pokémon aggiungendo una quantità specificata e
     * aggiorna la proprietà XP osservabile.
     *
     * @param addedXP La quantità di XP da aggiungere.
     */
    private void updateXp(int addedXP) {
        xp += addedXP;
        xpProperty.set(xp);
    }

    /**
     * Incrementa il livello del Pokémon.
     */
    private void increaseLevel() {
        currLevel++;
        currLevelProperty.set(currLevel);
    }

    /**
     * Verifica se il Pokémon può evolversi in base al livello e alla soglia di evoluzione.
     *
     * @return True se il Pokémon può evolversi, altrimenti false.
     */
    private boolean canEvolve() {
        int evoT = breed.getNextEvoThresh();
        return getLevel() >= evoT && evoT != 0;
    }

    /**
     * Aggiunge una mossa al set di mosse del Pokémon.
     *
     * @param move La mossa da aggiungere.
     */
    public void addMove(Move move) {
        moves.add(move);
        PPs.add(move.getPP());
        ppProperties.add(new SimpleIntegerProperty(move.getPP()));
    }

    /**
     * Rimuove una mossa dal set di mosse del Pokémon in base all'indice specificato.
     *
     * @param moveIndex L'indice della mossa da rimuovere.
     */
    public void removeMove(int moveIndex) {
        moves.remove(moveIndex);
        PPs.remove(moveIndex);
        ppProperties.remove(moveIndex);
    }


    /**
     * Ripristina i PP di tutte le mosse del Pokémon ai loro valori massimi.
     */
    public void restorePPs() {
        int pp;
        for (int i = 0; i < PPs.size(); i++) {
            pp = moves.get(i).getPP();
            PPs.set(i, pp);
            ppProperties.get(i).set(pp);
        }
    }

    /**
     * Cura completamente il Pokémon, ripristinando HP e PP.
     */
    public void heal() {
        setCurrHP(getMaxHP());
        restorePPs();
    }

    /**
     * Cura il Pokémon di una quantità specificata di HP, senza superare il massimo.
     *
     * @param amount La quantità di HP da ripristinare.
     */
    public void heal(int amount) {
        setCurrHP(Math.min(getMaxHP(), amount));
    }

    /**
     * Aggiorna le proprietà osservabili del Pokémon, inclusa la proprietà XP.
     */
    @Override
    protected void refreshProperties() {
        super.refreshProperties();
        xpProperty = new SimpleIntegerProperty(xp);
    }

    /**
     * Sostituisce una mossa esistente con una nuova.
     *
     * @param moveToForgetIndex L'indice della mossa da dimenticare.
     * @param newMove           La nuova mossa da aggiungere.
     */
    public void replaceMove(int moveToForgetIndex, Move newMove) {
        moves.set(moveToForgetIndex, newMove);
        PPs.set(moveToForgetIndex, newMove.getPP());
        ppProperties.get(moveToForgetIndex).set(newMove.getPP());
    }
}
