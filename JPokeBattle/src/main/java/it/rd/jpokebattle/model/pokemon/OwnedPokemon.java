package it.rd.jpokebattle.model.pokemon;

import java.io.Serializable;
import static it.rd.jpokebattle.model.pokemon.PokemonManager.getXPTreshold;

public class OwnedPokemon extends Pokemon implements Serializable {
    private int ID;
    private int xp;
    private int nextLvXPTresh;

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

    private void setXp() {
        xp = getXPTreshold(getLevel());
        nextLvXPTresh = getXPTreshold(getLevel() + 1);
    }

    public int getXpToNextLv() {
        return Math.max(nextLvXPTresh - xp, 0);
    }

    public void increaseXP(int addedXP) {
        xp += addedXP;

        while (xp >= nextLvXPTresh) {
            increaseLevel();
            nextLvXPTresh = PokemonManager.getXPTreshold(getLevel() + 1);
            updateStats();
        }

        if (canEvolve())
            breed = Breed.fromName(breed.getSuccBreedName());

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
}
