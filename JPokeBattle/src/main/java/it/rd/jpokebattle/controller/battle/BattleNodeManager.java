package it.rd.jpokebattle.controller.battle;

import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.model.move.MoveCategory;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.ResourceLoader;
import it.rd.jpokebattle.view.arcade.MoveCard;
import it.rd.jpokebattle.view.arcade.PokemonCard;
import it.rd.jpokebattle.view.bar.LifeBar;
import it.rd.jpokebattle.view.bar.XpBar;
import it.rd.jpokebattle.view.battle.BattleMoveCard;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;

import static it.rd.jpokebattle.model.pokemon.Stats.*;
import static it.rd.jpokebattle.model.pokemon.Stats.SPEED;

/**
 * Classe che gestisce la creazione e la visualizzazione di nodi nella scena di lotta.
 */
public final class BattleNodeManager extends NodeManager {
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

    /**
     * Mostra il pannello contenente le card di tutti i pokemon posseduti.
     *
     * @param player Giocatore da cui poter prendere i pokemon
     */
    protected void showTeamPane(Profile player) {
        ctrl.teamPane.setVisible(true);

        for (OwnedPokemon pkmn : player.getTeam()) {
            PokemonCard card = new PokemonCard(pkmn);
            card.setOnMouseClicked(e -> ctrl.pokemonDetails(e, pkmn));
            ctrl.teamCardsPane.getChildren().add(card);
        }
    }

    /**
     * Mostra i dettagli di un Pokemon specifico.
     */
    public void showPokemonDetails(OwnedPokemon pkmn) {
        ctrl.teamCardsPane.setVisible(false);
        ctrl.teamPaneBackBtn.setVisible(false);
        ctrl.pokemonInfoPane.setVisible(true);
        updatePokemonInfoPane(pkmn);

        ArrayList<Move> moves = pkmn.getMoves();
        ArrayList<Integer> pps = pkmn.getPPs();
        int size = Math.min(moves.size(), 4);

        for (int i=0; i<size; i++) {
            Move move = moves.get(i);
            int pp = pps.get(i);
            MoveCard mc = new MoveCard(move, pp);
            mc.setOnMouseClicked(e -> showMoveInfoPane(move, pp));
            ctrl.pokemonMovesVBox.getChildren().add(mc);
        }
    }

    /**
     * Mostra i dettagli del Pokemon senza specificarne uno in particolare.
     */
    public void showPokemonDetails() {
        ctrl.teamCardsPane.setVisible(false);
        ctrl.teamPaneBackBtn.setVisible(false);
        ctrl.moveInfoPane.setVisible(false);
        ctrl.pokemonInfoPane.setVisible(true);
    }

    /**
     * Mostra i dettagli di una mossa specifica.
     */
    private void showMoveInfoPane(Move move, int pp) {
        ctrl.pokemonInfoPane.setVisible(false);
        ctrl.moveInfoPane.setVisible(true);
        MoveCard mc = new MoveCard(move, pp);
        mc.setCursor(Cursor.DEFAULT);
        ctrl.moveInfoPane.getChildren().add(mc);
        GridPane.setValignment(mc, VPos.BOTTOM);
        GridPane.setHalignment(mc, HPos.RIGHT);

        ctrl.moveDescriptionLbl.setText(move.getDescription());
        ctrl.moveTypeLbl.setText("Tipo: " + move.getType().getFormattedName());
        ctrl.moveCatLbl.setText("Categoria: " + MoveCategory.getFormattedName(move.getCategory()));
        ctrl.movePowLbl.setText("Potenza: " + move.getPower());
        ctrl.movePriorityLbl.setText("Priorità: " + move.getPriority());
        ctrl.movePrecLbl.setText("Precisione: " + (int) move.getPrecision());
        ctrl.movPPLbl.setText("PP: " + move.getPP());

    }

    /**
     * Torna alla visualizzazione del team dopo aver mostrato i dettagli di un Pokemon.
     */
    public void backToShowTeam() {
        ctrl.pokemonInfoPane.setVisible(false);
        ctrl.teamCardsPane.setVisible(true);
        ctrl.teamPaneBackBtn.setVisible(true);
        ctrl.pokemonMovesVBox.getChildren().clear();
    }

