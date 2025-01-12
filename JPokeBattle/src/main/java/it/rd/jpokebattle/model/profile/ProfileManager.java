package it.rd.jpokebattle.model.profile;

import it.rd.jpokebattle.model.SerializableHandler;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.PokemonManager;
import javafx.scene.image.Image;

/**
 * Gestore per la gestione dei profili utente nel gioco.
 * Fornisce metodi per creare, aggiornare, salvare ed eliminare i profili.
 * Implementa il meccanismo di serializzazione tramite la classe {@link SerializableHandler}.
 */
public class ProfileManager implements SerializableHandler {
    private static final String SER_SRC_NAME = "ser.prof";
    private static int maxID = SerializableHandler.calcMaxID(SER_SRC_NAME);

    /**
     * Calcola il prossimo ID disponibile per un nuovo profilo.
     *
     * @return Il prossimo ID disponibile.
     */
    public static int getNextID() {
        return ++maxID;
    }

    /**
     * Crea un nuovo profilo con un nome e un avatar specificati e lo salva.
     *
     * @param name   Nome del nuovo profilo.
     * @param avatar Immagine avatar del nuovo profilo.
     */
    public static void newProfile(String name, Image avatar) {
        Profile profile = new Profile(getNextID(), name, avatar);
        save(profile);
    }


    /**
     * Aggiorna le informazioni di un profilo esistente e salva le modifiche.
     *
     * @param p          Profilo da aggiornare.
     * @param name       Nuovo nome del profilo.
     * @param avatarPath Percorso al file dell'immagine avatar.
     */
    public static void updateProfileInfo(Profile p, String name, String avatarPath) {
        p.setName(name);
        p.setAvatarSrcName(avatarPath);
        save(p);
    }

    /**
     * Salva un profilo serializzandolo e memorizzandolo nel file associato.
     *
     * @param p Profilo da salvare.
     */
    public static void save(Profile p) {
        SerializableHandler.save(p, p.getID(), SER_SRC_NAME);
    }


    /**
     * Elimina un profilo specificato e rimuove tutti i Pokémon posseduti associati a tale profilo.
     *
     * @param p Profilo da eliminare.
     */
    public static void delete(Profile p) {
        SerializableHandler.delete(p.getID(), SER_SRC_NAME);

        for (int pkmnID : p.getOwnedPokemonIDs()) {
            OwnedPokemon pkmn = PokemonManager.getPokemonFromID(pkmnID);
            PokemonManager.delete(pkmn, false);
        }
        PokemonManager.save();
    }
}
