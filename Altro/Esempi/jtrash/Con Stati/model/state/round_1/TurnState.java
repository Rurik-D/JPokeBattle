package it.uniroma1.jtrash.model.state.round_1;

import it.uniroma1.jtrash.Observable;
import it.uniroma1.jtrash.Observer;
import it.uniroma1.jtrash.controller.player.Cycle;
import it.uniroma1.jtrash.model.player.Player;
import it.uniroma1.jtrash.model.state.State;
import it.uniroma1.jtrash.model.state.StateMachine;
import it.uniroma1.jtrash.model.state.turn_2.TurnEndState;
import it.uniroma1.jtrash.model.state.turn_2.TurnStartState;

public class TurnState extends State implements Observer {
    private Player lastPlayer;
    private int currentPlayer;
    private Cycle
    private StateMachine TurnStates;

    public TurnState(int currentPlayer, Cycle ciclo, Player lastPlayer){
        this.currentPlayer = currentPlayer;
        this.lastPlayer = lastPlayer;
        this.TurnStates = new StateMachine(new TurnStartState());
        TurnStates.addObserver(this);
    }

    @Override
    public void onUpdate(Observable observable) {
        StateMachine o = (StateMachine) observable;
        if (o.getStatoCorrente() instanceof TurnEndState) {
            TurnEndState endState = (TurnEndState) o.getStatoCorrente();
            if (endState.isLastTurn()){}
            else{}
            /* se turnend dice che è la fine di un turno normqle: dobiamo controllare se il turno lo abbiamo iniziato
            * sapendo che il turno era l'ultimo ultimo,il giocatore che ha appena finito er il lastPlayer, il prossimo stato è il gameend
            * altrimenti: se è normale normale: il prossimo turno è del giocatore successivo
            * oppure:  */
        }
    }
}











1,2,3, game end


enum
turno g1(0), turno g2(1) ...


1 -> 2 -> 3
V
inizio turno, pesca carta, carta in mano


if





















