package it.rd.jpokebattle.controller.arcade;

import com.almasb.fxgl.scene.Scene;
import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.controller.SceneManager;
import it.rd.jpokebattle.controller.battle.BattleController;
import it.rd.jpokebattle.controller.battle.BattleNodeManager;
import it.rd.jpokebattle.controller.menu.MenuNodeManager;
import it.rd.jpokebattle.model.area.Area;
import it.rd.jpokebattle.model.pokemon.*;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.model.profile.ProfileManager;
import it.rd.jpokebattle.util.audio.SoundManager;
import it.rd.jpokebattle.util.file.ResourceLoader;
import it.rd.jpokebattle.view.arcade.BattleTransition;
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
import java.util.Random;

public class ArcadeController {
    private static Profile player;
    private static SpawnZone currSpawnZone;
    private ArcadeNodeManager nodeMan = ArcadeNodeManager.getIstance();
    private SoundManager soundMan = SoundManager.getInstance();
    private Breed selectedBreed;
    private Random rand = new Random();


    @FXML
    protected Button area0Btn, area1Btn, area2Btn, teamPaneBackBtn, pokemonInfoPaneBackBtn;
    @FXML
    protected Label
            narratorLbl, profileNameLbl, clockLbl, locationLbl,
            info_nameLbl, info_lvLbl, info_xpLbl, info_hpLbl, info_attLbl, info_difLbl, info_sAttLbl, info_sDifLbl, info_velLbl;
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
            pokemonInfoPane;

    @FXML
    protected ScrollPane narratorScrlPane;

    @FXML
    protected FlowPane teamCardsPane;

    @FXML
    protected VBox pokemonMovesVBox;


    // private OwnedPokemon POKEMON_PROVA;


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
     * Mostra la schermata di visualizzazione dei pokemon in squadra.
     */
    @FXML
    public void ownedTeam(MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showOwnedTeam(player);
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
        Pokemon pkmn = PokemonManager.generatePokemon(selectedBreed, 8);
        player.addToOwned(PokemonManager.toOwnedPokemon(pkmn));
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
        player.goToNextArea(areaIndex);
        updateNarrator();
    }

    /**
     *
     */
    public void specialArea() {
        soundMan.buttonClick();
        player.goToSpecialArea();
        updateNarrator();
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
        switch (player.getCurrentArea().getNextAreaType(areaIndex)) {
            case DEFAULT:
                nextArea(areaIndex);
                break;
            case TALL_GRASS:
                int foundValue = rand.nextInt(0, 2);
                if (foundValue == 0)
                    nextArea(areaIndex);
                else
                    specialArea();
                break;
            case HEAL:
                player.healTeam();
                nodeMan.updateNarratorLbl("La tua squadra adesso è in perfette condizioni!");
                break;
            case TRADE:
                pokeMartPane.setVisible(true); // TODO
                break;
            case BATTLE:
                area0Btn.setDisable(true);
                currSpawnZone = SpawnZone.fromName(player.getCurrentArea().getNameID());
                startBattle(e);
                break;
            case TRY_ESCAPE:
                int succesValue = rand.nextInt(0, 2);
                if (succesValue == 0) {
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
        // Genero il pokemon avversario e preparo le variabili statiche nel nuovo controller
        Pokemon wildPokemon = PokemonManager.spawnPokemon(currSpawnZone);
        BattleController.setWildBattle(player, wildPokemon);

        // Salvo tutto
        player.setNarratorTextHistory(narratorLbl.getText());
        ProfileManager.save(player);
        PokemonManager.save();

        // Prendo il rootpane ed eseguo la transizione grafica della scena
        GridPane rootPane = (GridPane) SceneManager.getRootFromEvent(e);
        BattleTransition.getIstance().startCloseTrans(rootPane);

        // Prima di passare alla scena successiva attendo che la transizione sia terminata
        PauseTransition pause = new PauseTransition(Duration.seconds(1.4));
        pause.setOnFinished(ev -> {
                try {
                    FXMLLoader loader = SceneManager.switchScene(e, "fxml.battle", "css.battle");
                    BattleController contoller = loader.getController();
                    NodeManager.setRoot(loader.getRoot());
                    BattleNodeManager.setController(loader.getController());

                    contoller.initializeWildBattleScene();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        );
        pause.play();




    }

    /**
     * Aggiorna il testo del narratore con la descrizione dell'area corrente in cui
     * il giocatore si trova.
     */
    private void updateNarrator() {
        Area area = player.getCurrentArea();
        nodeMan.updateNarratorLbl(area.getDescription());
        nodeMan.updateNextAreaButtons(player.getCurrentArea());
        locationLbl.setText(player.getCurrentArea().getTitle());
        nodeMan.updateNarratorScrollbarPosition();
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
