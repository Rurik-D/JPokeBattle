package it.ml.jpokebattle.controller.menu;

import it.ml.jpokebattle.controller.NodeManager;
import it.ml.jpokebattle.model.Profile;
import it.ml.jpokebattle.util.FileManager;


/**
 * Classe singleton per la gestione delle componenti nella scena del menù.
 * Lavora a stretto contatto con la classe MenuController per l'accensione
 * e lo spegnimento dei bottoni.
 *
 * @see MenuController
 */
public class MenuNodeManager extends NodeManager {
    private static MenuNodeManager instance;      // Unica istanza del button manager
    private static MenuController ctrl;           // Istanza del controller da cui vengono prese le var. dei bottoni

    private MenuNodeManager() { }

    /**
     * Se l'istanza ancora non esiste la crea, altrimenti ritorna
     * quella già esistente.
     *
     * @return      L'unica istanza del node manager
     */
    public static MenuNodeManager getInstance() {
        if (instance == null)
            instance = new MenuNodeManager();

        return instance;
    }


    /**
     * Salva il controller passato in input come attributo, in modo
     * da poterlo usare successivamente per ricavare le variabili
     * delle componenti.
     *
     * @param controller MenuController istanziato nel main
     */
    public static void setController(MenuController controller) {
        ctrl = controller;
    }

    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle
     * del menù impostazioni.
     *
     * @see MenuController
     */
    public void switchToSettsMenu(){
        hideAll();
        ctrl.settingsPane.setVisible(true);
    }

    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle
     * del menù principale.
     *
     * @see MenuController
     */
    public void switchToMainMenu(){
        hideAll();
        lengthensSeparators();
        ctrl.mainMenuPane.setVisible(true);
    }

    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle
     * del menù di inizio gioco.
     *
     * @see MenuController
     */
    public void switchToSelectGameMode(){
        hideAll();
        lengthensSeparators();
        ctrl.gameModePane.setVisible(true);
    }

    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle
     * del menù principale dei profili.
     *
     * @see MenuController
     */
    public void switchToProfilesMenu(){
        hideAll();
        shortensSeparators();
        ctrl.profilesPane.setVisible(true);
        ctrl.profilesScrlPane.setVisible(true);
        ctrl.profileCardGrid.setVisible(true);

        ctrl.gengarGif.setVisible(true);
        ctrl.snorlaxGif.setVisible(true);

        ctrl.avatarViewImg.setImage(null);
        ctrl.nicknameProfCardLbl.textProperty().unbind();
        ctrl.nicknameProfCardLbl.setText("");
        ctrl.nicknameTxtFd.clear();
    }

    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle
     * del menù di aggiunta profilo.
     *
     * @see MenuController
     */
    public void switchToAddProfile(){
        hideAll();
        ctrl.addProfilePane.setVisible(true);

        ctrl.nicknameTxtFd.setVisible(true);
        ctrl.profileCardGrid.setVisible(true);
        ctrl.avatarSelectionGrid.setVisible(true);

        ctrl.gengarGif.setVisible(false);
        ctrl.snorlaxGif.setVisible(false);

        ctrl.nicknameTxtFd.clear();
        ctrl.avatarViewImg.setImage(null);
        ctrl.nicknameProfCardLbl.textProperty().bind(ctrl.nicknameTxtFd.textProperty());
    }

    /**
     * Prima di mostrare la nuova schermata aggiorna il valore del campo
     * di testo con il nickname del profilo selezionato (ancora salvato
     * nella label della scheda profilo). Senza questo passaggio la label
     * si resetterebbe una volta sincronizzata con il campo di testo (cosa
     * che avviene alla chiamata di switchToAddProfile).
     *
     * @see MenuController
     */
    public void switchToModifyProfile(Profile pickedProf) {
        hideAll();
        ctrl.modifyProfilePane.setVisible(true);

        ctrl.modifyProfBtn.setVisible(false);
        ctrl.displayProfInfoBtn.setVisible(false);

        ctrl.nicknameTxtFd.setVisible(true);
        ctrl.profileCardGrid.setVisible(true);
        ctrl.avatarSelectionGrid.setVisible(true);

        ctrl.nicknameProfCardLbl.textProperty().bind(ctrl.nicknameTxtFd.textProperty());
        ctrl.nicknameTxtFd.setText(pickedProf.getName());
        ctrl.avatarViewImg.setImage(FileManager.getImage(pickedProf.getAvatarSrcName()));
    }

    /**
     * Visualizza (all'interno della schermata di visualizzazione del
     * profilo) la scheda del profilo sulla destra insieme ai bottoni
     * 'modifica' e 'visualizza'.
     *
     * @param prof      Profilo da cui prendere avatar e nome
     */
    public void showModifyProfPreview(Profile prof) {
        showProfileCard(prof);
        ctrl.modifyProfBtn.setVisible(true);
        ctrl.displayProfInfoBtn.setVisible(true);
    }

    /**
     * TODO: DA IMPLEMENTARE
     * Nasconde tutte le componenti della scena e mostra solo quelle
     * del menù di visualizzazione delle informazioni del profilo
     * selezionato.
     *
     */
    public void switchToProfileInfo() {
        hideAll();
        ctrl.profileInfoPane.setVisible(true);
        ctrl.profileCardGrid.setVisible(true);
    }

    public void switchToSelectGameProfile() {
        switchToProfilesMenu();
        ctrl.modifyProfilePane.setVisible(false);
        ctrl.selectGameProfilePane.setVisible(true);
    }

    /**
     *
     *
     */
    public void showStartPreview(Profile prof) {
        showProfileCard(prof);
        ctrl.selectGameModeBtn.setVisible(true);
    }

    public void showProfileCard(Profile prof) {
        ctrl.avatarViewImg.setImage(FileManager.getImage(prof.getAvatarSrcName()));
        ctrl.nicknameProfCardLbl.setText(prof.getName());

        ctrl.gengarGif.setVisible(false);
        ctrl.snorlaxGif.setVisible(false);
    }


    /**
     * Accorcia i separatori ai lati del menù.
     */
    private void shortensSeparators() {
        ctrl.leftSeparatorLn.setEndX(220);
        ctrl.rightSeparatorLn.setEndX(220);
    }

    /**
     * Allunga i separatori ai lati del menù.
     */
    private void lengthensSeparators() {
        ctrl.leftSeparatorLn.setEndX(280);
        ctrl.rightSeparatorLn.setEndX(280);
    }

    /**
     * Nasconde tutti gli oggetti della scena (al di fuori dei separatori e
     * delle imageView).
     */
    private void hideAll() {
        root.lookupAll(".grid-pane").forEach(n -> { if (!n.getId().equals("rootPane")) n.setVisible(false); });

        ctrl.nicknameTxtFd.setVisible(false);
        ctrl.profilesScrlPane.setVisible(false);
    }
}

