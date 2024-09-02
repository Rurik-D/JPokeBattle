package it.rd.jpokebattle.controller.arcade;

import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.controller.SceneManager;
import it.rd.jpokebattle.controller.menu.MenuNodeManager;
import it.rd.jpokebattle.model.area.Area;
import it.rd.jpokebattle.model.pokemon.Breed;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.model.profile.ProfileManager;
import it.rd.jpokebattle.util.audio.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ArcadeController {
    private static Profile profile;
    private ArcadeNodeManager nodeMan = ArcadeNodeManager.getIstance();
    private SoundManager soundMan = SoundManager.getInstance();
    private Breed selectedBreed;

    @FXML
    protected Button nextAreaBtn, prevAreaBtn, specialAreaBtn;
    @FXML
    protected Label narratorLbl, profileNameLbl, clockLbl, locationLbl;
    @FXML
    protected ImageView avatarImageView;
    @FXML
    protected GridPane
            gameSettingsPane,
            pokemonPane,
            invenctoryPane,
            starterSelectionPane,
            pokeMartPane;
    @FXML
    protected ScrollPane narratorScrlPane;



    public static void setProfile(Profile lastPickedProfile) {
        profile = lastPickedProfile;

    }


    /**
     * Nasconde tutti i panelli e torna alla schermata di gioco attuale.
     */
    @FXML
    public void backToGame (ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.hideAllPane();
    }

    /**
     * TODO:DA IMPLEMENTARE
     * Mostra la schermata di visualizzazione dei pokemon.
     */
    @FXML
    public void ownedPokemon(MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showOwnedPokemon();

    }

    /**
     * TODO:DA IMPLEMENTARE
     * Mostra la schermata di visualizzazione dell'inventario.
     */
    @FXML
    public void invenctory(MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showInvenctory();
    }

    /**
     * Torna al menù principale, carica la nuova scena, aggiorna la root nella classe
     * astratta NodeManager e aggiorna il file dei profili.
     * */
    @FXML
    public void mainMenu(ActionEvent e) throws IOException {
        soundMan.buttonClick();
        soundMan.switchTrack("mp3.title");

        FXMLLoader loader = SceneManager.switchScene(e, "fxml.menu", "css.menu");
        NodeManager.setRoot(loader.getRoot());
        MenuNodeManager.setController(loader.getController());
        profile.setNarratorTextHistory(narratorLbl.getText());
        ProfileManager.save(profile);
    }

    /**
     * Mostra la schermata di impostazioni.
     */
    @FXML
    public void settings (MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showSettingsPane();
    }


    /**
     * Mostra la schermata di impostazioni.
     */
    @FXML
    public void selectStarterPokemon (MouseEvent e) {
        soundMan.buttonClick();
        Node node = (Node) e.getSource();
        selectedBreed = Breed.fromName(node.getId());
    }

    /**
     *
     */
    @FXML
    public void confirmSelection(ActionEvent e) {
        soundMan.buttonClick();

    }


    /**
     *
     */
    @FXML
    public void checkAndGoToNextArea(ActionEvent e) {
        soundMan.buttonClick();
        checkSpecialArea();
    }


    /**
     *
     */
    @FXML
    public void nextArea(ActionEvent e) {
        soundMan.buttonClick();
        profile.goToNextArea();
        updateNarrator();
        nodeMan.updateNarratorScrollbarPosition();
        nodeMan.hideAllPane();
    }


    /**
     *
     */
    @FXML
    public void prevArea(ActionEvent e) {
        soundMan.buttonClick();
        profile.goToPrevArea();
        updateNarrator();
        nodeMan.updateNarratorScrollbarPosition();
    }

    /**
     *
     */
    @FXML
    public void specialArea(ActionEvent e) {
        soundMan.buttonClick();
        profile.goToSpecialArea();
        updateNarrator();
        nodeMan.updateNarratorScrollbarPosition();
    }

    /**
     * Aggiorna il testo del narratore con la descrizione dell'area corrente in cui
     * il giocatore si trova.
     */
    private void checkSpecialArea() {
        switch (profile.getCurrentArea().getAreaType()) {
            case DEFAULT:
                profile.goToNextArea();
                updateNarrator();
                nodeMan.updateNarratorScrollbarPosition();
                break;
            case TALL_GRASS:
                System.out.println("Erba alta!"); // TODO
                break;
            case POKE_CENTER:
                System.out.println("Pokemon curati!"); // TODO
                break;
            case POKE_MART:
                pokeMartPane.setVisible(true); // TODO
                break;
            case STARTER_SELECT:
                starterSelectionPane.setVisible(true);
                break;
        }
    }

    /**
     * Aggiorna il testo del narratore con la descrizione dell'area corrente in cui
     * il giocatore si trova.
     */
    private void updateNarrator() {
        Area area = profile.getCurrentArea();
        nodeMan.updateNarratorLbl(area.getDescription());
        nodeMan.updateChoiceButtons(profile.getCurrentArea());
        locationLbl.setText(profile.getCurrentArea().getTitle());
    }

    /**
     * TODO: DA IMPLEMENTARE (PROVVISORIA)
     * @param e
     */
    @FXML
    public void volume(ActionEvent e) {
        soundMan.buttonClick();
        soundMan.toggleVolume();
    }

}
