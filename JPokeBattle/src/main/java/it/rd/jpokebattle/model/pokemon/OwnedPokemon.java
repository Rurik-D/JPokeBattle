package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.model.move.Move;
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

    @Override
    public String getName() {
        return this.name;
    }

    private void setXp() {
        updateXp(getXPTreshold(getLevel()));
        nextLvXPTresh = getXPTreshold(getLevel() + 1);
    }

    private void setXpTresh() {
        nextLvXPTresh = getXPTreshold(getLevel() + 1);
    }

    public IntegerProperty xpProperty() {
        return xpProperty;
    }

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

    public void addMove(Move move) {
        moves.add(move);
        PPs.add(move.getPP());
        ppProperties.add(new SimpleIntegerProperty(move.getPP()));
    }

    public void removeMove(int moveIndex) {
        moves.remove(moveIndex);
        PPs.remove(moveIndex);
        ppProperties.remove(moveIndex);
    }

    public void restorePPs() {
        int pp;
        for (int i = 0; i < PPs.size(); i++) {
            pp = moves.get(i).getPP();
            PPs.set(i, pp);
            ppProperties.get(i).set(pp);
        }
    }


    public void heal() {
        setCurrHP(getMaxHP());
        restorePPs();
    }

    public void heal(int amount) {
        setCurrHP(Math.min(getMaxHP(), amount));
    }

    @Override
    protected void refreshProperties() {
        super.refreshProperties();
        xpProperty = new SimpleIntegerProperty(xp);
    }

    public void replaceMove(int moveToForgetIndex, Move newMove) {
        moves.set(moveToForgetIndex, newMove);
        PPs.set(moveToForgetIndex, newMove.getPP());
        ppProperties.get(moveToForgetIndex).set(newMove.getPP());
    }
}
