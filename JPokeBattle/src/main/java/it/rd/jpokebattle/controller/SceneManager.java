package it.rd.jpokebattle.controller;

import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class SceneManager {
    /**
     * Questo metodo consente il passaggio di una scena ad un'altra.
     *
     * @param fxmlSrcName   Nome della risorsa del file fxml che si vuole usare per cambiare scena.
     * @see it.rd.jpokebattle.properties
     * @return Il loader del nuovo file FXML
     * */
    public static FXMLLoader switchScene(Event e, String fxmlSrcName, String cssSrcName) throws IOException {
        return switchScene((Node) e.getSource(), fxmlSrcName, cssSrcName);
    }

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



    public static Parent getRootFromEvent(Event e) {
        Node node = (Node) e.getSource();
        return node.getScene().getRoot();
    }
}