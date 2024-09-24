package it.rd.jpokebattle.controller.arcade;

import it.rd.jpokebattle.controller.Controller;
import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.controller.SceneManager;
import it.rd.jpokebattle.controller.battle.BattleController;
import it.rd.jpokebattle.controller.battle.BattleNodeManager;
import it.rd.jpokebattle.controller.menu.MenuNodeManager;
import it.rd.jpokebattle.model.pokemon.*;
import it.rd.jpokebattle.model.profile.ProfileManager;
import it.rd.jpokebattle.util.audio.SoundManager;
import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.animation.PauseTransition;
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
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;

public final class ArcadeController extends Controller {

    private static SpawnZone currSpawnZone;
    private ArcadeNodeManager nodeMan = ArcadeNodeManager.getInstance();
    private SoundManager soundMan = SoundManager.getInstance();
    private Breed selectedBreed;


    @FXML
    protected Button area0Btn, area1Btn, area2Btn, teamPaneBackBtn;
    @FXML
    protected Label
            narratorLbl, profileNameLbl, clockLbl, locationLbl,
            info_nameLbl, info_lvLbl, info_xpLbl, info_hpLbl, info_attLbl,
            info_difLbl, info_sAttLbl, info_sDifLbl, info_velLbl, moveDescriptionLbl,
            moveTypeLbl, moveCatLbl, movePowLbl, movePriorityLbl, movePrecLbl, movPPLbl;
    @FXML
    protected ImageView avatarImageView, selectPrevImgView, info_avatarImgView;
    @FXML
    protected GridPane
            gameSettingsPane,
            teamPane,
            invenctoryPane,
            starterSelectionPane,
            pokeMartPane,
            selectionPreviewPane,
            pokemonInfoPane,
            moveInfoPane;

    @FXML
    protected ScrollPane narratorScrlPane;

    @FXML
    protected FlowPane teamCardsPane;

    @FXML
    protected VBox pokemonMovesVBox;



    public void initializeScene() {
        nodeMan.initializeNodes(getPlayer());
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
     * Mostra la schermata di visualizzazione dei pokemon in squadra.
     */
    @FXML
    public void ownedTeam(MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showOwnedTeam(getPlayer());
    }

    /**
     * Torna alla schermata di visualizzazione dei pokemon.
     */
    @FXML
    public void backToOwnedTeam(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.backToShowTeam();
    }

    /**
     *
     */
    @FXML
    public void backToPokemonDetails(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.showPokemonDetails();
        moveInfoPane.getChildren().removeLast();
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
     * Mostra la schermata di impostazioni.
     */
    @FXML
    public void settings (MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showSettingsPane();
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

        saveAll();
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
        Pokemon pkmn = PokemonManager.generatePokemon(selectedBreed, 5);
        getPlayer().addToOwned(PokemonManager.toOwnedPokemon(pkmn));
        nodeMan.hideAllPane();
        nextArea(0);
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
    public void pokemonDetails(MouseEvent e, OwnedPokemon pkmn) {
        soundMan.buttonClick();
        nodeMan.showPokemonDetails(pkmn);
    }

    /**
     *
     */
    @FXML
    public void nextAreaWithTypeCheck(ActionEvent e) {
        soundMan.buttonClick();
        int areaIndex = getNextAreaBtnID(e);
        checkSpecialArea(e, areaIndex);
    }

    /**
     *
     */
    public void nextArea(int areaIndex) {
        soundMan.buttonClick();
        getPlayer().goToNextArea(areaIndex);
        nodeMan.updateNarrationInterface(getPlayer().getCurrentArea());
    }

    /**
     *
     */
    public void specialArea() {
        soundMan.buttonClick();
        getPlayer().goToSpecialArea();
        nodeMan.updateNarrationInterface(getPlayer().getCurrentArea());
    }


    private int getNextAreaBtnID(ActionEvent e) {
        Node n = (Node) e.getSource();
        String btnID = n.getId();
        if (!(btnID == null))
            return Integer.parseInt("" + btnID.charAt(4)); // in posizione 4 nell'id del bottone c'è il suo numero di indice
        else
            return 1;
    }


    /**
     * Aggiorna il testo del narratore con la descrizione dell'area corrente in cui
     * il giocatore si trova.
     */
    private void checkSpecialArea(ActionEvent e, int areaIndex) {
        switch (getPlayer().getCurrentArea().getNextAreaType(areaIndex)) {
            case DEFAULT:
                nextArea(areaIndex);
                break;
            case TALL_GRASS:
                int foundValue = rand.nextInt(0, 100);
                if (foundValue < 70)
                    specialArea();
                else
                    nextArea(areaIndex);
                break;
            case HEAL:
                getPlayer().healTeam();
                nodeMan.updateNarratorLbl("La tua squadra adesso è in perfette condizioni!");
                break;
            case TRADE:
                pokeMartPane.setVisible(true); // TODO
                break;
            case BATTLE:
                area0Btn.setDisable(true);
                currSpawnZone = SpawnZone.fromName(getPlayer().getCurrentArea().getNameID());
                startBattle(e);
                break;
            case TRY_ESCAPE:
                int succesValue = rand.nextInt(0, 100);
                if (succesValue < 70) {
                    nodeMan.updateNarratorLbl("Non sei riuscito a seminare il pokémon, ora sei costretto a combattere!");
                    area1Btn.setVisible(false);
                } else {
                    nodeMan.updateNarratorLbl("Sei riuscito a seminare il pokémon!");
                    nextArea(areaIndex);
                }
                break;
            case STARTER_SELECT:
                starterSelectionPane.setVisible(true);
                break;
        }
    }

    private void startBattle(ActionEvent e) {
        nodeMan.startClosingAnimation();

        // Prima di passare alla scena successiva attendo che la transizione sia terminata
        PauseTransition pause = new PauseTransition(Duration.seconds(1.4));
        pause.setOnFinished(ev -> switchToBattleScene(e));
        pause.play();

        saveAll();
    }

    private void switchToBattleScene(ActionEvent e) {
        try {
            FXMLLoader loader = SceneManager.switchScene(e, "fxml.battle", "css.battle");
            NodeManager.setRoot(loader.getRoot());
            BattleController battleCtrl = loader.getController();
            BattleNodeManager.setController(battleCtrl);
            battleCtrl.initializeWildBattleScene(PokemonManager.spawnPokemon(currSpawnZone));

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void saveAll() {
        getPlayer().setNarratorTextHistory(narratorLbl.getText());
        ProfileManager.save(getPlayer());
        PokemonManager.save();
    }
}
