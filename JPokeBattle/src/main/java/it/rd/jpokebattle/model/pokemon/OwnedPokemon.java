package it.rd.jpokebattle.model.pokemon;

import java.io.Serializable;
import java.util.HashMap;

public class OwnedPokemon extends Pokemon implements Serializable {
    private final int ID;
    private String nickname;
    private int exp = 0;
    private int nextLvExpTresh;
    protected HashMap<Stats, Integer> ivMap;


    /**

     */
    public OwnedPokemon(int id, String breedName, String nickname) {
        super(Breed.fromName(breedName), 5);
        this.ID = id;
//        this.nickname = nickname;  // setNickname(name); TODO qui va bene il setNickname? a pokemon catturato serve messaggio GUI per fare setNickname
//        this.expForNextLevel = calculateRequiredExpForNextLevel(this.level);
//        save();               // aggiunge il pokemon alla mappa e la salva nel file ser
    }

    public int getID() {
        return ID;
    }

    //    @Override
//    public String getName() {
//        return (nickname != null && !nickname.isEmpty()) ? nickname : super.getName();
//    }

//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }

//    public int getExp() {return this.exp;}

//    public int getExpForNextLevel() {return expForNextLevel;}

//    public int getIV(String statName) {return ivMap.get(statName);}

//    public void setExp(int exp) {this.exp = exp;}

//    private void setExpForNextLevel(int expForNextLevel) {this.expForNextLevel = expForNextLevel;}

//    public void addExp(int expGuadaganti) {
//        if (this.getLevel() < 100)
//            this.exp += expGuadaganti;
//    }

//    private void setRandomIVs() {
//        Random random = new Random();
//
//        for (Stats stat : ivMap.keySet()) {
//            ivMap.replace(stat, random.nextInt(0, 16));
//        }
//    }

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
}
