package it.rd.jpokebattle.controller;

import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe finalizzata alla gestione delle transizioni tra scene in un'applicazione JavaFX.
 */
public final class SceneManager {

    /**
     * Costruttore privato per prevenire istanziazione
     */
    private SceneManager() {}

    /**
     * Cambia scena utilizzando un evento come riferimento per la finestra corrente.
     *
     * @param e            Evento da cui ottenere il nodo sorgente per il riferimento alla finestra.
     * @param fxmlSrcName  Nome della risorsa FXML per la nuova scena.
     * @param cssSrcName   Nome della risorsa CSS per lo stile della nuova scena.
     * @return             L'istanza di FXMLLoader associata alla nuova scena.
     * @throws IOException Se il file FXML non viene trovato o non può essere caricato.
     */
    public static FXMLLoader switchScene(Event e, String fxmlSrcName, String cssSrcName) throws IOException {
        return switchScene((Node) e.getSource(), fxmlSrcName, cssSrcName);
    }

    /**
     * Cambia scena utilizzando un nodo come riferimento per la finestra corrente.
     *
     * @param n            Nodo sorgente per ottenere il riferimento alla finestra.
     * @param fxmlSrcName  Nome della risorsa FXML per la nuova scena.
     * @param cssSrcName   Nome della risorsa CSS per lo stile della nuova scena.
     * @return             L'istanza di FXMLLoader associata alla nuova scena.
     * @throws IOException Se il file FXML non viene trovato o non può essere caricato.
     */
    public static FXMLLoader switchScene(Node n, String fxmlSrcName, String cssSrcName) throws IOException {
        Stage stage = (Stage) (n.getScene().getWindow());
        FXMLLoader loader = ResourceLoader.loadFXML(fxmlSrcName);
        Scene scene = new Scene(loader.load());

        scene.setUserData(loader);
        scene.getStylesheets().add(ResourceLoader.getResource(cssSrcName));
        stage.setScene(scene);
        stage.show();

        return loader;
    }
}