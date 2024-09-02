package it.rd.jpokebattle.model.battle;

import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.model.pokemon.Pokemon;

public class BattleManager {
    private static Profile player1;
    private static Profile player2;
    private static Pokemon striker;
    private static Pokemon defender;

    /*

    public void newBattle(Profile player1, Profile player2, Pokemon pkmn1, Pokemon pkmn2) {
        this.player1 = player1;
        this.player2 = player2;
        this.striker = pkmn1;
        this.defender = pkmn2;
    }

    private void nextRound() {
        Pokemon tmp = striker;
        striker = defender;
        defender = tmp;
    }

    public void setStriker(Pokemon newPokemon) {
        striker = newPokemon;
    }

    public void attack() {
        int damage = striker.getAtk(); // TODO FORMULA DEL DANNO
        defender.setHp(Math.max(defender.getHp() - damage, 0)); // TODO SOSTITUIRE CON UN METODO PER "PRENDERE DANNO" E CONTROLLARE CHE IL POKEMON SIA ANCORA VIVO
    }


    public void useItem() {

    }
    */

}
