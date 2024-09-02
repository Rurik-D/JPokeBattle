package it.rd.jpokebattle.model.profile;

import it.rd.jpokebattle.model.SerializableHandler;
import javafx.scene.image.Image;



public class ProfileManager implements SerializableHandler {
    private static final String SER_SRC_NAME = "ser.prof";
    private static int maxID = SerializableHandler.getMaxID(SER_SRC_NAME);

    /**
     *
     */
    public static void newProfile(String name, Image avatar) {
        Profile profile = new Profile(maxID++, name, avatar);
        save(profile);
    }


    /**
     * Imposta il nome e il percorso dell'avatar, dopodiché aggiorna il file.
     *
     * @param p         Profilo da aggiornare
     * @param name       Nome del profilo
     * @param avatarPath Percorso all'immagine dell'avatar.
     */
    public static void updateProfileInfo(Profile p, String name, String avatarPath) {
        p.setName(name);
        p.setAvatarSrcName(avatarPath);
        save(p);
    }

    /**
     *
     */
    public static void save(Profile p) {
        SerializableHandler.save(p, p.ID, SER_SRC_NAME);
    }


    /**
     *
     */
    public static void delete(Profile p) {
        SerializableHandler.delete(p.ID, SER_SRC_NAME);
    }
}
