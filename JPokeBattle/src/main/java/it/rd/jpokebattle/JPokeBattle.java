package it.rd.jpokebattle;

import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.controller.menu.MenuNodeManager;
import it.rd.jpokebattle.model.pokemon.PokemonManager;
import it.rd.jpokebattle.util.audio.SoundManager;
import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Classe principale da cui parte l'esecuzione del codice.
 *
 * @author Emanuele D'Agostino
 * @version 1.0
 * @since 2024-08-12
 */
public class JPokeBattle extends Application {
    private FXMLLoader loader;
    private Stage mainStage;
    private Scene mainScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        loader = ResourceLoader.loadFXML("fxml.menu");
        setScene(loader.load());
        setMainStage();

        NodeManager.setRoot(mainScene.getRoot());
        MenuNodeManager.setController(loader.getController());

        SoundManager.getInstance();

        PokemonManager.updatePkmnMap();
    }


    /**
     * Imposta la scena iniziale.
     */
    private void setScene(Parent root) {
        mainScene = new Scene(root);
        mainScene.getStylesheets().add(ResourceLoader.getResource("css.menu"));
        mainScene.setUserData(loader);
    }

    /**
     * Imposta parametri e valori della finestra principale.
     */
    private void setMainStage() {
        Image icon = ResourceLoader.loadImage("img.logo");
        mainStage.getIcons().add(icon);
        mainStage.setTitle("JPokeBattle");
        mainStage.getIcons().add(icon);
        mainStage.setResizable(false);
        mainStage.setScene(mainScene);
        mainStage.show();
    }
}
