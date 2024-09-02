package it.uniroma1.jtrash.controller;

import it.uniroma1.jtrash.controller.player.BotController;
import it.uniroma1.jtrash.controller.player.HumanController;
import it.uniroma1.jtrash.controller.player.PlayerController;
import it.uniroma1.jtrash.model.Partita;
import it.uniroma1.jtrash.model.player.Player;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {
    private static final int NUMERO_CARTE_INIZIALE = 10;
    private List<Player> listaPlayersDaRiempire;
    private List<PlayerController> playerControllers; //per ogni giocatore associamo un controller, che sa il tipo di giocatore (bot o no) elo gestisce
    private Random randomGenerator;
    private Partita partita;

    //Costruttore
    public GameController(){ // è lo stesso fino alla vittoria finale
        this.listaPlayersDaRiempire = new ArrayList<>();
        this.playerControllers = new ArrayList<>();
        this.randomGenerator = new Random();
    }

    //------------------------------------------------------------------------------------------------------------

    /**
     * Registra un giocatore nella partita, la registrazione è possibile solo quando la partita non è ancora iniziata
     *
     * @param bot è un flag per indicare se il giocatore è controllato automaticamente o no
     */
    public void addPlayer(boolean bot){
        Player p = new Player(NUMERO_CARTE_INIZIALE,0);
        listaPlayersDaRiempire.add(p);
        if (bot) playerControllers.add(new BotController(p));  //decorator
        else {playerControllers.add(new HumanController(p));    //decorator
        }
    }

    //------------------------------------------------------------------------------------------------------------


    public void start(){
        int firstPlayer = randomGenerator.nextInt(0,listaPlayersDaRiempire.size());
        for (int i = 0; i < listaPlayersDaRiempire.size(); i++){
            Player p = listaPlayersDaRiempire.get((firstPlayer + i) % listaPlayersDaRiempire.size());
            p.setPlayerID(i);
        }
        this.partita = new Partita(listaPlayersDaRiempire, firstPlayer);
        for (PlayerController plCo : playerControllers) {
            plCo.associate(partita);
        }
        partita.primoTurno();
    }


}


//