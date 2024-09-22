package it.rd.jpokebattle.controller.battle;

import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.ResourceLoader;
import it.rd.jpokebattle.view.arcade.PokemonCard;
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
    private static BattleNodeManager instance;
    private static BattleController ctrl;
    private final String SEPARATOR = " ————————————————————————————————————\n\n";
    private LifeBar oppLB = new LifeBar(184, 9);
    private LifeBar plrLB = new LifeBar(184, 9);
    private XpBar xpBar = new XpBar(340, 3);
    private Label currHPLbl = new Label();
    private Label plrLvLbl = new Label();
    private Label oppLvLbl = new Label();


    private BattleNodeManager() { }

    /**
     * Restituisce una nuova istanza se non ne esiste già una, altrimenti torna quella giò
     * esistente.
     *
     * @return Unica istanza della classe
     */
    public static BattleNodeManager getInstance() {
        if (instance == null)
            return new BattleNodeManager();
        return null;
    }

    /**
     * Imposta il controller a cui il Node Manager fa riferimento.
     *
     * @param controller
     */
    public static void setController(BattleController controller) {
        ctrl = controller;
    }


    /**
     * Inizializza tutti i nodi della label. Da chiamare all'inizio di una nuova lotta.
     *
     * @param playerPkmn Pokemon del giocatore
     * @param opponentPkmn Pokemon avversario
     */
    protected void initializeNodes(OwnedPokemon playerPkmn, Pokemon opponentPkmn) {
        setLabels(playerPkmn, opponentPkmn);
        setMovesPane(playerPkmn);
        updateLogLbl(String.format("È apparso un %s!", opponentPkmn.getName()));
        startOpeningAnimation();
    }

    /**
     * Aggiorna il contenuto della log label (ovvero la label, all'interno del relativo scroll
     * pane, che mostra la sequenza azioni che si susseguono durante lo scontro)
     *
     * @param text Testo da aggiungere alla log label
     */
    protected void updateLogLbl(String text) {
        text = ctrl.logLbl.getText() + SEPARATOR + text + "\n\n";
        ctrl.logLbl.setText(text);
        updateNarratorScrollbarPosition(ctrl.logScrollPane);
    }

    /**
     * Aggiorna il contatore dei pokemon posseduti (le piccole pokeball) sotto la barra
     * della vita.
     *
     * @param player Profilo del giocatore
     */
    protected void updatePokeCounterBox(Profile player) {
        for (int id : player.getOwnedPokemonIDs()) {
            ImageView pokeball = new ImageView(ResourceLoader.loadImage("img.pokeCount"));
            pokeball.setPreserveRatio(true);
            pokeball.setFitWidth(30);
            ctrl.pokeCounterBox.getChildren().add(pokeball);
        }
    }

    protected void showPkmnPane(Profile player) {
        ctrl.pkmnPane.setVisible(true);

        for (OwnedPokemon pkmn : player.getTeam()) {
            PokemonCard card = new PokemonCard(pkmn);
//            card.setOnMouseClicked(e -> ctrl.pokemonDetails(e, pkmn));
            ctrl.teamCardsPane.getChildren().add(card);
        }
    }

    protected void backToGame() {
        ctrl.bagPane.setVisible(false);
        ctrl.pkmnPane.setVisible(false);
        ctrl.teamCardsPane.getChildren().clear();
    }

    /**
     * Imposta tutte le label della scena.
     *
     * @param playerPkmn Pokemon del giocatore
     * @param opponentPkmn Pokemon avversario
     */
    private void setLabels(OwnedPokemon playerPkmn, Pokemon opponentPkmn) {
        ctrl.playerPkmnGif.setImage(playerPkmn.getBreed().getBackGif());
        ctrl.opponentPkmnGif.setImage(opponentPkmn.getBreed().getFrontGif());
        ctrl.playerPkmnNameLbl.setText(playerPkmn.getName());
        ctrl.opponentPkmnNameLbl.setText(opponentPkmn.getBreed().getName());
        setLifeBars(playerPkmn, opponentPkmn);
        setPlayerXPBar(playerPkmn);
        setCurrentPlayerHPLabel(playerPkmn);
        setLevelLabels(playerPkmn, opponentPkmn);
    }

    /**
     * Imposta le barre della vita per giocatore e avversario. Le barre della vita hanno colore
     * e lunghezza variabile in base al valore della salute attuale e massima del pokemon relativo.
     *
     * @param playerPkmn Pokemon del giocatore
     * @param opponentPkmn Pokemon avversario
     */
    private void setLifeBars(OwnedPokemon playerPkmn, Pokemon opponentPkmn) {
        // Player life bar
        plrLB.setListener(playerPkmn);
        plrLB.setVisible(true);
        ctrl.labelsPane.add(plrLB, 3, 2, 1, 1);
        plrLB.setTranslateX(106.5);
        plrLB.setTranslateY(26.5);

        // Opponent life bar
        oppLB.setListener(opponentPkmn);
        oppLB.setVisible(true);
        ctrl.labelsPane.add(oppLB, 0, 0, 2, 1);
        oppLB.setTranslateX(125.5);
        oppLB.setTranslateY(26.5);
    }

    /**
     * Imposta la barra dell'esperienza del pokemon del giocatore.
     *
     * @param playerPkmn Pokemon del giocatore
     */
    private void setPlayerXPBar(OwnedPokemon playerPkmn) {
        xpBar.setListener(playerPkmn);
        ctrl.labelsPane.add(xpBar, 3, 2, 2, 1);
        xpBar.setTranslateX(30);
        xpBar.setTranslateY(42);

    }

    /**
     * Imposta la label contenente le informazioni di salute attuale e salute massima del
     * pokemon del giocatore.
     *
     * @param playerPkmn Pokemon del giocatore
     */
    private void setCurrentPlayerHPLabel(OwnedPokemon playerPkmn) {
        currHPLbl.textProperty().bind(Bindings.format("%d/%d",
                playerPkmn.currHPProperty(),
                playerPkmn.maxHPProperty()
        ));

        ctrl.labelsPane.add(currHPLbl, 4, 2, 1, 1);
        GridPane.setValignment(currHPLbl, VPos.BOTTOM);
        currHPLbl.setTranslateX(-30);
        currHPLbl.setTranslateY(-30);
    }

    /**
     * Imposta le Label del livello per giocatore e avversario.
     * La label del pokemon del giocatore è legata al livello corrente del pokemon in questione.
     *
     * @param playerPkmn Pokemon del giocatore
     * @param opponentPkmn Pokemon avversario
     */
    private void setLevelLabels(OwnedPokemon playerPkmn, Pokemon opponentPkmn) {
        // Player level label
        plrLvLbl.textProperty().bind(Bindings.format("Lv %d", playerPkmn.currLevelProperty()));
        ctrl.labelsPane.add(plrLvLbl, 4, 2, 1, 1);
        GridPane.setHalignment(plrLvLbl, HPos.CENTER);
        plrLvLbl.setTranslateX(30);
        plrLvLbl.setTranslateY(-5);

        // Opponent level label
        oppLvLbl.setText("Lv " + opponentPkmn.getLevel());
        ctrl.labelsPane.add(oppLvLbl, 1, 0, 1, 1);
        GridPane.setHalignment(oppLvLbl, HPos.CENTER);
        oppLvLbl.setTranslateX(50);
        oppLvLbl.setTranslateY(-5);
    }

    /**
     * Imposta il pannello contenente le mosse del pokemon del giocatore.
     * Ogni mossa è un Grid Pane cliccabile. Se cliccato avvia la Battle Phase (vedi nel controller).
     *
     * @param pkmn Pokemon dal quale prendere le mosse da visualizzare
     * @see BattleController
     */
    private void setMovesPane(OwnedPokemon pkmn) {
        ArrayList<Move> moves = pkmn.getMoves();
        ArrayList<Integer> PPs = pkmn.getPPs();
        int size = moves.size();

        for (int i=0; i<size; i++) {
            BattleMoveCard moveCard = new BattleMoveCard(pkmn, i);
            moveCard.setUserData(moves.get(i));
            moveCard.setId("move_" + i);
            moveCard.setOnMouseClicked(e -> ctrl.move(e));
            ctrl.movesPane.getChildren().add(moveCard);
        }

    }

    /**
     * Avvia l'animazione di apertura della scena.
     */
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
}
