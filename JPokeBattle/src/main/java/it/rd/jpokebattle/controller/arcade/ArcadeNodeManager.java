package it.rd.jpokebattle.controller.arcade;

import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.model.area.Area;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    }

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


    public void updateChoiceButtons(Area area) {
        ctrl.nextAreaBtn.setText(area.getNextAreaButtonName());
        ctrl.prevAreaBtn.setText(area.getPrevAreaButtonName());
        ctrl.specialAreaBtn.setText(area.getSpecialAreaButtonName());

        ctrl.prevAreaBtn.setVisible(!ctrl.prevAreaBtn.getText().isBlank());
        ctrl.specialAreaBtn.setVisible(!ctrl.specialAreaBtn.getText().isBlank());
    }


    /**
     * Mostra il pannelo delle impostazioni.
     */
    public void showSettingsPane() {
        ctrl.gameSettingsPane.setVisible(true);
    }

    /**
     * TODO:DA IMPLEMENTARE
     */
    public void showOwnedPokemon() {
        ctrl.pokemonPane.setVisible(true);
    }

    /**
     * TODO:DA IMPLEMENTARE
     */
    public void showInvenctory() {
        ctrl.invenctoryPane.setVisible(true);
    }



    public void initialize(ArcadeController controller, Profile profile) {
        setController(controller);
        setNarratorLbl(profile.getNarratorTextHistory());
        updateChoiceButtons(profile.getCurrentArea());
        ctrl.profileNameLbl.setText(profile.getName());
        ctrl.avatarImageView.setImage(ResourceLoader.loadImage(profile.getAvatarSrcName()));
        startClock();
        ctrl.locationLbl.setText(profile.getCurrentArea().getTitle());
    }

    private void startClock() {
        updateClock();
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(20), e -> updateClock()));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
    }

    private void updateClock() {
        LocalTime currentTime = LocalTime.now();
        ctrl.clockLbl.setText(currentTime.format(formatter));
    }


    /**
     * Nasconde tutti i panneli al di fuori dal principale
     */
    public void hideAllPane() {
        ctrl.gameSettingsPane.setVisible(false);
        ctrl.pokemonPane.setVisible(false);
        ctrl.invenctoryPane.setVisible(false);
        ctrl.starterSelectionPane.setVisible(false);
        ctrl.pokeMartPane.setVisible(false);

    }
}
