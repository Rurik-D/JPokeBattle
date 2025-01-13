package it.rd.jpokebattle.util.audio;

/**
 * Classe singleton per la gestione dell'audio del programma.
 */
public class SoundManager {
    private static SoundManager instance;    // Unica istanza della classe
    private boolean isVolumeOn = true;
    private AudioTrack currentTrack = AudioTrack.fromSrcName("mp3.title");
    private AudioTrack buttonPlayer = AudioTrack.fromSrcName("wav.btn");
    private AudioTrack pkmnAttackPlayer = AudioTrack.fromSrcName("mp3.tackle");

    private SoundManager() {
        currentTrack.playLoop();
        pkmnAttackPlayer.setVolume(1);
    }

    /**
     * Se l'istanza ancora non esiste la crea, altrimenti ritorna
     * quella già esistente.
     *
     * @return L'unica istanza della classe
     */
    public static SoundManager getInstance() {
        if (instance == null)
            instance = new SoundManager();

        return instance;
    }

    /**
     * Restituisce lo stato booleano (on/off) del volume.
     */
    public boolean isVolumeOn() {
        return isVolumeOn;
    }

    /**
     * Dato in input il nome di una traccia (presente nel file .properties) se la
     * traccia non è già in esecuzione effettua il cambio.
     *
     * @param srcName Nome sorgente della traccia  (presente nel file .properties)
     */
    public void switchTrack(String srcName) {
        if (!currentTrack.getSrcName().equals(srcName)) {
            AudioTrack newTrack = AudioTrack.fromSrcName(srcName);
            currentTrack.switchToTrack(newTrack);
            currentTrack = newTrack;
        }
    }

    /**
     * Inverte il valore della variabile booleana associata allo
     * stato del volume.
     */
    public void toggleVolume() {
        isVolumeOn = !isVolumeOn;
        if (isVolumeOn)
            currentTrack.audioOn();
        else
            currentTrack.audioOff();
    }

    /**
     * Suono standard dei bottoni.
     */
    public void buttonClick() {
        buttonPlayer.playOnce();
    }

    public void attackSound() {
        pkmnAttackPlayer.playOnce();
    }
}
