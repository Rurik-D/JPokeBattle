package it.rd.jpokebattle.controller.menu;

import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.ResourceLoader;


/**
 * Classe singleton per la gestione delle componenti nella scena del menù principale.
 * Lavora a stretto contatto con la classe ArcadeController per l'accensione, lo
 * spegnimento e la modifica dei nodi (componenti).
 *
 * @see MenuController
 */
public final class MenuNodeManager extends NodeManager {
    private static MenuNodeManager instance;
    private static MenuController ctrl;

    private MenuNodeManager() { }

    /**
     * Se l'istanza ancora non esiste la crea, altrimenti ritorna quella già esistente.
     *
     * @return L'unica istanza del node manager
     */
    public static MenuNodeManager getInstance() {
        if (instance == null)
            instance = new MenuNodeManager();

        return instance;
    }

    /**
     * Salva il controller passato in input come attributo, in modo da poterlo usare
     * successivamente per ricavare le variabili delle componenti.
     *
     * @param controller MenuController istanziato nel main
     */
    public static void setController(MenuController controller) {
        ctrl = controller;
    }


    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle del menù di aggiunta
     * profilo (tra cui la griglia per la selezione dell'avatar, il text field per la
     * scelta del nome e il pannello che mostra la grafica attuale del profilo).
     *
     * @see MenuController
     */
    public void switchToAddProfile(){
        hideAll();
        ctrl.backToProfilesCancelBtn.setVisible(true);
        ctrl.confirmCreationBtn.setVisible(true);

        ctrl.addProfLbl.setVisible(true);
        ctrl.nicknameLbl.setVisible(true);
        ctrl.avatarLbl.setVisible(true);

        ctrl.nicknameTxtFd.setVisible(true);

        ctrl.profileCardGrid.setVisible(true);
        ctrl.avatarGrid.setVisible(true);

        ctrl.gengarGif.setVisible(false);
        ctrl.snorlaxGif.setVisible(false);

        ctrl.nicknameTxtFd.clear();
        ctrl.avatarViewImg.setImage(null);
        ctrl.nicknameProfCardLbl.textProperty().bind(ctrl.nicknameTxtFd.textProperty());
    }

    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle del menù principale.
     *
     * @see MenuController
     */
    public void switchToTitleMenu(){
        hideAll();
        lengthensSeparators();
        ctrl.selectGameProfileBtn.setVisible(true);
        ctrl.settingsBtn.setVisible(true);
        ctrl.exitBtn.setVisible(true);
        ctrl.titleMenuLbl.setVisible(true);
        ctrl.gengarGif.setVisible(true);
        ctrl.snorlaxGif.setVisible(true);

    }

    /**
     * Mostra la stessa schermata di aggiunta del profilo, modificando solo le componenti
     * e le impostazioni necessarie al corretto funzionamento e alla corretta
     * visualizzazione.
     *
     * @param pickedProf Profilo da cui prendere avatar e nome
     * @see MenuController
     */
    public void switchToModifyProfile(Profile pickedProf) {
        hideAll();
        switchToAddProfile();
        ctrl.confirmCreationBtn.setVisible(false);
        ctrl.addProfLbl.setVisible(false);

        ctrl.confirmChangesBtn.setVisible(true);
        ctrl.deleteProfBtn.setVisible(true);
        ctrl.modifyProfLbl.setVisible(true);

        ctrl.nicknameTxtFd.setText(pickedProf.getName());
        ctrl.avatarViewImg.setImage(ResourceLoader.loadImage(pickedProf.getAvatarSrcName()));
    }

    /**
     * TODO: DA IMPLEMENTARE
     * Nasconde tutte le componenti della scena e mostra solo quelle del menù di
     * visualizzazione delle informazioni del profilo selezionato.
     * Tra le informazioni troviamo:
     *  -
     *
     */
    public void switchToProfileInfo() {
        hideAll();
        ctrl.backToProfilesBtn.setVisible(true);
        ctrl.profileCardGrid.setVisible(true);
        ctrl.displayProfInfoLbl.setVisible(true);
    }

    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle del menù principale
     * dei profili (dove vengono visualizzati i profili per aggiunta e modifica).
     *
     * @see MenuController
     */
    public void switchToProfilesMenu(){
        hideAll();
        shortensSeparators();
        ctrl.backToMainBtn3.setVisible(true);
        ctrl.addProfBtn.setVisible(true);
        ctrl.profileLbl.setVisible(true);
        ctrl.profilesScrlPane.setVisible(true);

        ctrl.gengarGif.setVisible(true);
        ctrl.snorlaxGif.setVisible(true);

        ctrl.avatarViewImg.setImage(null);
        ctrl.nicknameProfCardLbl.textProperty().unbind();
        ctrl.nicknameProfCardLbl.setText("");
        ctrl.nicknameTxtFd.clear();
    }

    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle del menù
     * principale.
     *
     * @see MenuController
     */
    public void switchToMainMenu(){
        hideAll();
        lengthensSeparators();
        ctrl.arcadeBtn.setVisible(true);
        ctrl.pvpBtn.setVisible(true);
        ctrl.titleMenuBtn.setVisible(true);
        ctrl.changeProfileBtn.setVisible(true);
        ctrl.mainMenuLbl.setVisible(true);
        ctrl.profileCardGrid.setVisible(true);
    }

    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle del menù di
     * selezione del profilo di gioco.
     *
     * @see MenuController
     */
    public void switchToSelectGameProfile() {
        switchToProfilesMenu();

        ctrl.backToMainBtn3.setVisible(true);
    }

    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle del menù impostazioni.
     *
     * @see MenuController
     */
    public void switchToSettsMenu(){
        hideAll();
        ctrl.languageBtn.setVisible(true);
        ctrl.creditBtn.setVisible(true);
        ctrl.volumeBtn.setVisible(true);
        ctrl.backToMainBtn2.setVisible(true);
        ctrl.settingsLbl.setVisible(true);
    }


