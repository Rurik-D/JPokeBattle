package it.uniroma1.jtrash.controller;

import it.uniroma1.jtrash.controller.player.Cycle;
import it.uniroma1.jtrash.controller.player.PlayerController;
import it.uniroma1.jtrash.model.board.Field;
import it.uniroma1.jtrash.model.player.Player;
import it.uniroma1.jtrash.model.state.StateMachine;
import it.uniroma1.jtrash.model.state.round_1.GameStartState;
import it.uniroma1.jtrash.model.state.round_1.TurnState;

import java.util.List;
import java.util.Random;

public class GameController {
    private Field field;
    private List<Player> playerList;
    private List<PlayerController> playerControllers;
    private StateMachine gameStates;
    private Random randomGenerator;

    public GameController(){
        this.gameStates = new StateMachine(new GameStartState());
    }

    public void addPlayer(boolean bot){}

    public void start(){
        int r = randomGenerator.nextInt(playerList.size());
        GameStartState cs = (GameStartState) gameStates.getStatoCorrente();
        cs.transition(new TurnState(r, new Cycle(playerList.size()), null));
    }
}
