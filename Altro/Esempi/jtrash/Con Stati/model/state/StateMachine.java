package it.uniroma1.jtrash.model.state;

import it.uniroma1.jtrash.AbstractObservable;

public class StateMachine extends AbstractObservable {
    private State statoCorrente;

    public StateMachine(State stato){
        transition(stato);
    }

    /*default */ void transition(State s){
        if (statoCorrente != null) statoCorrente.onExit();
        statoCorrente = s;
        s.setStateMachine(this);
        update();
    }

    public State getStatoCorrente() {
        return statoCorrente;
    }
}


//fare gli saltri state scarto e trash e carta in mano

//bolean che transiziona in automatico
