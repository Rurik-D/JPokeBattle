package it.rd.jpokebattle.controller;

import it.rd.jpokebattle.controller.menu.MenuController;
import javafx.animation.PauseTransition;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;

public abstract class NodeManager {
    protected static Parent root;
    private PauseTransition pause = new PauseTransition(Duration.millis(50));


    /**
     * Salva la scena principale.
     *
     * @param sceneRoot     Radice della scena
     */
    public static void setRoot(Parent sceneRoot) {
        root = sceneRoot;
    }

    /**
     * Nasconde tutti i bottoni della scena.
     *
     * @see MenuController
     */
    protected void hideAllButtons(){
        root.lookupAll(".button").forEach(node -> node.setVisible(false));
    }


    /**
     *
     */
    protected void updateNarratorScrollbarPosition(ScrollPane scrl) {
        pause.setOnFinished(e -> scrl.setVvalue(1.0));
        pause.play();
    }


}
