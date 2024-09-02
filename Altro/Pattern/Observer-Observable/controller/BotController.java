package it.uniroma1.jtrash.controller.player;

import it.uniroma1.jtrash.Observable;
import it.uniroma1.jtrash.model.Partita;
import it.uniroma1.jtrash.model.player.Player;

public class BotController extends PlayerController{
    public BotController(Player p) {
        super(p);
    }

    @Override
    public void onUpdate(Observable observable) {
        if (observable instanceof Partita){     //TODO riguardare e capire bene
            Partita obs = (Partita) observable;
            if (isMyTurn(obs)) System.out.println("chiamato onUpdate su Partita " + getPlayer().getPlayerID());
        }
    }
}
