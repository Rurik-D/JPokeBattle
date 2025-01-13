package it.rd.jpokebattle.util.audio;

import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Enum che rappresenta le tracce audio utilizzate nell'applicazione.
 * Ogni traccia audio è associata a un file sorgente e a un oggetto MediaPlayer per la riproduzione.
 */
public enum AudioTrack {
    TITLE_MENU("mp3.title"),
    LAB("mp3.lab"),
    BARK_TOWN("mp3.barkTown"),
    ROUTE_1("mp3.route1"),
    CH_GROVE("mp3.chGrove"),
    ROUTE_2("mp3.route2"),
    VIOLET("mp3.violet"),
    ROUTE_3("mp3.route3"),
    GOLDENROD("mp3.goldenrod"),
    ROUTE_4("mp3.route4"),
    CIANWOOD("mp3.cianwood"),
    ROUTE_5("mp3.route5"),
    BATTLE("mp3.battle"),
    VICTORY("mp3.victory"),
    TACKLE("mp3.tackle"),
    BUTTON("wav.btn");

    private String srcName;
    private MediaPlayer mediaPlayer;
    private double volume = 0.5;

    /**
     * Costruttore dell'enum AudioTrack.
     *
     * @param mediaSrcName Nome del file sorgente per la traccia audio.
     */
    private AudioTrack(String mediaSrcName) {
        srcName = mediaSrcName;
        mediaPlayer = ResourceLoader.loadMP3(mediaSrcName);
        mediaPlayer.setVolume(volume);
    }

    /**
     * Ritorna l'istanza di AudioTrack corrispondente a un dato nome sorgente.
     *
     * @param srcName Il nome del file sorgente da cercare.
     * @return L'istanza di AudioTrack corrispondente o null se non trovata.
     */
    public static AudioTrack fromSrcName(String srcName) {
        for (AudioTrack track : AudioTrack.values()) {
            if (track.getSrcName().equalsIgnoreCase(srcName))
                return track;
        }
        return null;
    }

    /**
     * Restituisce l'oggetto MediaPlayer associato a questa traccia.
     *
     * @return Il MediaPlayer associato.
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * Restituisce il nome del file sorgente della traccia audio.
     *
     * @return Il nome del file sorgente.
     */
    public String getSrcName() {
        return srcName;
    }

    /**
     * Attiva l'audio della traccia impostando il volume al valore predefinito.
     */
    public void audioOn() {
        mediaPlayer.setVolume(volume);
    }

    /**
     * Disattiva l'audio della traccia impostando il volume a 0.
     */
    public void audioOff() {
        mediaPlayer.setVolume(0);
    }

    /**
     * Riproduce la traccia in loop infinito.
     */
    public void playLoop() {
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    /**
     * Riproduce la traccia una sola volta, ripristinando il punto di inizio.
     */
    public void playOnce() {
        mediaPlayer.play();
        mediaPlayer.seek(Duration.ZERO);
    }

    /**
     * Imposta il nuovo livello del volume (reale tra 0 e 1) al livello passato in input
     *
     * @param v Nuovo livello del volume della traccia
     */
    public void setVolume(double v) {
        mediaPlayer.setVolume(v);
    }

    /**
     * Interrompe la traccia corrente e passa a una nuova traccia.
     *
     * @param newTrack La nuova traccia da riprodurre.
     */
    public void switchToTrack(AudioTrack newTrack) {
        MediaPlayer newMP = newTrack.getMediaPlayer();

        newMP.setVolume(0.5);
        newMP.setCycleCount(MediaPlayer.INDEFINITE);
        newMP.play();

        mediaPlayer.setVolume(0);
        mediaPlayer.stop();
        mediaPlayer.seek(Duration.ZERO);
    }
}
