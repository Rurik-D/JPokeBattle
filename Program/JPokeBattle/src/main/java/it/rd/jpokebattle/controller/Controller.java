package it.rd.jpokebattle.controller;

import it.rd.jpokebattle.model.profile.Profile;

import java.util.Random;

/**
 * Classe astratta base per i controller del gioco. Gestisce il profilo del giocatore
 * e fornisce utilità comuni come un'istanza di Random.
 */
public abstract class Controller {
    private static Profile player;
    protected Random rand = new Random();

    /**
     * Restituisce il profilo del giocatore attualmente impostato.
     *
     * @return Il profilo del giocatore.
     */
    public static Profile getPlayer() {
        return player;
    }

    /**
     * Imposta il profilo del giocatore.
     *
     * @param p Il profilo del giocatore da impostare.
     * @throws IllegalArgumentException Se il profilo fornito è nullo.
     */
    public static void setPlayer(Profile p) {
        player = p;
    }

}
