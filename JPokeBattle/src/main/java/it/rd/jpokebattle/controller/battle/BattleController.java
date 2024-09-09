package it.rd.jpokebattle.controller.battle;

import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class BattleController {
    private static BattleNodeManager nodeMan = BattleNodeManager.getIstance();
    private static Profile player;
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
            playerPkmnNameLbl, opponentPkmnNameLbl, playerPkmnLvLbl, opponentPkmnLvLbl,
            currHPLbl, logLbl;



    public static void setWildBattle(Profile playerProf, Pokemon wildPkmn) {
        player = playerProf;
        wildPokemon = wildPkmn;
        playerPokemon = player.getFirstPokemon();
    }


    public void initializeWildBattleScene() {
        nodeMan.initializeNodes(playerPokemon, wildPokemon);

        for (int id : player.getOwnedPokemonIDs()) {
            ImageView pokeball = new ImageView(ResourceLoader.loadImage("img.pokeCount"));
            pokeball.setPreserveRatio(true);
            pokeball.setFitWidth(30);
            pokeCounterBox.getChildren().add(pokeball);
        }


    }

}
