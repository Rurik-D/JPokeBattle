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

/**
 * Classe che gestisce la logica e l'interfaccia utente della modalità Arcade.
 * Consente al giocatore di navigare tra aree, gestire i Pokémon posseduti,
 * accedere all'inventario, alle impostazioni e di effettuare battaglie.
 */
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


    /**
     * Inizializza la scena Arcade, configurando i nodi e aggiornando i Pokémon del giocatore.
     */
    public void initializeScene() {
        nodeMan.initializeNodes(getPlayer());
        PokemonManager.refreshPlayerPkmnsProperties(getPlayer());
        soundMan.switchTrack(getPlayer().getCurrentArea().getMusicSrcName());
    }



    /**
     * Nasconde tutti i pannelli e torna alla schermata di gioco attuale.
     *
     * @param e L'evento di azione associato.
     */
    @FXML
    public void backToGame (ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.hideAllPane();
    }

    /**
     * Mostra la schermata di visualizzazione dei Pokémon in squadra.
     *
     * @param e L'evento di mouse associato.
     */
    @FXML
    public void ownedTeam(MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showTeamPane(getPlayer());
    }

    /**
     * Torna alla schermata di visualizzazione dei Pokémon in squadra.
     *
     * @param e L'evento di azione associato.
     */
    @FXML
    public void backToOwnedTeam(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.backToShowTeam();
    }

    /**
     * Torna alla schermata dei dettagli del Pokémon selezionato.
     *
     * @param e L'evento di azione associato.
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
     *
     * @param e L'evento di mouse associato.
     */
    @FXML
    public void invenctory(MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showInvenctory();
    }

    /**
     * Mostra la schermata delle impostazioni.
     *
     * @param e L'evento di mouse associato.
     */
    @FXML
    public void settings (MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showSettingsPane();
    }


    /**
     * Torna al menu principale, aggiornando i file di salvataggio.
     *
     * @param e L'evento di azione associato.
     * @throws IOException Se si verifica un errore nel caricamento della nuova scena.
     */
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
     * TODO: DA IMPLEMENTARE
     * Switch del volume.
     *
     * @param e Azione generica sul bottone.
     */
    @FXML
    public void volume(ActionEvent e) {
        soundMan.buttonClick();
        soundMan.toggleVolume();
    }



    /**
     * Seleziona un Pokémon iniziale e aggiorna l'anteprima.
     *
     * @param e L'evento di mouse associato.
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
     * Conferma la selezione del Pokémon iniziale e passa alla prima area.
     *
     * @param e L'evento di azione associato.
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
     * Annulla la selezione dello starter e torna alla schermata precedente.
     */
    @FXML
    public void cancelSelection(ActionEvent e) {
        soundMan.buttonClick();
        selectionPreviewPane.setVisible(false);
    }

    /**
     * Aggiorna l'interfaccia per mostrare i dettagli di un Pokémon.
     *
     * @param e L'evento di mouse associato.
     * @param pkmn Il Pokémon selezionato.
     */
    public void pokemonDetails(MouseEvent e, OwnedPokemon pkmn) {
        soundMan.buttonClick();
        nodeMan.showPokemonDetails(pkmn);
    }

    /**
     * Gestisce il passaggio alla prossima area, con controlli aggiuntivi in
     * caso di presenza di aree speciali.
     *
     * @param e L'evento di azione associato.
     */
    @FXML
    public void nextAreaWithTypeCheck(ActionEvent e) {
        soundMan.buttonClick();
        int areaIndex = getNextAreaBtnID(e);
        checkSpecialArea(e, areaIndex);
    }

    /**
     * Passa il giocatore all'area successiva.
     *
     * @param areaIndex L'indice dell'area successiva.
     */
    public void nextArea(int areaIndex) {
        soundMan.buttonClick();
        getPlayer().goToNextArea(areaIndex);
        nodeMan.updateNarrationInterface(getPlayer().getCurrentArea());
        soundMan.switchTrack(getPlayer().getCurrentArea().getMusicSrcName());
    }

    /**
     * Gestisce il passaggio a una zona speciale (es. erba alta, guarigione, ecc).
     */
    public void specialArea() {
        soundMan.buttonClick();
        getPlayer().goToSpecialArea();
        nodeMan.updateNarrationInterface(getPlayer().getCurrentArea());
        soundMan.switchTrack(getPlayer().getCurrentArea().getMusicSrcName());
    }

    /**
     * Ottiene l'indice dell'area successiva dal pulsante premuto.
     *
     * @param e L'evento di azione associato.
     * @return L'indice dell'area successiva.
     */
    private int getNextAreaBtnID(ActionEvent e) {
        Node n = (Node) e.getSource();
        String btnID = n.getId();
        if (!(btnID == null))
            return Integer.parseInt("" + btnID.charAt(4)); // in posizione 4 nell'id del bottone c'è il suo numero di indice
        else
            return 1;
    }
    /**
     * Controlla il tipo di area successiva e gestisce l'azione appropriata.
     *
     * @param e         L'evento di azione associato.
     * @param areaIndex L'indice dell'area da verificare.
     */
    private void checkSpecialArea(ActionEvent e, int areaIndex) {
        switch (getPlayer().getCurrentArea().getNextAreaType(areaIndex)) {
            case DEFAULT:
                nextArea(areaIndex);
                break;
            case TALL_GRASS:
                int foundValue = rand.nextInt(0, 100);
                if (foundValue < 80)
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
                soundMan.switchTrack("mp3.battle");
                startBattle(e);
                break;
            case TRY_ESCAPE:
                int succesValue = rand.nextInt(0, 100);
                if (succesValue < 85) {
                    nodeMan.updateNarratorLbl("Non sei riuscito a seminare il pokémon, ora sei costretto a combattere!");
                    area1Btn.setVisible(false);
                } else {
                    nodeMan.updateNarratorLbl("Sei riuscito a seminare il pokémon!");
                    getPlayer().goToLastSafeArea();
                    nodeMan.updateNarrationInterface(getPlayer().getCurrentArea());
                }
                break;
            case STARTER_SELECT:
                starterSelectionPane.setVisible(true);
                break;
            case NEW_POKEMON:
                nextArea(areaIndex);
                nodeMan.updateNarratorLbl("Un nuovo Pokémon si è unito alla tua squadra!");
                currSpawnZone = SpawnZone.fromName(getPlayer().getCurrentArea().getSpecialAreaName());

                // Se il numero di pokemon posseduti è uguale all'indice dell'area, genera un nuovo pokemon posseduto da aggiungere agli altri del team
                if (getPlayer().getTeam().size() == getPlayer().getCurrentArea().getSectionIndex())
                    getPlayer().addToOwned(PokemonManager.toOwnedPokemon(PokemonManager.spawnOwnedPokemon(currSpawnZone)));
                break;
        }
    }

    /**
     * Avvia una battaglia, preparando la transizione verso la scena della battaglia.
     *
     * @param e L'evento di azione associato.
     */
    private void startBattle(ActionEvent e) {
        nodeMan.startClosingAnimation();
        saveAll();

        // Prima di passare alla scena successiva attendo che la transizione sia terminata
        PauseTransition pause = new PauseTransition(Duration.seconds(1.4));
        pause.setOnFinished(ev -> switchToBattleScene(e));
        pause.play();
    }

    /**
     * Passa alla scena di battaglia e la inizializza.
     *
     * @param e L'evento di azione associato.
     */
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

    /**
     * Salva lo stato attuale del giocatore e dei Pokémon.
     */
    private void saveAll() {
        getPlayer().setNarratorTextHistory(narratorLbl.getText());
        ProfileManager.save(getPlayer());
        PokemonManager.save();
    }
}
