package it.rd.jpokebattle.controller.menu;

import it.rd.jpokebattle.controller.NodeManager;
import it.rd.jpokebattle.controller.SceneManager;
import it.rd.jpokebattle.controller.arcade.ArcadeController;
import it.rd.jpokebattle.controller.arcade.ArcadeNodeManager;
import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.model.profile.ProfileManager;
import it.rd.jpokebattle.util.audio.SoundManager;
import it.rd.jpokebattle.util.file.ResourceLoader;
import it.rd.jpokebattle.view.AlertMessage;
import it.rd.jpokebattle.view.menu.ProfileBox;
import it.rd.jpokebattle.view.menu.ProfileBoxFunction;
import it.rd.jpokebattle.view.menu.ProfilesContainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuController {
    private MenuNodeManager nodeMan = MenuNodeManager.getInstance();
    private SoundManager soundMan = SoundManager.getInstance();
    private AlertMessage alert = new AlertMessage();
    private Profile selectedProfile;            // Ultimo profilo selezionato nella schermata di visualizzazione profili

    @FXML
    protected ImageView volumeImg, avatarViewImg, gengarGif, snorlaxGif;
    @FXML
    protected Button
            // Menu principale
            selectGameProfileBtn, profileBtn, settingsBtn, exitBtn,
            // Impostazioni
            volumeBtn, creditBtn, languageBtn,
            // Menu di selezione del profilo di gioco
            selectGameModeBtn,
            // Menu di selezione modalità
            arcadeBtn, pvpBtn, changeProfileBtn,
            // Visualizzazione profili
            addProfBtn, modifyProfBtn, displayProfInfoBtn,
            // Aggiungi/modifica profilo
            confirmCreationBtn, confirmChangesBtn, deleteProfBtn,
            // Ritorno
            mainMenuBtn, backToMainBtn2, backToMainBtn3, backToProfilesCancelBtn, backToProfilesBtn;
    @FXML
    protected Label
            titleLbl, mainMenuLbl, gameProfileLbl, gameModeLbl, profileLbl, settingsLbl,
            addProfLbl, nicknameLbl, nicknameProfCardLbl, avatarLbl, modifyProfLbl, displayProfInfoLbl;
    @FXML
    protected Line leftSeparatorLn, rightSeparatorLn;
    @FXML
    protected TextField nicknameTxtFd;
    @FXML
    protected GridPane profileCardGrid, avatarGrid;
    @FXML
    protected ScrollPane profilesScrlPane;
    @FXML
    protected ProfilesContainer profContBox;


    /**
     * Metodo chiamato dal bottone 'aggiungi' nel menù di visualizzazione dei profili:
     * passa alla schermata di aggiunta del profilo.
     * */
    @FXML
    public void addProf(ActionEvent e){
        soundMan.buttonClick();
        nodeMan.switchToAddProfile();
    }

    /**
     * Metodo chiamato dal bottone 'arcade' nel menù di selezione della modalità di
     * gioco: carica la scena della modalità arcade, aggiorna la root corrente nella
     * classe astratta NodeManeger, passa al controller l'istanza del profilo
     * selezionato, passa all'ArcadeNodeManager l'istanza del controller appena
     * caricato e carica nella narratorLbl la cronologia del testo del narratore di
     * quel profilo.
     * */
    @FXML
    public void arcade(ActionEvent e) throws IOException {
        soundMan.buttonClick();
        soundMan.switchTrack(selectedProfile.getCurrentArea().getMusicSrcName());

        FXMLLoader loader = SceneManager.switchScene(e, "fxml.arcade", "css.arcade");
        NodeManager.setRoot(loader.getRoot());

        ArcadeNodeManager arcNodeMan = ArcadeNodeManager.getIstance();

        ArcadeController.setPlayer(selectedProfile);
        arcNodeMan.initialize(loader.getController(), selectedProfile);
    }

    /**
     * Metodo chiamato dal bottone 'conferma' nel menù di modifica del profilo:
     * aggiorna le variabili di nome e avatar nel profilo, aggiorna il container
     * delle profile box e torna alla schermata di visualizzazione dei profili.
     * Se l'input dell'utente non è corretto resta nella schermata e mostra una
     * finestra di errore.
     *
     * @see ProfilesContainer
     * @see ProfileBox
     * @see AlertMessage
     */
    @FXML
    public void confirmProfChanges(ActionEvent e) {
        soundMan.buttonClick();
        if (!nicknameTxtFd.getText().isBlank() && avatarViewImg.getImage() != null) {
            ProfileManager.updateProfileInfo(selectedProfile, nicknameTxtFd.getText(), avatarViewImg.getImage().getUrl());
            profContBox.updateProfileContainer(ProfileBoxFunction.SHOW_PROFILES);
            nodeMan.switchToProfilesMenu();
        } else {
            alert.notExhaustiveProfileCompilation();
        }
    }

    /**
     * Metodo chiamato dal bottone 'conferma' nel menù di modifica del profilo:
     * crea un nuovo profilo a partire dai dati in input, aggiorna il container
     * delle profile box e torna alla schermata di visualizzazione dei profili.
     * Se l'input dell'utente non è corretto resta nella schermata e mostra una
     * finestra di errore.
     *
     * @see ProfilesContainer
     * @see ProfileBox
     * @see AlertMessage
     */
    @FXML
    public void confirmProfCreation(ActionEvent e) {
        soundMan.buttonClick();
        if (!nicknameTxtFd.getText().isBlank() && avatarViewImg.getImage() != null) {
            ProfileManager.newProfile(nicknameTxtFd.getText(), avatarViewImg.getImage());
            profContBox.updateProfileContainer(ProfileBoxFunction.SHOW_PROFILES);
            nodeMan.switchToProfilesMenu();
        } else {
            alert.notExhaustiveProfileCompilation();
        }
    }

    /**
     * TODO: da implementare!
     * */
    @FXML
    public void credits(ActionEvent e){
        soundMan.buttonClick();
        System.out.println("Crediti");
    }

    /**
     * Metodo chiamato dal bottone 'elimina' nel menù di modifica del profilo:
     * prima di procedere chiede conferma, in caso affermativo elimina il profilo,
     * aggiorna il profiles container e torna alla schermata di selezione del profilo.
     */
    @FXML
    public void deleteProf(ActionEvent e) {
        soundMan.buttonClick();

        if (alert.confirmDeleteProfile()) {
            ProfileManager.delete(selectedProfile);
            profContBox.updateProfileContainer(ProfileBoxFunction.SHOW_PROFILES);
            nodeMan.switchToProfilesMenu();
        }
    }

    /**
     * TODO: DA IMPLEMENTARE
     * Metodo chiamato dal bottone 'visualizza' nel menù di selezione dei
     * profili: passa alla schermata di visualizzazioni delle informazioni del
     * profilo selezionato.
     */
    @FXML
    public void displayProfileInfo(ActionEvent e) {
        nodeMan.switchToProfileInfo();
        soundMan.buttonClick();
    }

    /**
     * TODO: da implementare!
     * */
    @FXML
    public void language(ActionEvent e){
        soundMan.buttonClick();
        System.out.println("Lingua");
    }

    /**
     * Visualizza il menù principale.
     * */
    @FXML
    public void mainMenu(ActionEvent e){
        soundMan.buttonClick();
        nodeMan.switchToMainMenu();
    }

    /**
     * Metodo chiamato dal bottone 'modifica' nel menù di selezione dei
     * profili: passa alla schermata di modifica del profilo.
     */
    @FXML
    public void modifyProf(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.switchToModifyProfile(selectedProfile);
    }

    /**
     * Si attiva quando viene selezionato un profilo nel menù di selezione dei
     * profili. Mostra la scheda del profilo insieme ai bottoni 'modifica' e
     * 'visualizza' e memorizza l'ultimo profilo selezionato.
     *
     * @param prof Profilo da cui prendere avatar e nome
     */
    public void profileModifyPreview(Profile prof) {
        soundMan.buttonClick();
        nodeMan.showModifyProfPreview(prof);
        selectedProfile = prof;
    }

    /**
     * Metodo chiamato dal bottone 'profili' nel menù pricipale: passa alla schermata
     * di selezione dei profili.
     * */
    @FXML
    public void profiles(ActionEvent e){
        soundMan.buttonClick();
        profContBox.updateProfileContainer(ProfileBoxFunction.SHOW_PROFILES);
        nodeMan.switchToProfilesMenu();
    }

    /**
     * TODO: da implementare!
     * */
    @FXML
    public void pvp(ActionEvent e){
        soundMan.buttonClick();
        System.out.println("PvP");
    }

    /**
     * Chiude la finestra e termina il programma.
     * */
    @FXML
    public void quit(ActionEvent e){
        soundMan.buttonClick();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Metodo chiamato dal bottone "continua" nella schermata di selezione del
     * profilo: passa alla schermata di selezione della modalità di gioco.
     * */
    @FXML
    public void selectGameMode(ActionEvent e){
        soundMan.buttonClick();
        profContBox.updateProfileContainer(ProfileBoxFunction.START);
        nodeMan.switchToSelectGameMode();
    }

    /**
     * Metodo chiamato dal bottone 'inizio' nel menù principale: passa
     * alla schermata di selezione del profilo.
     * */
    @FXML
    public void selectGameProfile(ActionEvent e){
        soundMan.buttonClick();
        profContBox.updateProfileContainer(ProfileBoxFunction.START);
        nodeMan.switchToSelectGameProfile();
    }

    /**
     * Metodo chiamato dalle ImageView nel menù di creazione/modifica del
     * profilo: imposta l'immagine della scheda del profilo con l'immagine
     * dell'avatar selezionato nella griglia.
     */
    @FXML
    public void setAvatarProfileCard(MouseEvent e) {
        soundMan.buttonClick();
        ImageView clickedImageView = (ImageView) e.getSource();
        avatarViewImg.setImage(clickedImageView.getImage());
    }

    /**
     * Metodo chiamato dal bottone 'impostazioni' nel menù pricipale: passa
     * alla schermata delle impostazioni.
     * */
    @FXML
    public void settings(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.switchToSettsMenu();
    }

    public void startPreview(Profile prof) {
        soundMan.buttonClick();
        nodeMan.showStartPreview(prof);
        selectedProfile = prof;
    }

    /**
     *  Inverte il valore della variabile booleana associata all'immagine
     *  del volume e aggiorna l'immagine.
     *
     * @param e     Click del mouse sull'immagine.
     * @see SoundManager
     */
    @FXML
    public void switchVolume(MouseEvent e) {
        soundMan.buttonClick();
        soundMan.toggleVolume();
        updateVolumeIcon();
    }

    /**
     * Aggiorna l'immagine del volume in base al valore della variabile
     * booleana associata.
     *
     * @see SoundManager
     */
    private void updateVolumeIcon() {
        String volumeSrcName = soundMan.isVolumeOn() ? "img.volume" : "img.mute";
        Image img = ResourceLoader.loadImage(volumeSrcName);
        volumeImg.setImage(img);
    }

    /**
     * TODO: da implementare!
     * */
    @FXML
    public void volume(ActionEvent e){
        soundMan.buttonClick();
        System.out.println("Volume");
    }
}
