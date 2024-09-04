package it.rd.jpokebattle.util.audio;


import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public enum AudioTrack {
    TITLE_MENU("mp3.title"),
    BARK_TOWN("mp3.barkTown"),
    BUTTON("wav.btn");

    private String srcName;
    private MediaPlayer mediaPlayer;
    private double volume = 0.5;

    private AudioTrack(String mediaSrcName) {
        srcName = mediaSrcName;
        mediaPlayer = ResourceLoader.loadMP3(mediaSrcName);
        mediaPlayer.setVolume(volume);
    }

    public static AudioTrack fromSrcName(String srcName) {
        for (AudioTrack track : AudioTrack.values()) {
            if (track.getSrcName().equalsIgnoreCase(srcName))
                return track;
        }
        return null;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public String getSrcName() {
        return srcName;
    }

    public void audioOn() {
        mediaPlayer.setVolume(volume);
    }

    public void audioOff() {
        mediaPlayer.setVolume(0);
    }


    public void playLoop() {
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void playOnce() {
        mediaPlayer.play();
        mediaPlayer.seek(Duration.ZERO);
    }

    public void switchToTrack(AudioTrack newTrack) {
        MediaPlayer newMP = newTrack.getMediaPlayer();

        newMP.setVolume(0); // Imposta il volume iniziale della nuova traccia a 0
        newMP.play();
        newMP.setCycleCount(MediaPlayer.INDEFINITE);

        // Timeline per il crossfade
        Timeline fadeOutIn = new Timeline(
                new KeyFrame(Duration.millis(50),
                        event -> {
                            double currentVolume = mediaPlayer.getVolume();
                            double nextVolume = newMP.getVolume();

                            mediaPlayer.setVolume(Math.max(currentVolume - 0.0125, 0));

                            newMP.setVolume(Math.min(nextVolume + 0.0125, volume));
                        }
                )
        );

        fadeOutIn.setCycleCount(80);        // Per 1 secondo di transizione (40 cicli * 50ms)
        fadeOutIn.setOnFinished(event -> {
            mediaPlayer.stop();             // Ferma la traccia corrente dopo il fade out
            mediaPlayer.seek(Duration.ZERO);
        });

        fadeOutIn.play(); // Esegui il crossfade

    }
}
