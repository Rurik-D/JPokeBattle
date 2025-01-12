package it.rd.jpokebattle.controller.arcade;

import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.model.area.Area;
import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.model.move.MoveCategory;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.ResourceLoader;
import it.rd.jpokebattle.view.arcade.MoveCard;
import it.rd.jpokebattle.view.arcade.PokemonCard;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static it.rd.jpokebattle.model.pokemon.Stats.*;

/**
 * Classe singleton per la gestione delle componenti nella scena dell'arcade. Lavora a
 * stretto contatto con la classe ArcadeController per l'accensione, lo spegnimento e
 * la modifica dei nodi (componenti).
 *
 * @see ArcadeController
 */
public final class ArcadeNodeManager extends NodeManager {
    private static ArcadeNodeManager instance;
    private static ArcadeController ctrl;
    private static final String SEPARATOR = "\n———————————————————————————————\n\n";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


    private ArcadeNodeManager() { }

    /**
     * Se l'istanza ancora non esiste la crea, altrimenti ritorna quella già esistente.
     *
     * @return L'unica istanza del node manager
     */
    public static ArcadeNodeManager getInstance() {
        if (instance == null)
            instance = new ArcadeNodeManager();

        return instance;
    }


    /**
     * Salva il controller passato in input come attributo, in modo da poterlo usare
     * successivamente per ricavare le variabili delle componenti.
     *
     * @param controller ArcadeController istanziato dal menù controller
     */
    public static void setController(ArcadeController controller) {
        ctrl = controller;
    }

    /**
     * Inizializza i nodi dell'interfaccia con le informazioni del profilo del giocatore.
     */
    public void initializeNodes(Profile player) {
        setNarratorLbl(player.getNarratorTextHistory());
        updateNextAreaButtons(player.getCurrentArea());
        ctrl.profileNameLbl.setText(player.getName());
        ctrl.avatarImageView.setImage(ResourceLoader.loadImage(player.getAvatarSrcName()));
        startClock();
        ctrl.locationLbl.setText(player.getCurrentArea().getTitle());
    }


    /**
     * Aggiorna i bottoni di "scelta della prossima area" con i valori delle aree successive
     * contenuti nell'ogetto Area corrente. Se non sono presenti valori per un bottone, tale
     * bottone viene nascosto.
     */
    public void updateNextAreaButtons(Area area) {
        ctrl.area0Btn.setText(area.getNextAreaBtnText(0));
        ctrl.area1Btn.setText(area.getNextAreaBtnText(1));
        ctrl.area2Btn.setText(area.getNextAreaBtnText(2));

        ctrl.area1Btn.setVisible(!ctrl.area1Btn.getText().isBlank());
        ctrl.area2Btn.setVisible(!ctrl.area2Btn.getText().isBlank());
    }

    /**
     * Mostra il pannelo delle impostazioni.
     */
    public void showSettingsPane() {
        ctrl.gameSettingsPane.setVisible(true);
    }

    /**
     * Mostra i pokemon del team
     */
    public void showTeamPane(Profile player) {
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
     * Torna alla visualizzazione del team dopo aver mostrato i dettagli di un Pokemon.
     */
    public void backToShowTeam() {
        ctrl.pokemonInfoPane.setVisible(false);
        ctrl.teamCardsPane.setVisible(true);
        ctrl.teamPaneBackBtn.setVisible(true);
        ctrl.pokemonMovesVBox.getChildren().clear();
    }

    /**
     * Mostra l'inventario del giocatore.
     */
    public void showInvenctory() {
        ctrl.invenctoryPane.setVisible(true);
    }

    /**
     * Aggiorna  il testo del narratore al testo passato in input. Sposta la scrollbar
     * in basso.
     *
     * @param narratorText Testo con cui settare la narratorLbl
     */
    public void setNarratorLbl(String narratorText) {
        ctrl.narratorLbl.setText(narratorText);
        // Imposta la barra di scorrimento dopo che la GUI è pronta
        updateNarratorScrollbarPosition(ctrl.narratorScrlPane);
    }

    /**
     * Aggiorna il testo del narratore con la descrizione dell'area corrente in cui
     * il giocatore si trova.
     */
    public void updateNarrationInterface(Area currentArea) {
        updateNarratorLbl(currentArea.getDescription());
        updateNextAreaButtons(currentArea);
        updateNarratorScrollbarPosition(ctrl.narratorScrlPane);
        ctrl.locationLbl.setText(currentArea.getTitle());
    }

    /**
     * Aggiorna il testo del narratore con il testo passato in input. Aggiunge il nuovo
     * testo in fondo e sposta la scrollbar sul fondo.
     *
     * @param newNarratorText Testo con cui aggiornare la narratorLbl
     */
    public void updateNarratorLbl(String newNarratorText) {
        String text = ctrl.narratorLbl.getText() + SEPARATOR + newNarratorText + SEPARATOR;
        ctrl.narratorLbl.setText(text);
        updateNarratorScrollbarPosition(ctrl.narratorScrlPane);
    }

    /**
     * Avvia l'aggiornamento dell'orologio.
     */
    private void startClock() {
        updateClock();
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(20), e -> updateClock()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    /**
     * Aggiorna l'orario visualizzato nell'interfaccia.
     */
    private void updateClock() {
        LocalTime currentTime = LocalTime.now();
        ctrl.clockLbl.setText(currentTime.format(formatter));
    }

    /**
     * Avvia l'animazione di chiusura.
     */
    public void startClosingAnimation() {
        ImageView clsAnimGif = new ImageView(ResourceLoader.loadImage("gif.transCls"));
        clsAnimGif.setId("battleTransition");
        clsAnimGif.setPreserveRatio(false);
        clsAnimGif.setFitWidth(1000);
        clsAnimGif.setFitHeight(702);
        GridPane rootPane = (GridPane) root;
        rootPane.add(clsAnimGif, 0, 0,5, 11);
    }

    /**
     * Nasconde tutti i panneli al di fuori dal principale
     */
    public void hideAllPane() {
        ctrl.gameSettingsPane.setVisible(false);
        ctrl.teamPane.setVisible(false);
        ctrl.invenctoryPane.setVisible(false);
        ctrl.starterSelectionPane.setVisible(false);
        ctrl.pokeMartPane.setVisible(false);
        ctrl.selectionPreviewPane.setVisible(false);

        ctrl.teamCardsPane.getChildren().clear();
    }

}
