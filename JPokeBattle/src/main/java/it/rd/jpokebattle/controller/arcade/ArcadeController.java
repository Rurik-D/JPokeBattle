package it.rd.jpokebattle.controller.arcade;

import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.controller.SceneManager;
import it.rd.jpokebattle.controller.menu.MenuNodeManager;
import it.rd.jpokebattle.model.area.Area;
import it.rd.jpokebattle.model.pokemon.Breed;
import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.model.pokemon.PokemonManager;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.model.profile.ProfileManager;
import it.rd.jpokebattle.util.audio.SoundManager;
import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ArcadeController {
    private static Profile player;
    private ArcadeNodeManager nodeMan = ArcadeNodeManager.getIstance();
    private SoundManager soundMan = SoundManager.getInstance();
    private Breed selectedBreed;

    @FXML
    protected Button nextAreaBtn, prevAreaBtn, specialAreaBtn;
    @FXML
    protected Label narratorLbl, profileNameLbl, clockLbl, locationLbl;
    @FXML
    protected ImageView avatarImageView, selectPrevImgView;
    @FXML
    protected GridPane
            gameSettingsPane,
            pokemonPane,
            invenctoryPane,
            starterSelectionPane,
            pokeMartPane,
            selectionPreviewPane;
    @FXML
    protected ScrollPane narratorScrlPane;

    @FXML
    protected FlowPane teamPane;


    public static Profile getPlayer() {
        return player;
    }

    public static void setPlayer(Profile lastPickedProfile) {
        player = lastPickedProfile;

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
        nodeMan.showOwnedPokemon(player);

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
        player.setNarratorTextHistory(narratorLbl.getText());
        ProfileManager.save(player);
        PokemonManager.save();
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
        String breedName = node.getId();
        selectedBreed = Breed.fromName(breedName);
        selectionPreviewPane.setVisible(true);
        selectPrevImgView.setImage(ResourceLoader.loadImage("img." + breedName));
    }

    /**
     *
     */
    @FXML
    public void confirmSelection(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.hideAllPane();
        nextArea(e);
        Pokemon pkmn = PokemonManager.generatePokemon(selectedBreed, 5);
        player.addToOwned(PokemonManager.toOwnedPokemon(pkmn));
    }

    /**
     *
     */
    @FXML
    public void cancelSelection(ActionEvent e) {
        soundMan.buttonClick();
        selectionPreviewPane.setVisible(false);
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
        player.goToNextArea();
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
        player.goToPrevArea();
        updateNarrator();
        nodeMan.updateNarratorScrollbarPosition();
    }

    /**
     *
     */
    @FXML
    public void specialArea(ActionEvent e) {
        soundMan.buttonClick();
        player.goToSpecialArea();
        updateNarrator();
        nodeMan.updateNarratorScrollbarPosition();
    }

    /**
     * Aggiorna il testo del narratore con la descrizione dell'area corrente in cui
     * il giocatore si trova.
     */
    private void checkSpecialArea() {
        switch (player.getCurrentArea().getAreaType()) {
            case DEFAULT:
                player.goToNextArea();
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
        Area area = player.getCurrentArea();
        nodeMan.updateNarratorLbl(area.getDescription());
        nodeMan.updateChoiceButtons(player.getCurrentArea());
        locationLbl.setText(player.getCurrentArea().getTitle());
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
