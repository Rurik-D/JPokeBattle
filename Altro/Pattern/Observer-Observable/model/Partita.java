package it.uniroma1.jtrash.model;

import it.uniroma1.jtrash.AbstractObservable;
import it.uniroma1.jtrash.Observable;
import it.uniroma1.jtrash.controller.board.FieldCreator;
import it.uniroma1.jtrash.model.State.Stato;
import it.uniroma1.jtrash.model.board.Field;
import it.uniroma1.jtrash.model.player.Player;

import java.util.List;

public class Partita extends AbstractObservable {
    private Stato stato;
    private List<Player> listaPlayers;
    private int turnoDelGiocatoreCorrente;
    private Field field;

    public Partita(List<Player> listaPlayers, int turnoDelGiocatoreCorrente){
        this.listaPlayers = listaPlayers;
        this.turnoDelGiocatoreCorrente = turnoDelGiocatoreCorrente;
        this.stato = Stato.STATO_INIZIALE;
        this.field = new FieldCreator(listaPlayers).create();
    }

    public Player getGiocatoreCorrente(){
        return listaPlayers.get(turnoDelGiocatoreCorrente);
    }

    public void primoTurno(){


        update();
    }

    public void avanzaTurno(){
        turnoDelGiocatoreCorrente ++;
        turnoDelGiocatoreCorrente %= listaPlayers.size();
        //controllare se partita finita
        update();
    }

}