    /**
     * Visualizza, all'interno della schermata di visualizzazione dei profili, la scheda
     * del profilo selezionato sulla destra insieme ai bottoni 'modifica' e 'visualizza'.
     *
     * @param pickedProf Profilo da cui prendere avatar e nome
     * @see MenuController
     */
    public void showProfPreview(Profile pickedProf) {
        showProfileCard(pickedProf);
        ctrl.modifyProfBtn.setVisible(true);
        ctrl.displayProfInfoBtn.setVisible(true);
        ctrl.mainMenuBtn.setVisible(true);
    }

    /**
     * Visualizza, all'interno della schermata di selezione del profilo di gioco, la
     * scheda del profilo sulla destra insieme al bottone 'continua'.
     *
     * @see MenuController
     */
    public void showStartPreview(Profile pickedProf) {
        showProfileCard(pickedProf);
        ctrl.mainMenuBtn.setVisible(true);
    }

    /**
     * Imposta avatar e nome della profile card, rendendola poi visibile e nascondendo
     * le gif sottostanti.
     *
     * @param prof Profilo da cui prendere avatar e nome
     */
    public void showProfileCard(Profile prof) {
        ctrl.avatarViewImg.setImage(ResourceLoader.loadImage(prof.getAvatarSrcName()));
        ctrl.nicknameProfCardLbl.setText(prof.getName());
        ctrl.profileCardGrid.setVisible(true);

        ctrl.gengarGif.setVisible(false);
        ctrl.snorlaxGif.setVisible(false);
    }


    /**
     * Allunga i separatori ai lati del menù.
     */
    private void lengthensSeparators() {
        ctrl.leftSeparatorLn.setEndX(280);
        ctrl.rightSeparatorLn.setEndX(280);
    }

    /**
     * Accorcia i separatori ai lati del menù.
     */
    private void shortensSeparators() {
        ctrl.leftSeparatorLn.setEndX(220);
        ctrl.rightSeparatorLn.setEndX(220);
    }


    /**
     * Nasconde tutti gli oggetti della scena (al di fuori dei separatori e
     * delle imageView).
     */
    private void hideAll() {
        hideAllButtons();
        hideAllLabels();
        hideAllTextFields();
        hideAllPanes();
    }

    /**
     * Nasconde tutti i bottoni della scena.
     *
     * @see MenuController
     */
    protected void hideAllButtons(){
        root.lookupAll(".button").forEach(node -> node.setVisible(false));
    }

    /**
     * Nasconde e tutte le label della scena, escluso il titolo e le
     * label presenti nei pannelli.
     *
     * @see MenuController
     */
    private void hideAllLabels(){
        ctrl.titleMenuLbl.setVisible(false);
        ctrl.mainMenuLbl.setVisible(false);
        ctrl.profileLbl.setVisible(false);
        ctrl.settingsLbl.setVisible(false);
        ctrl.addProfLbl.setVisible(false);
        ctrl.nicknameLbl.setVisible(false);
        ctrl.avatarLbl.setVisible(false);
        ctrl.profileLbl.setVisible(false);
        ctrl.modifyProfLbl.setVisible(false);
        ctrl.displayProfInfoLbl.setVisible(false);
    }

    /**
     * Nasconde e resetta tutti i pannelli della scena la cui
     * variabile è stata definita nel controller del menù.
     *
     * @see MenuController
     */
    private void hideAllPanes() {
        ctrl.profileCardGrid.setVisible(false);
        ctrl.avatarGrid.setVisible(false);
        ctrl.profilesScrlPane.setVisible(false);
        ctrl.profilesScrlPane.setVvalue(0);
    }

    /**
     * Nasconde e resetta tutti i campi di testo della scena la cui
     * variabile è stata definita nel controller del menù.
     *
     * @see MenuController
     */
    private void hideAllTextFields() {
        ctrl.nicknameTxtFd.setVisible(false);
    }
}

