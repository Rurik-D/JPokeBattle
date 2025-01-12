package it.rd.jpokebattle.util.file;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * Classe di utilità per il caricamento e la manipolazione di qualsiasi file esterno.
 */
public class ResourceLoader extends FileManager{


    /**
     *  A partire dal nome di risorsa aggiungo il path dalla radice del programma e
     *  ritorno L'URL sotto forma di stringa.
     *
     * @return URL del file tradotto in stringa
     */
    public static String getResource(String fileSrcName) {
        String path = ROOT_PATH + src.getString(fileSrcName);
        return ResourceLoader.class.getResource(path).toString();
    }

    /**
     * Restituisce un file fxml a partire dal nome della risorsa.
     *
     * @param fxmlSrcName   nome di risorsa del file
     * @return              FXMLLoader contenente la scena caricata
     * */
    public static FXMLLoader loadFXML(String fxmlSrcName) {
        String path =  ROOT_PATH + src.getString(fxmlSrcName);

        return new FXMLLoader(ResourceLoader.class.getResource(path));
    }

    /**
     * Restituisce un'immagine a partire dal nome della risorsa.
     *
     * @param imageSrcName  nome di risorsa del file
     * @return              immagine caricata
     */
    public static Image loadImage(String imageSrcName) {
        String path = ROOT_PATH + src.getString(imageSrcName);
        URL imageUrl = ResourceLoader.class.getResource(path);

        if (imageUrl == null) {
            // Gestione errore: file non trovato
            throw new IllegalArgumentException("File non trovato: " + path);
        }

        return new Image(imageUrl.toExternalForm());
    }

    /**
     * Restituisce il media player di un file audio.
     *
     * @param mediaSrcName  nome di risorsa del file
     */
    public static MediaPlayer loadMP3(String mediaSrcName) {
        String path = ROOT_PATH + src.getString(mediaSrcName);
        Media media = new Media(ResourceLoader.class.getResource(path).toExternalForm());
        return new MediaPlayer(media);
    }
}
