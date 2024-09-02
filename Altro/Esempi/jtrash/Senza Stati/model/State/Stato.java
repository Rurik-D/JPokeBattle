package it.uniroma1.jtrash.model.State;

public enum Stato {
    /**
     * Carta non ancora pescata
     */
    STATO_INIZIALE,

    /**
     * Carta in mano, si aspetta l'input utente per posizionare la carta
     */
    CARTA_IN_MANO,

    /**
     * Non c'e' posto per la carta negli slot, oppure hai un mano jack o queen
     */
    RICHIESTA_SCARTO,

    /**
     * Il giocatore ha riempito gli slot, deve dire TRASH
     */
    TRASH,

    /**
     * Fine turno NORMALE
     * il giocatore ha finito il turno ma non ha ancora completato gli slot
     */
    FINE_TURNO_NORMALE,

    /**
     * il giocatore ha finito il turno e ha completato gli slot,
     * si procede il giro fino al nuovo turno di questo giocatore
     * (lui escluso), poi la partita finisce
     */
    FINE_TURNO_TRASH;
}
