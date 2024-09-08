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
        xp = getXPTreshold(currLevel);
        nextLvXPTresh = getXPTreshold(currLevel + 1);
    }

    public int getXpToNextLv() {
        return Math.max(nextLvXPTresh - xp, 0);
    }

    public void increaseXP(int addedXP) {
        xp += addedXP;

        while (xp >= nextLvXPTresh) {
            currLevel++;
            nextLvXPTresh = PokemonManager.getXPTreshold(currLevel+1);
            updateStats();
        }

        if (canEvolve())
            breed = Breed.fromName(breed.getSuccBreedName());

    }

    private boolean canEvolve() {
        int evoT = breed.getNextEvoThresh();
        return currLevel >= evoT && evoT != 0;
    }

    public void heal() {
        currHP = getStat(Stats.HP);
    }

    public void heal(int amount) {
        currHP = Math.min(getStat(Stats.HP), amount);
    }
}





//    private void increaseLevelByOne() {this.level++;}

//    public void canLevelUp() {
//        if (this.exp >= this.expForNextLevel && this.level < 100) {
//            levelUpAndStatsUp();
//        }
//    }

//    private void updateStat(Stats stat, int iv, int baseStat) {
//        int newValue = ((baseStat + iv) * 2 + level / 4 ) * level / 100 + 5; // 2 + Math.sqrt(usedEVs) / 4 *
//
//        switch (stat) {
//            case stat.HP:
//                newValue += level + 5;
//                setHp(newValue);
//                break;
//            case stat.ATK:
//                setAtk(newValue);
//                break;
//            case stat.DEF:
//                setDef(newValue);
//                break;
//            case stat.SPEC_ATK:
//                setSpecialAtk(newValue);
//                break;
//            case stat.SPEC_DEF:
//                setSpecialDef(newValue);
//                break;
//            case stat.SPEED:
//                setSpeed(newValue);
//                break;
//        }
//    }

//    private void levelUpAndStatsUp() {
//        levelUp();
//        for (Stats stat : ivMap.keySet()) {
//            updateStat(stat, ivMap.get(stat), getBreed().baseValueOf(stat));
//        }
//    }

//    private void levelUp() {
//        increaseLevelByOne();
//        this.exp = 0;
//        if (this.level < 100) {
//            this.expForNextLevel = calculateRequiredExpForNextLevel(this.level);
//        }
//    }

//    private int calculateRequiredExpForNextLevel(int currentLevel) {
//        currentLevel++;
//        return 4 * (currentLevel * currentLevel * currentLevel) / 5;
//    }

//    public boolean canEvolve(){                        // TODO aggiungere messaggio GUI
//        return this.getLevel() >= this.getBreed().getNextEvoThreshold();
//    }

//    public void evolve() {
//        if (this.canEvolve()) {
//            this.breedName = this.getBreed().getSuccBreedName();
//
//            if (this.nickname.isEmpty()) {
//                this.setName(breedName);
//            }
//        }
//    }

//    public void addMove(Move move) {
//        if (moves.size() > 3) {
//
//            System.out.println(getName() + "conosce già 4 mosse, deve dimenticarne una..."); // TODO manu messaggio in GUI
//            // click per rimuovere una mossa                                               // in GUI
//            // removeMove(Move mossaDaDimenticare)
//            System.out.println(getName() + "ha imparato la mossa: " + move + "\nFantastico!"); // messaggio in GUI
//        }
//        else {
//            int ppMax = move.getPPDefault();
//            this.moves.put(move, ppMax);
//        }
//
//    }

//    public void removeMove(Move move){
//        this.moves.remove(move);
//    }

//    public HashMap<Move, Integer> getMoves() {
//        return moves;
//    }

//    public void setMoves(HashMap<Move, Integer> moves) {
//        if (moves.size() > 4) {
//            throw new IllegalArgumentException("Un Pokémon può avere al massimo 4 mosse.");
//        }
//        this.moves = moves;
//    }

//    public void setMoves(Move[] moves) {
//        if (moves.length > 4) {
//            throw new IllegalArgumentException("Un Pokémon può avere al massimo 4 mosse.");
//        }
//
//        for (Move move : moves) {
//            this.moves.put(move, move.getPPDefault());
//        }
//    }