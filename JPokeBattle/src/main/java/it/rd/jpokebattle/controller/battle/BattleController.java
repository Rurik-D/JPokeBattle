package it.rd.jpokebattle.controller.battle;

import it.rd.jpokebattle.controller.Controller;
import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.controller.SceneManager;
import it.rd.jpokebattle.controller.arcade.ArcadeController;
import it.rd.jpokebattle.controller.arcade.ArcadeNodeManager;
import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.model.move.MoveCategory;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.model.pokemon.Stats;
import it.rd.jpokebattle.util.audio.SoundManager;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;


public class BattleController extends Controller {
    private static BattleNodeManager nodeMan = BattleNodeManager.getIstance();
    private SoundManager soundMan = SoundManager.getInstance();
    private PauseTransition delay_3_0 = new PauseTransition(Duration.seconds(3.0));
    private PauseTransition delay_2_0 = new PauseTransition(Duration.seconds(2.0));
    private PauseTransition delay2_2_0 = new PauseTransition(Duration.seconds(2.0));
    private PauseTransition delay3_2_0 = new PauseTransition(Duration.seconds(2.0));
    private PauseTransition delay_1_5 = new PauseTransition(Duration.seconds(1.5));
    private PauseTransition delay_1_0 = new PauseTransition(Duration.seconds(1.0));
    private static OwnedPokemon playerPokemon;
    private static Pokemon opponentPokemon;
    private static Turn firstAttacker;
    private boolean running;

    @FXML
    protected GridPane rootPane, labelsPane, buttonsPane, bagPane, pkmnPane;

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




    public void initializeWildBattleScene(Pokemon oppPkmn) {
        opponentPokemon = oppPkmn;
        playerPokemon = getPlayer().getFirstPokemon();
        running = true;

        nodeMan.initializeNodes(playerPokemon, opponentPokemon);
        nodeMan.updatePokeCounterBox(getPlayer());
    }



    @FXML
    public void escape(MouseEvent e) {
        soundMan.buttonClick();
        getPlayer().goToLastSafeArea();
        switchToArcadeScene();
    }

    @FXML
    public void openBag(MouseEvent e) {
        soundMan.buttonClick();
        bagPane.setVisible(true);
    }

    @FXML
    public void pkmnTeam(MouseEvent e) {
        soundMan.buttonClick();
        pkmnPane.setVisible(true);
    }

    @FXML
    public void backToGame(ActionEvent e) {
        soundMan.buttonClick();
        bagPane.setVisible(false);
        pkmnPane.setVisible(false);
    }


    /**
     * MOTORE DELLA FOTTUTISSIMA CLASSE
     *
     * @param e
     */
    public void move(MouseEvent e) {
        Node moveCard = (Node) e.getSource();
        int playerMoveIndex = getMoveIndexFromPaneID(moveCard.getId());
        int opponentMoveIndex;

        if (isMoveAvailable(playerPokemon, playerMoveIndex)) {
            soundMan.buttonClick();
            opponentMoveIndex = getOpponentMoveIndex();
            battlePhase(playerMoveIndex, opponentMoveIndex);

        }
    }

    private void battlePhase(int plMvIndex, int oppMvIndex) {
        buttonsPane.setDisable(true);
        updateFirstAttacker(plMvIndex, oppMvIndex);
        boolean isSecondMove = false;

        for (int i=0; i<2; i++) {
            switch (firstAttacker) {
                case PLAYER:
                    makeMove(playerPokemon, opponentPokemon, plMvIndex, isSecondMove);
                    firstAttacker = Turn.OPPONENT;
                    break;
                case OPPONENT:
                    makeMove(opponentPokemon, playerPokemon, oppMvIndex, isSecondMove);
                    firstAttacker = Turn.PLAYER;
                    break;
            }

            isSecondMove = true;
        }
    }

    private void makeMove(Pokemon pkmnAtt, Pokemon pkmnDef, int moveIndex, boolean isSecondMove) {
        if (isSecondMove) {
            delay_3_0.setOnFinished(e -> {
                makeMove(pkmnAtt, pkmnDef, moveIndex, false);
                delay_3_0.setOnFinished(ev -> {
                    buttonsPane.setDisable(!running);
                });
                delay_3_0.play();
            });
            delay_3_0.play();
        } else if (running){
            Move move = pkmnAtt.getMove(moveIndex);
            pkmnAtt.decresePP(moveIndex);
            nodeMan.updateLogLbl(pkmnAtt.getName() + " usa " + move.getName());

            delay_1_5.setOnFinished(ev -> {
                pkmnDef.takeDamage(calcDamage(pkmnAtt, pkmnDef, move));
                checkEndConditions();
            });
            delay_1_5.play();
        }
    }

