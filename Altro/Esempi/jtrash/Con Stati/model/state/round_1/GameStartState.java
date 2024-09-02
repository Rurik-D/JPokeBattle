package it.uniroma1.jtrash.model.state.round_1;

import it.uniroma1.jtrash.model.state.State;
import it.uniroma1.jtrash.model.state.turn_2.TurnStartState;

public class GameStartState extends State {

    public GameStartState() {}

    public void transition(TurnState ts){
        super.transition(ts);
    }
}
