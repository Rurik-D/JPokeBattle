package it.uniroma1.jtrash.model.state.turn_2;

import it.uniroma1.jtrash.model.state.State;

public class TurnEndState extends State {
    private boolean lastTurn;

    public TurnEndState(boolean lastTurn){
        this.lastTurn = lastTurn;
    }

    public boolean isLastTurn() {
        return lastTurn;
    }
}
