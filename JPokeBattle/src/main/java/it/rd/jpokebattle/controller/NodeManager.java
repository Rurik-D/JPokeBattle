package it.rd.jpokebattle.controller;

import it.rd.jpokebattle.controller.menu.MenuController;
import javafx.scene.Parent;

public abstract class NodeManager {
    protected static Parent root;

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
}
