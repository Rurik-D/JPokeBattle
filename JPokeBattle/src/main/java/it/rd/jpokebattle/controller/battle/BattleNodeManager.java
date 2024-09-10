package it.rd.jpokebattle.controller.battle;

import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.ResourceLoader;
import it.rd.jpokebattle.view.bar.LifeBar;
import it.rd.jpokebattle.view.bar.XpBar;
import it.rd.jpokebattle.view.battle.BattleMoveCard;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;

public class BattleNodeManager extends NodeManager {
    private static BattleNodeManager istance;
    private static BattleController ctrl;
    private final String SEPARATOR = "\n———————————————————————————————\n\n";

    private LifeBar oppLB = new LifeBar(184, 9);
    private LifeBar plrLB = new LifeBar(184, 9);
    private XpBar xpBar = new XpBar(340, 3);
    private Label currHPLbl = new Label();
    private Label plrLvLbl = new Label();
    private Label oppLvLbl = new Label();



    private BattleNodeManager() { }

    public static BattleNodeManager getIstance() {
        if (istance == null)
            return new BattleNodeManager();
        return null;
    }

    public static void setController(BattleController controller) {
        ctrl = controller;
    }

    public void initializeNodes(OwnedPokemon playerPkmn, Pokemon opponentPkmn) {
        setLabels(playerPkmn, opponentPkmn);
        setMovesPane(playerPkmn);
        updateLogLbl("È apparso un " + opponentPkmn.getName() + " selvatico!");
        startOpeningAnimation();
    }

    private void setLabels(OwnedPokemon playerPkmn, Pokemon opponentPkmn) {
        ctrl.playerPkmnGif.setImage(playerPkmn.getBreed().getBackGif());
        ctrl.opponentPkmnGif.setImage(opponentPkmn.getBreed().getFrontGif());
        ctrl.playerPkmnNameLbl.setText(playerPkmn.getName());
        ctrl.opponentPkmnNameLbl.setText(opponentPkmn.getName());

        ///////////////////////////////////////////////////////////////

        oppLB.setListener(opponentPkmn);
        plrLB.setListener(playerPkmn);

        ctrl.labelsPane.add(oppLB, 0, 0, 2, 1);
        ctrl.labelsPane.add(plrLB, 3, 2, 1, 1);

        oppLB.setTranslateX(125.5);
        oppLB.setTranslateY(26.5);
        plrLB.setTranslateX(106.5);
        plrLB.setTranslateY(26.5);

        ///////////////////////////////////////////////////////////////

        xpBar.setListener(playerPkmn);

        ctrl.labelsPane.add(xpBar, 3, 2, 2, 1);

        xpBar.setTranslateX(30);
        xpBar.setTranslateY(42);

        ///////////////////////////////////////////////////////////////

        currHPLbl.textProperty().bind(Bindings.format("%d/%d",
                playerPkmn.currHPProperty(),
                playerPkmn.maxHPProperty()
        ));

        ctrl.labelsPane.add(currHPLbl, 4, 2, 1, 1);
        GridPane.setValignment(currHPLbl, VPos.BOTTOM);
        currHPLbl.setTranslateX(-30);
        currHPLbl.setTranslateY(-30);

        ///////////////////////////////////////////////////////////////

        plrLvLbl.textProperty().bind(Bindings.format("Lv %d",
                playerPkmn.currLevelProperty()
        ));

        ctrl.labelsPane.add(plrLvLbl, 4, 2, 1, 1);
        GridPane.setHalignment(plrLvLbl, HPos.CENTER);
        plrLvLbl.setTranslateX(30);
        plrLvLbl.setTranslateY(-5);

        ///////////////////////////////////////////////////////////////

        oppLvLbl.textProperty().bind(Bindings.format("Lv %d",
                opponentPkmn.currLevelProperty()
        ));

        ctrl.labelsPane.add(oppLvLbl, 1, 0, 1, 1);
        GridPane.setHalignment(oppLvLbl, HPos.CENTER);
        oppLvLbl.setTranslateX(50);
        oppLvLbl.setTranslateY(-5);


        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(ev -> {
            playerPkmn.increaseXP(100);


        });
        pause.play();

    }

    private void setMovesPane(OwnedPokemon pkmn) {
        ArrayList<Move> moves = pkmn.getMoves();
        ArrayList<Integer> PPs = pkmn.getPPs();
        int size = moves.size();

        for (int i=0; i<size; i++) {
            ctrl.movesPane.getChildren().add(new BattleMoveCard(moves.get(i), PPs.get(i)));
        }

    }

    private void startOpeningAnimation() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1.6));
        pause.setOnFinished(ev -> {
            ctrl.rootPane.getChildren().remove(ctrl.transitionGif);
            ctrl.labelsPane.setVisible(true);
            ctrl.buttonsPane.setVisible(true);
            ctrl.logScrollPane.setVisible(true);
        });
        pause.play();
    }

    public void updateLogLbl(String text) {
        text = ctrl.logLbl.getText() + SEPARATOR + text + "\n";
        ctrl.logLbl.setText(text);
    }

    public void updatePokeCounterBox(Profile player) {
        for (int id : player.getOwnedPokemonIDs()) {
            ImageView pokeball = new ImageView(ResourceLoader.loadImage("img.pokeCount"));
            pokeball.setPreserveRatio(true);
            pokeball.setFitWidth(30);
            ctrl.pokeCounterBox.getChildren().add(pokeball);
        }

    }
}
