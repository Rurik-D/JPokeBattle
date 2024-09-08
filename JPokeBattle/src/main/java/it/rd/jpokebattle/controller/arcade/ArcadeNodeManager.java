package it.rd.jpokebattle.controller.arcade;

import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.model.area.Area;
import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.PokemonManager;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.ResourceLoader;
import it.rd.jpokebattle.view.arcade.MoveCard;
import it.rd.jpokebattle.view.arcade.PokemonCard;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
public class ArcadeNodeManager extends NodeManager {
    private static ArcadeNodeManager istance;
    private static ArcadeController ctrl;
    private static final String SEPARATOR = "\n———————————————————————————————\n\n";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");


    private ArcadeNodeManager() { }

    /**
     * Se l'istanza ancora non esiste la crea, altrimenti ritorna quella già esistente.
     *
     * @return L'unica istanza del node manager
     */
    public static ArcadeNodeManager getIstance() {
        if (istance == null)
            istance = new ArcadeNodeManager();

        return istance;
    }

    public void initialize(ArcadeController controller, Profile player) {
        setController(controller);
        setNarratorLbl(player.getNarratorTextHistory());
        updateNextAreaButtons(player.getCurrentArea());
        ctrl.profileNameLbl.setText(player.getName());
        ctrl.avatarImageView.setImage(ResourceLoader.loadImage(player.getAvatarSrcName()));
        startClock();
        ctrl.locationLbl.setText(player.getCurrentArea().getTitle());
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
     *
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
     *
     */
    public void showOwnedTeam(Profile player) {
        ctrl.teamPane.setVisible(true);
        int teamCounter = 0;

        for (int pkmnID : player.getOwnedPokemonIDs()) {
            if (teamCounter >= 6) break;
            OwnedPokemon pkmn = PokemonManager.getPokemonFromID(pkmnID);
            PokemonCard card = new PokemonCard(pkmn);
            card.setOnMouseClicked(e -> ctrl.pokemonDetails(e, pkmn));
            ctrl.teamCardsPane.getChildren().add(card);
            teamCounter++;
        }
    }

    /**
     *
     */
    public void showPokemonDetails(OwnedPokemon pkmn) {
        ctrl.teamCardsPane.setVisible(false);
        ctrl.teamPaneBackBtn.setVisible(false);
        ctrl.pokemonInfoPane.setVisible(true);
        ctrl.pokemonInfoPaneBackBtn.setVisible(true);
        updatePokemonInfoPane(pkmn);

        ArrayList<Move> moves = pkmn.getMoves();
        ArrayList<Integer> pps = pkmn.getPPs();
        Move move;
        int pp;

        for (int i=0; i<4; i++) {
            move = moves.get(i);
            pp = pps.get(i);
            ctrl.pokemonMovesVBox.getChildren().add(new MoveCard(move, pp));
        }

    }

    /**
     *
     */
    private void updatePokemonInfoPane(OwnedPokemon pkmn) {
        ctrl.info_avatarImgView.setImage(pkmn.getBreed().getAvatar());
        ctrl.info_nameLbl.setText("Nome: " + pkmn.getName());
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
     *
     */
    public void backToShowTeam() {
        ctrl.pokemonInfoPane.setVisible(false);
        ctrl.pokemonInfoPaneBackBtn.setVisible(false);
        ctrl.teamCardsPane.setVisible(true);
        ctrl.teamPaneBackBtn.setVisible(true);
        ctrl.pokemonMovesVBox.getChildren().clear();
    }

    /**
     *
     */
    public void showInvenctory() {
        ctrl.invenctoryPane.setVisible(true);
    }

    /**
     * Imposta il testo del narratore al testo passato in input. Sposta la scrollbar
     * in basso.
     *
     * @param narratorText Testo con cui settare la narratorLbl
     */
    public void setNarratorLbl(String narratorText) {
        ctrl.narratorLbl.setText(narratorText);
        // Imposta la barra di scorrimento dopo che la GUI è pronta
        updateNarratorScrollbarPosition();
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
        updateNarratorScrollbarPosition();
    }

    /**
     *
     */
    public void updateNarratorScrollbarPosition() {
        new Thread(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            ctrl.narratorScrlPane.setVvalue(1.0);
        }).start();
    }


    /**
     *
     */
    private void startClock() {
        updateClock();
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(20), e -> updateClock()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    /**
     *
     */
    private void updateClock() {
        LocalTime currentTime = LocalTime.now();
        ctrl.clockLbl.setText(currentTime.format(formatter));
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
