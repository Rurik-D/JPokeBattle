package it.rd.jpokebattle.controller;

import javafx.animation.PauseTransition;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;

/**
 * Classe astratta per la gestione dei nodi nella scena.
 * Fornisce utility comuni per la gestione del layout e delle transizioni.
 */
public abstract class NodeManager {
    protected static Parent root;
    private PauseTransition pause = new PauseTransition(Duration.millis(50));

    /**
     * Imposta la radice della scena principale.
     *
     * @param sceneRoot La radice della scena da impostare.
     * @throws IllegalArgumentException Se la radice fornita è nulla.
     */
    public static void setRoot(Parent sceneRoot) {
        root = sceneRoot;
    }

    /**
     * Aggiorna la posizione dello ScrollPane per far visualizzare l'ultima parte del contenuto.
     * Mantiene lo ScrollPane aggiornato quando viene aggiunto nuovo contenuto.
     *
     * @param scrl Il componente ScrollPane da aggiornare.
     * @throws IllegalArgumentException Se lo ScrollPane fornito è nullo.
     */
    protected void updateNarratorScrollbarPosition(ScrollPane scrl) {
        pause.setOnFinished(e -> scrl.setVvalue(1.0));
        pause.play();
    }
}
