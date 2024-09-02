package it.uniroma1.jtrash.controller.player;

import it.uniroma1.jtrash.Observer;
import it.uniroma1.jtrash.model.Partita;
import it.uniroma1.jtrash.model.player.Player;

public abstract class PlayerController implements Observer {
    /**
     * segue il pattern decoratore
     */
    private Player player;

    public PlayerController(Player p){
        this.player = p;
    }

    public void associate(Partita partita){
        partita.addObserver(this);
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isMyTurn(Partita partita){
        return getPlayer().getPlayerID() == partita.getGiocatoreCorrente().getPlayerID();
    }
}

