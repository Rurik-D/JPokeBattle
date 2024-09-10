package it.rd.jpokebattle.controller.battle;

import it.rd.jpokebattle.controller.Controller;
import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.controller.SceneManager;
import it.rd.jpokebattle.controller.arcade.ArcadeController;
import it.rd.jpokebattle.controller.arcade.ArcadeNodeManager;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.util.audio.SoundManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

import java.io.IOException;


public class BattleController extends Controller {
    private static BattleNodeManager nodeMan = BattleNodeManager.getIstance();
    private SoundManager soundMan = SoundManager.getInstance();
    private static OwnedPokemon playerPokemon;
    private static Pokemon wildPokemon;

    @FXML
    protected GridPane rootPane, labelsPane, buttonsPane;

    @FXML
    protected ScrollPane logScrollPane;

    @FXML
    protected FlowPane movesPane;

    @FXML
    protected HBox pokeCounterBox;

    @FXML
    protected ImageView playerPkmnGif, opponentPkmnGif, transitionGif;

    @FXML
    protected Label
            playerPkmnNameLbl, opponentPkmnNameLbl, logLbl;




    public void initializeWildBattleScene(Pokemon wildPkmn) {
        wildPokemon = wildPkmn;
        playerPokemon = getPlayer().getFirstPokemon();

        nodeMan.initializeNodes(playerPokemon, wildPokemon);
        nodeMan.updatePokeCounterBox(getPlayer());
    }



    @FXML
    public void escape(MouseEvent e) {
        getPlayer().goToLastSafeArea();
        switchToArcadeScene(e);
    }



    private void switchToArcadeScene(Event e) {
        try {
            FXMLLoader loader = SceneManager.switchScene(e, "fxml.arcade", "css.arcade");
            NodeManager.setRoot(loader.getRoot());
            ArcadeController arcadeCtrl = loader.getController();
            ArcadeNodeManager.setController(arcadeCtrl);
            arcadeCtrl.initializeScene();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
