package it.rd.jpokebattle.model.pokemon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;

import static it.rd.jpokebattle.model.pokemon.PokemonManager.getXPTreshold;

public class OwnedPokemon extends Pokemon implements Serializable {
    private int ID;
    private int xp;
    private int nextLvXPTresh;

    protected transient IntegerProperty xpProperty;


    /**
     */
    public OwnedPokemon(int id, String breedName, int lv) {
        super(Breed.fromName(breedName), lv);
        this.ID = id;
        setXp();
    }

    /**
     */
    public OwnedPokemon(int id, Pokemon pkmn) {
        super(pkmn);
        this.ID = id;
        setXp();
    }

    /**
     */
    protected OwnedPokemon() {
        super();
    }


    public int getID() {
        return ID;
    }

    public int getXp() {
        return xp;
    }

    public int getNextLvXPTresh() {
        return nextLvXPTresh;
    }

    public int getXpToNextLv() {
        return Math.max(nextLvXPTresh - xp, 0);
    }

    private void setXp() {
        updateXp(getXPTreshold(getLevel()));
        nextLvXPTresh = getXPTreshold(getLevel() + 1);
    }

    public IntegerProperty xpProperty() {
        return xpProperty;
    }

    public void increaseXP(int addedXP) {
        updateXp(addedXP);

        while (xp >= nextLvXPTresh) {
            increaseLevel();
            nextLvXPTresh = getXPTreshold(getLevel() + 1);
            updateStats();
        }

        if (canEvolve())
            breed = Breed.fromName(breed.getSuccBreedName());

    }

    private void updateXp(int addedXP) {
        xp += addedXP;
        xpProperty.set(xp);
    }

    private void increaseLevel() {
        currLevel++;
        currLevelProperty.set(currLevel);
    }

    private boolean canEvolve() {
        int evoT = breed.getNextEvoThresh();
        return getLevel() >= evoT && evoT != 0;
    }

    public void heal() {
        setCurrHP(getMaxHP());
    }

    public void heal(int amount) {
        setCurrHP(Math.min(getMaxHP(), amount));
    }

    @Override
    protected void refreshProperties() {
        super.refreshProperties();
        xpProperty = new SimpleIntegerProperty(xp);
    }

}
