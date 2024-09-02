package it.ml.jpokebattle.controller.menu;

import it.ml.jpokebattle.controller.SceneManager;
import it.ml.jpokebattle.controller.arcade.ArcadeNodeManager;
import it.ml.jpokebattle.model.engine.Arcade;
import it.ml.jpokebattle.util.FileManager;
import it.ml.jpokebattle.model.Profile;
import it.ml.jpokebattle.util.SoundManager;
import it.ml.jpokebattle.view.AlertMessage;
import it.ml.jpokebattle.view.ProfileBoxFunction;
import it.ml.jpokebattle.view.ProfilesContainer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
    private Profile lastPickedProfile;            // Ultimo profilo selezionato nella schermata di visualizzazione profili
    private static Parent root;


    @FXML
    protected ImageView volumeImg, avatarViewImg, gengarGif, snorlaxGif;


    @FXML
    protected Button
            selectGameModeBtn, modifyProfBtn, displayProfInfoBtn;


    @FXML
    protected Label
            nicknameLbl, nicknameProfCardLbl, avatarLbl;

    @FXML
    protected Line leftSeparatorLn, rightSeparatorLn;

    @FXML
    protected TextField nicknameTxtFd;

    @FXML
    protected GridPane
            mainMenuPane,
            selectGameProfilePane,
            gameModePane,
            profilesPane,
            addProfilePane,
            modifyProfilePane,
            profileInfoPane,
            settingsPane,
            profileCardGrid,
            avatarSelectionGrid;

    @FXML
    protected ScrollPane profilesScrlPane;

    @FXML
    protected ProfilesContainer profContBox;


    /**
     * Metodo chiamato dal bottone 'aggiungi' nel menù pricipale dei profili: passa
     * alla schermata di aggiunta/modifica del profilo.
     * */
    @FXML
    public void addProf(ActionEvent e){
        soundMan.buttonClick();
        mainMenuPane.setVisible(false);
        addProfilePane.setVisible(true);
    }

    /**
     * TODO: da implementare!
     * */
    @FXML
    public void arcade(ActionEvent e) throws IOException {
        soundMan.buttonClick();
        soundMan.switchToArcadeTrack();
        FXMLLoader loader = SceneManager.switchScene(e, "fxml.arcade", "css.arcade");
        ArcadeNodeManager.setController(loader.getController());
        Arcade arcade = Arcade.getIstance();
        arcade.update(lastPickedProfile);
        ArcadeNodeManager.getInstance().updateNarratorLbl();
    }

    /**
     * Metodo chiamato dal bottone 'indietro' nei menù "adiacenti" al menù
     * pricipale: torna al menù principale.
     * */
    @FXML
    public void mainMenu(ActionEvent e){
        soundMan.buttonClick();
        nodeMan.switchToMainMenu();
    }

    /**
     * Metodo chiamato dal bottone 'conferma' nel menù di modifica del
     * profilo: conferma le modifiche apportate al profilo, aggiornando prima
     * le variabili di nome e avatar nell'istanza del profilo selezionato
     * e poi aggiornando la mappa sul file ser.
     */
    @FXML
    public void confirmProfChanges(ActionEvent e) {
        soundMan.buttonClick();
        if (!nicknameTxtFd.getText().isBlank() && avatarViewImg.getImage() != null) {
            lastPickedProfile.setName(nicknameTxtFd.getText());
            lastPickedProfile.setAvatarSrcName(avatarViewImg.getImage().getUrl());
            lastPickedProfile.updateSER();
            profContBox.updateProfileContainer(ProfileBoxFunction.SHOW_PROFILES);
            nodeMan.switchToProfilesMenu();
        } else {
            alert.notExhaustiveProfileCompilation();
            soundMan.buttonClick();
        }
    }

    /**
     * Metodo chiamato dal bottone 'conferma' nel menù di creazione del
     * profilo: prima di procedere controlla il corretto inserimento dei
     * dati, dopodiché crea il profilo (salvandolo sul relativo file .ser)
     * e torna alla schermata principale dei profili.
     */
    @FXML
    public void confirmProfCreation(ActionEvent e) {
        soundMan.buttonClick();
        if (!nicknameTxtFd.getText().isBlank() && avatarViewImg.getImage() != null) {
            Profile prof = new Profile(nicknameTxtFd.getText(), avatarViewImg.getImage());
            prof.updateSER();
            profContBox.updateProfileContainer(ProfileBoxFunction.SHOW_PROFILES);
            nodeMan.switchToProfilesMenu();
        } else {
            alert.notExhaustiveProfileCompilation();
            soundMan.buttonClick();
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
     * Metodo chiamato dal bottone 'elimina' nel menù di visualizzazione dei
     * profili: prima di procedere chiede conferma, in caso affermativo
     * elimina il profilo, se ci sono altri profili non cambia schermata,
     * altrimenti (se il profilo eliminato era l'ultimo) torna alla schermata
     * principale dei profili.
     */
    @FXML
    public void deleteProf(ActionEvent e) {
        soundMan.buttonClick();
        boolean delete = alert.confirmDeleteProfile(lastPickedProfile.getName());
        soundMan.buttonClick();

        if (delete) {
            lastPickedProfile.delete();
            profContBox.updateProfileContainer(ProfileBoxFunction.SHOW_PROFILES);
            nodeMan.switchToProfilesMenu();
        }
    }

    /**
     * TODO: DA IMPLEMENTARE
     * Metodo chiamato dal bottone 'visualizza' nel menù di visualizzazione dei
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
     * Metodo chiamato dal bottone 'modifica' nel menù di visualizzazione dei
     * profili: passa alla schermata di modifica del profilo.
     */
    @FXML
    public void modifyProf(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.switchToModifyProfile(lastPickedProfile);
    }

    /**
     * Si attiva quando viene selezionato un profilo nel menù di
     * visualizzazione dei profili. Mostra la scheda del profilo
     * insieme ai bottoni 'modifica' e 'visualizza' e memorizza
     * l'ultimo profilo selezionato.
     *
     * @param prof      Profilo da cui prendere avatar e nome
     */
    public void profileModifyPreview(Profile prof) {
        soundMan.buttonClick();
        nodeMan.showModifyProfPreview(prof);
        lastPickedProfile = prof;
    }

    /**
     * Metodo chiamato dal bottone 'profili' nel menù pricipale dei
     * profili: passa alla schermata principale dei profili.
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

    public static void setRoot(Parent windowRoot) {
        root = windowRoot;
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
        lastPickedProfile = prof;
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
        Image img = FileManager.getImage(volumeSrcName);
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
