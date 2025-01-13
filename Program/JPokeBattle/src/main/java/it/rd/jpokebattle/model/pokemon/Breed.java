package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.util.file.DataMapLoader;
import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe che rappresenta una razza di Pokémon.
 * Contiene informazioni di base come statistiche, tipi, soglia per l'evoluzione,
 * mosse apprese e risorse visive (immagini e GIF).
 */
public class Breed implements Serializable {
    /**
     * Mappa contenente tutte le razze caricate dal relativo file json.
     * Le chiavi sono i nomi delle mosse e i valori sono istanze di {@link Move}.
     */
    private static final HashMap<String, Breed> AREA_MAP = DataMapLoader.loadMap("json.breed", Breed.class);
    private String name;
    private String firstTypeName;
    private String secondTypeName;
    private String predBreedName;
    private String succBreedName;
    private String avatarSrcName;
    private String frontGifSrcName;
    private String backGifSrcName;
    private int nextEvoThreshold;
    private int baseHP;
    private int baseAtk;
    private int baseDef;
    private int baseSpecAtk;
    private int baseSpecDef;
    private int baseSpeed;
    private int baseXp;
    private HashMap<String, Integer> movesPerLevel;

    /**
     * Restituisce il valore base di una statistica per questa razza di Pokémon.
     *
     * @param stat La statistica richiesta.
     * @return Il valore base della statistica specificata.
     */
    public int baseValueOf(Stats stat) {
        return switch (stat) {
            case HP -> baseHP;
            case ATK -> baseAtk;
            case DEF -> baseDef;
            case SPEC_ATK -> baseSpecAtk;
            case SPEC_DEF -> baseSpecDef;
            case SPEED -> baseSpeed;
            case XP -> baseXp;
            default -> 100;
        };
    }

    /**
     * Restituisce la soglia di esperienza necessaria per l'evoluzione a questa razza.
     *
     * @return La soglia di esperienza per la prossima evoluzione.
     */
    public int getNextEvoThresh() {
        return nextEvoThreshold;
    }

    /**
     * Restituisce il nome della razza successiva in una catena evolutiva.
     *
     * @return Il nome della razza successiva.
     */
    public String getSuccBreedName() {
        return succBreedName;
    }

    /**
     * Restituisce il nome di questa razza.
     *
     * @return Il nome della razza.
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce l'immagine dell'avatar di questa razza di Pokémon.
     *
     * @return Un oggetto {@link Image} rappresentante l'avatar.
     */
    public Image getAvatar() {
        return ResourceLoader.loadImage(avatarSrcName);
    }

    /**
     * Restituisce l'immagine GIF frontale di questa razza di Pokémon.
     *
     * @return Un oggetto {@link Image} rappresentante la GIF frontale.
     */
    public Image getFrontGif() {
        return ResourceLoader.loadImage(frontGifSrcName);
    }

    /**
     * Restituisce l'immagine GIF posteriore di questa razza di Pokémon.
     *
     * @return Un oggetto {@link Image} rappresentante la GIF posteriore.
     */
    public Image getBackGif() {
        return ResourceLoader.loadImage(backGifSrcName);
    }

    /**
     * Restituisce il nome del tipo primario della razza.
     *
     * @return Il nome del tipo primario.
     */
    public String getFirstTypeName() {
        return firstTypeName;
    }

    /**
     * Restituisce il nome del tipo secondario della razza, se presente.
     *
     * @return Il nome del tipo secondario, oppure null se non presente.
     */
    public String getSecondTypeName() {
        return secondTypeName;
    }

    /**
     * Restituisce una mappa delle mosse che questa razza può apprendere con i rispettivi livelli.
     *
     * @return Una mappa dove le chiavi sono i nomi delle mosse e i valori i livelli.
     */
    public HashMap<String, Integer> getMovesPerLevel() {
        return movesPerLevel;
    }

    /**
     * Dato il livello di un pokemon di una certa razza, viene restituita, se esiste la
     * mossa imparata a quel livello. Restituisce null se tale mossa non esiste
     *
     * @param level Livello del pokemon
     * @return Nome della mossa
     */
    public Move getMoveFromLevel(int level) {
        for (String move : movesPerLevel.keySet()) {
            if (movesPerLevel.get(move) == level)
                return Move.fromName(move);
        }
        return null;
    }

    /**
     * Restituisce una lista delle mosse che questa razza può apprendere fino a un livello specificato.
     *
     * @param level Il livello massimo considerato.
     * @return Una lista di oggetti {@link Move}.
     */
    public ArrayList<Move> getMovesBelowLevel(int level) {
        ArrayList<Move> moves = new ArrayList<>();

        for (String move : movesPerLevel.keySet()) {
            if (movesPerLevel.get(move) <= level)
                moves.add(Move.fromName(move));
        }

        return moves;
    }

    /**
     * Restituisce un'istanza della razza corrispondente a un nome specificato.
     *
     * @param breedName Il nome della razza.
     * @return L'istanza della razza, oppure null se il nome non è valido.
     */
    public static Breed fromName(String breedName) {
        return AREA_MAP.getOrDefault(breedName, null);
    }

}