    /**
     * Aggiorna i dettagli del Pokemon visualizzati.
     */
    private void updatePokemonInfoPane(OwnedPokemon pkmn) {
        ctrl.info_avatarImgView.setImage(pkmn.getBreed().getAvatar());
        ctrl.info_nameLbl.setText("Nome: " + pkmn.getBreed().getName());
        ctrl.info_lvLbl.setText("Livello: " + pkmn.getLevel());
        ctrl.info_xpLbl.setText("Esperienza: " + pkmn.getXp());
        ctrl.info_hpLbl.setText("Punti Salute: " + pkmn.getStat(HP));
        ctrl.info_attLbl.setText("Attacco: " + pkmn.getStat(ATK));
        ctrl.info_difLbl.setText("Difesa: " + pkmn.getStat(DEF));
        ctrl.info_sAttLbl.setText("Att. Speciale: " + pkmn.getStat(SPEC_ATK));
        ctrl.info_sDifLbl.setText("Dif. Speciale: " + pkmn.getStat(SPEC_DEF));
        ctrl.info_velLbl.setText("Velocità: " + pkmn.getStat(SPEED));
    }

    /**
     * Mostra il pannelo delle impostazioni.
     */
    public void showSettingsPane() {
        ctrl.gameSettingsPane.setVisible(true);
    }


    /**
     * Mostra il pannello che consente l'aggiunta/la modifica delle mosse quando il pokemon
     * apprende una nuova mossa
     *
     * @param pkmn Pokemon da cui prendere le mosse conosciute
     * @param newMove Nuova mossa da imparare
     */
    protected void showNewMovePane(OwnedPokemon pkmn, Move newMove) {
        ctrl.newMovePane.setVisible(true);
        insertMovesIntoPane(pkmn, ctrl.updateMovesPane, "oldMove_");

        for (Node node : ctrl.updateMovesPane.getChildren()) {
            node.setOnMouseClicked(e -> showForgetMove(e));
        }

        BattleMoveCard newMoveCard = new BattleMoveCard(newMove);
        newMoveCard.setUserData(newMove);
        newMoveCard.setId("newMove_0");
        newMoveCard.setOnMouseClicked(e -> hideForgetMove());

        newMoveCard.setTranslateY(60);
        newMoveCard.setMaxSize(165, 70);
        GridPane.setValignment(newMoveCard, VPos.TOP);
        GridPane.setHalignment(newMoveCard, HPos.CENTER);

        ctrl.newMovePane.add(newMoveCard, 2, 1);

    }

    /**
     * Mostra il pannello di scelta della mossa da dimenticare.
     */
    private void showForgetMove(MouseEvent e) {
        Node node = (Node) e.getSource();
        String moveId = node.getId();
        int moveIndex = Integer.parseInt(moveId.substring(moveId.length()-1));
        ctrl.setMoveToForgetIndex(moveIndex);
        ctrl.forgetMoveBtnPane.setVisible(true);
    }

    /**
     * Nasconde il pannello di scelta della mossa da dimenticare.
     */
    private void hideForgetMove() {
        ctrl.forgetMoveBtnPane.setVisible(false);
    }

    /**
     * Chiude tutti i pannelli aperti al di fuori dal principale, mostrando quindi solo la
     * schermata di gioco.
     */
    protected void backToGame() {
        ctrl.bagPane.setVisible(false);
        ctrl.teamPane.setVisible(false);
        ctrl.gameSettingsPane.setVisible(false);
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
        ctrl.playerPkmnNameLbl.setText(playerPkmn.getBreed().getName());
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
        insertMovesIntoPane(pkmn, ctrl.movesPane, "move_");

        for (Node node : ctrl.movesPane.getChildren()) {
            node.setOnMouseClicked(e -> ctrl.move(e));
        }
    }

    /**
     * Dato in input un flow pane e un pokemon, vengono aggiunte nel flow pane le card delle mosse conosciute
     * da quel pokemon. In aggiunta viene richiesto una stringa prefisso da usare per identificare univocamente
     * la specifica card della mossa nella scena (non possono esserci due id uguali nella stessa scena).
     *
     * @param pkmn Pokemon da cui prendere le mosse
     * @param movesPane Flow pane in cui aggiungere le card delle mosse
     * @param movesIdPrefix Prefisso degli identificatori delle card delle mosse in questo pannello
     */
    private void insertMovesIntoPane(OwnedPokemon pkmn, FlowPane movesPane, String movesIdPrefix) {
        ArrayList<Move> moves = pkmn.getMoves();
        int size = moves.size();

        for (int i=0; i<size; i++) {
            BattleMoveCard moveCard = new BattleMoveCard(pkmn, i);
            moveCard.setUserData(moves.get(i));
            moveCard.setId(movesIdPrefix + i);
            movesPane.getChildren().add(moveCard);
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
