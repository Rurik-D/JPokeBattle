package it.rd.jpokebattle.controller.battle;

import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.model.pokemon.Stats;
import it.rd.jpokebattle.view.battle.BattleMoveCard;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.ArrayList;

public class BattleNodeManager extends NodeManager {
    private static BattleNodeManager istance;
    private static BattleController ctrl;

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

        ctrl.playerPkmnGif.setImage(playerPkmn.getBreed().getBackGif());
        ctrl.opponentPkmnGif.setImage(opponentPkmn.getBreed().getFrontGif());
        ctrl.playerPkmnNameLbl.setText(playerPkmn.getName());
        ctrl.opponentPkmnNameLbl.setText(opponentPkmn.getName());
        ctrl.playerPkmnLvLbl.setText("Lv " + playerPkmn.getLevel());
        ctrl.opponentPkmnLvLbl.setText("Lv " + opponentPkmn.getLevel());
        ctrl.currHPLbl.setText(playerPkmn.getCurrHP() + "/" + playerPkmn.getStat(Stats.HP));

        ctrl.logLbl.setText("È apparso un " + opponentPkmn.getName() + " selvatico!");


        ArrayList<Move> moves = playerPkmn.getMoves();
        ArrayList<Integer> PPs = playerPkmn.getPPs();
        int size = moves.size();

        for (int i=0; i<size; i++) {
            ctrl.movesPane.getChildren().add(new BattleMoveCard(moves.get(i), PPs.get(i)));
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(1.6));
        pause.setOnFinished(ev -> {
            ctrl.rootPane.getChildren().remove(ctrl.transitionGif);
            ctrl.labelsPane.setVisible(true);
            ctrl.buttonsPane.setVisible(true);
            ctrl.logScrollPane.setVisible(true);
        });

        pause.play();
    }
}
