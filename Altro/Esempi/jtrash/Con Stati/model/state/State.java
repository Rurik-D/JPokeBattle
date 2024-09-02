package it.uniroma1.jtrash.model.state;

public abstract class State {
    private StateMachine stateMachine;
    public State(){}

    protected final void transition(State s){
        stateMachine.transition(s);
    }

    protected void onExit(){}

    void setStateMachine(StateMachine stateMachine){
        this.stateMachine = stateMachine;
    }
}
