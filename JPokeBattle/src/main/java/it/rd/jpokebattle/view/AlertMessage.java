package it.rd.jpokebattle.view;

import it.rd.jpokebattle.util.audio.SoundManager;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertMessage {
    private SoundManager soundMan = SoundManager.getInstance();

    /**
     * Messaggio di allerta per la mancata selezione di un nickname
     * o dell'avatar durante la creazione/modifica del profilo.
     */
    public void notExhaustiveProfileCompilation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Attenzione");
        alert.setHeaderText("Selezione non valida!");
        alert.setContentText("Inserisci un nickname e seleziona un avatar prima di continuare");
        alert.showAndWait();
        soundMan.buttonClick();
    }

    /**
     * Messaggio di allerta per la conferma dell'eliminazione del
     * profilo selezionato.
     *
     * @return valore booleano della scelta dell'utente
     */
    public boolean confirmDeleteProfile() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminare");
        alert.setHeaderText("Sicuro di voler eliminare il profilo?");
        alert.setContentText("Una volta eliminato, il profilo non sarà più recuperabile.");

        Optional<ButtonType> result = alert.showAndWait();
        soundMan.buttonClick();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

}

