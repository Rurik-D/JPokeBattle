package it.rd.jpokebattle.util.file;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * Classe di utilità astratta per la lettura dei file mediante il file properties.
 */
public abstract class FileManager {
    protected final static ResourceBundle src = ResourceBundle.getBundle("it/rd/jpokebattle/properties/resources");
    protected final static File dataDir = new File(System.getProperty("user.dir"), "data");
    protected final static String ROOT_PATH = "/it/rd/jpokebattle/";

    /**
     * A partire dal nome di risorsa ottengo il path assoluto del file aggiungendo il percorso
     * a partire dalla radice del file e, successivamente, traducendo il path prima in URL,
     * poi in URI e in fine in un percorso vero e prorio.
     */
    public static String getAbsPath(String fileSrcName) {
        String absPath = ROOT_PATH + src.getString(fileSrcName);

        try {
            absPath = Paths.get(ResourceLoader.class.getResource(absPath).toURI()).toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return absPath;
    }
}
