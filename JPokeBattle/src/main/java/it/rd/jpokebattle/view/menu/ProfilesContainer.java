package it.rd.jpokebattle.view.menu;

import it.rd.jpokebattle.controller.menu.MenuController;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.SerManager;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe che rappresenta il contenitore delle card dei profili all'interno del menù
 * principale.
 */
public class ProfilesContainer extends VBox {
    private HashMap<Integer, Profile> profMap;

    public ProfilesContainer() {
        this.setSpacing(10);
        this.setPadding(new Insets(5));
    }

    /**
     * Carica il file dei profili in una hashmap, svuota il vecchio contenuto
     * del contenitore, dopodiché per ogni profilo aggiunge una HBox (ProfileCard)
     * e ne definisce la funzione in base al tipo di funzione selezionato.
     */
    public void updateProfileContainer() {
        profMap = SerManager.loadSER("ser.prof");
        ArrayList<Profile> profiles;

        if (profMap != null)
            profiles = new ArrayList<>(profMap.values());
        else
            profiles = new ArrayList<>();

        this.getChildren().clear();

        for (int i = profiles.size()-1; i >= 0; i--) {
            Profile prof = profiles.get(i);
            ProfileCard profBox = new ProfileCard(prof);
            profBox.setOnMouseClicked(e -> getController(e).profilePreview(prof));


            this.getChildren().add(profBox);
        }
    }


    /**
     * A partire dall'evento di input ricava lo stage e poi il controller del menù.
     *
     * @param e     Click del mouse
     * @return      Il controller del menù
     */
    private static MenuController getController(MouseEvent e) {
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader loader = (FXMLLoader) stage.getScene().getUserData();
        return loader.getController();
    }
}