    private int calcDamage(Pokemon attPkmn, Pokemon defPkmn, Move move) {
        int lv = attPkmn.getLevel();
        int pow = move.getPower();
        int atk = attPkmn.getStat(Stats.ATK);
        int def = defPkmn.getStat(Stats.DEF);
        int spAtk = attPkmn.getStat(Stats.SPEC_ATK);
        int spDef = defPkmn.getStat(Stats.SPEC_DEF);

        return switch(MoveCategory.fromName(move.getCategory())) {
            case PHYSICAL -> ((2 * lv / 5 + 2) * pow * atk / def) / 50 + 2;
            case SPECIAL ->  ((2 * lv / 5 + 2) * pow * spAtk / spDef) / 50 + 2;
            case STATE -> 0;
        };

    }


    private void checkEndConditions() {
        if (playerPokemon.getCurrHP() <= 0) {
            running = false;
            defeat();
        }

        else if (opponentPokemon.getCurrHP() <= 0) {
            running = false;
            victory();
        }

    }

    private void defeat() {
        delay3_2_0.setOnFinished(e2 -> {
            endGame(false);
        });


        delay2_2_0.setOnFinished(e2 -> {
            if (checkOtherPokemonAvailable()) {
                nodeMan.updateLogLbl("Hai altri pokemon disponibili");
            } else {
                nodeMan.updateLogLbl("Non hai altri pokemon disponibili");
                delay3_2_0.play();
            }
        });

        delay_2_0.setOnFinished(e1 -> {
            nodeMan.updateLogLbl(playerPokemon.getName() + " è esausto!");
            playerPkmnGif.setVisible(false);
            delay2_2_0.play();
        });

        delay_2_0.play();
    }

    private void victory() {
        delay3_2_0.setOnFinished(e2 -> {
            endGame(true);
        });

        delay2_2_0.setOnFinished(e2 -> {
            nodeMan.updateLogLbl(playerPokemon.getName() + " ha ottenuto " + 20 + "xp!");
            delay3_2_0.play();
        });

        delay_2_0.setOnFinished(e1 -> {
            nodeMan.updateLogLbl(opponentPokemon.getName() + " è esausto!");
            opponentPkmnGif.setVisible(false);
            delay2_2_0.play();
        });

        delay_2_0.play();
    }

    private void endGame(boolean victory) {
        if (victory){
            getPlayer().goToLastSafeArea();
            getPlayer().updateNarratorTextHistory("Complimenti, hai sconfitto " + opponentPokemon.getName() + " selvatico!");
        } else {
            getPlayer().setCurrentArea("home_living_room");
            getPlayer().updateNarratorTextHistory("Sei stato sconfitto da " + opponentPokemon.getName() + " selvatico... sei tornato a casa per curare la tua scuadra.");

        }
        switchToArcadeScene();
    }

    private boolean checkOtherPokemonAvailable() {
        for (Pokemon pkmn : getPlayer().getTeam()) {
            if (pkmn.getCurrHP() > 0)
                return true;
        }
        return false;
    }

    /**
     *
     * @param moveIndex
     * @return Se la mossa è stata o meno eseguita
     */
    private boolean isMoveAvailable(Pokemon pkmn, int moveIndex) {
        return (pkmn.getPP(moveIndex) > 0 && pkmn.getCurrHP() > 0);
    }

    private int getOpponentMoveIndex(){
        int mvSize = opponentPokemon.getMoves().size();
        int moveIndex;

        do moveIndex = rand.nextInt(0, mvSize);
        while (!isMoveAvailable(opponentPokemon, moveIndex));

        return moveIndex;
    }


    private int getMoveIndexFromPaneID(String name) {
        // move_0, move_1, move_2, move_3
        return Integer.parseInt(name.substring(name.length()-1));
    }
    
    /**
     * Ordine deciso prima in base alla priorità della mossa e in caso di parità in
     * base alla velocità del pokemon. A velocità uguale attacca prima l'avversario.
     * 
     * @param plMvIndex
     * @param oppMvIndex
     */
    private void updateFirstAttacker(int plMvIndex, int oppMvIndex) {
        int playerPriority = playerPokemon.getMove(plMvIndex).getPriority();
        int opponentPriority = opponentPokemon.getMove(oppMvIndex).getPriority();
        
        if (playerPriority == opponentPriority) {
            if (playerPokemon.getStat(Stats.SPEED) > opponentPokemon.getStat(Stats.SPEED)) {
                firstAttacker = Turn.PLAYER;
            } else {
                firstAttacker = Turn.OPPONENT;
            }
        } else if (playerPriority > opponentPriority) {
            firstAttacker = Turn.PLAYER;
        } else {
            firstAttacker = Turn.OPPONENT;
        }
    }


    private void switchToArcadeScene() {
        try {
            FXMLLoader loader = SceneManager.switchScene(rootPane, "fxml.arcade", "css.arcade");
            NodeManager.setRoot(loader.getRoot());
            ArcadeController arcadeCtrl = loader.getController();
            ArcadeNodeManager.setController(arcadeCtrl);
            arcadeCtrl.initializeScene();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
