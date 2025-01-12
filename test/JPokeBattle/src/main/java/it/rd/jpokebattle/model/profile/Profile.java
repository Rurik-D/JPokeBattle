package it.rd.jpokebattle.model.profile;

import it.rd.jpokebattle.model.area.Area;
import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.PokemonManager;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Classe che rappresenta il profilo di un giocatore. Contiene informazioni personali
 * come nome, avatar, area corrente, Pokémon posseduti e una cronologia dei testi
 * mostrati dal narratore.
 *
 * La classe fornisce metodi per la gestione delle aree, dei Pokémon del team e della
 * cronologia testuale, consentendo l'interazione con il mondo di gioco.
 */
public class Profile implements Serializable {
    private final int ID;
    private String name;
    private String avatarSrcName;
    private String currentAreaName;
    private String lastSafeAreaName;
    private String narratorTextHistory;
    private ArrayList<Integer> ownedPkmnIDs = new ArrayList<>();
    private HashMap<String, Integer> itemsMap;

    /**
     * Costruttore della classe Profile.
     *
     * @param id     Identificativo univoco del profilo.
     * @param name   Nome del giocatore.
     * @param avatar Immagine dell'avatar del giocatore.
     */
    protected Profile(int id, String name, Image avatar) {
        this.ID = id;
        this.name = name;
        this.currentAreaName = "start";
        this.narratorTextHistory = getCurrentArea().getDescription();
//        setItemsMap();
        setAvatarSrcName(avatar.getUrl());
    }

    /**
     * Restituisce l'identificativo univoco del profilo.
     *
     * @return ID del profilo.
     */
    public int getID() {
        return ID;
    }

    /**
     * Restituisce il nome del giocatore associato al profilo.
     *
     * @return Nome del giocatore.
     */
    public String getName() {
        return name;
    }

    /**
     * Restituisce il percorso dell'immagine dell'avatar.
     *
     * @return Percorso dell'immagine dell'avatar.
     */
    public String getAvatarSrcName() {
        return avatarSrcName;
    }

    /**
     * Restituisce gli ID dei Pokémon posseduti dal giocatore.
     *
     * @return Lista degli ID dei Pokémon posseduti.
     */
    public ArrayList<Integer> getOwnedPokemonIDs() {
        return ownedPkmnIDs;
    }

    /**
     * Restituisce i Pokémon del team del giocatore (massimo 6).
     *
     * @return Lista dei Pokémon attualmente nel team.
     */
    public ArrayList<OwnedPokemon> getTeam() {
        ArrayList<OwnedPokemon> team = new ArrayList<>();
        int size = Math.min(6, ownedPkmnIDs.size());

        for (int i=0; i<size; i++){
            int pkmnID = ownedPkmnIDs.get(i);
            team.add(PokemonManager.getPokemonFromID(pkmnID));
        }
        return team;
    }

    /**
     * Restituisce il nome dell'area corrente.
     *
     * @return Nome dell'area corrente.
     */
    public String getCurrentAreaName() {
        return currentAreaName;
    }

    /**
     * Restituisce l'area corrente come oggetto.
     *
     * @return Oggetto {@link Area} corrispondente all'area corrente.
     */
    public Area getCurrentArea() {
        return Area.fromName(currentAreaName);}

    /**
     * Restituisce il primo Pokémon nel team.
     *
     * @return Il primo Pokémon posseduto.
     */
    public OwnedPokemon getFirstPokemon() {
        int id = ownedPkmnIDs.getFirst();
        return PokemonManager.loadPokemonFromID(id);
    }

    /**
     * Restituisce la cronologia dei testi del narratore.
     *
     * @return Cronologia dei testi del narratore.
     */
    public String getNarratorTextHistory() {
        return narratorTextHistory;
    }

    /**
     * Imposta il percorso dell'immagine dell'avatar.
     *
     * @param avatarPath Percorso dell'immagine dell'avatar.
     */
    public void setAvatarSrcName(String avatarPath) {
        String[] path = avatarPath.split("jpokebattle/images/avatar/");
        this.avatarSrcName = path[1];
    }

    /**
     * Imposta il nome del giocatore.
     *
     * @param name Nome del giocatore.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Imposta il nome dell'area corrente.
     *
     * @param areaName Nome dell'area corrente.
     */
    public void setCurrentArea(String areaName) {
        currentAreaName = areaName;
    }

    /**
     * Passa all'area successiva in base all'indice specificato.
     *
     * @see Area
     * @param areaIndex Indice dell'area successiva.
     */
    public void goToNextArea(int areaIndex) {
        lastSafeAreaName = currentAreaName;
        Area currArea = Area.fromName(currentAreaName);
        setCurrentArea(currArea.getNextAreaName(areaIndex));
    }

    /**
     * Passa a un'area speciale definita dall'area corrente.
     */
    public void goToSpecialArea() {
        lastSafeAreaName = currentAreaName;
        Area currArea = Area.fromName(currentAreaName);
        setCurrentArea(currArea.getSpecialAreaName());
    }

    /**
     * Torna all'ultima area sicura.
     * (ultima zona visitata prima dell'erba alta)
     */
    public void goToLastSafeArea() {
        setCurrentArea(lastSafeAreaName);
    }

    /**
     * Imposta la cronologia dei testi del narratore, limitandola agli ultimi 2000 caratteri.
     *
     * @param narratorTextHistory Testo da impostare come cronologia del narratore.
     */
    public void setNarratorTextHistory(String narratorTextHistory) {
        int length = narratorTextHistory.length();
        this.narratorTextHistory = narratorTextHistory.substring(Math.max(0, length - 2000));
    }

    /**
     * Aggiorna la cronologia dei testi del narratore, aggiungendo nuovo testo e
     * limitando la lunghezza agli ultimi 2000 caratteri.
     *
     * @param text Testo da aggiungere alla cronologia.
     */
    public void updateNarratorTextHistory(String text) {
        narratorTextHistory += "\n———————————————————————————————\n\n" + text + "\n\n———————————————————————————————\n\n";
        int length = narratorTextHistory.length();
        this.narratorTextHistory = narratorTextHistory.substring(Math.max(0, length - 2000));
    }

    /**
     * Aggiunge un Pokémon alla lista dei Pokémon posseduti.
     *
     * @param pkmn Il Pokémon da aggiungere.
     */
    public void addToOwned(OwnedPokemon pkmn) {
        ownedPkmnIDs.add(pkmn.getID());
    }

    /**
     * Cura tutti i Pokémon posseduti dal giocatore.
     */
    public void healTeam() {
        for (int id : ownedPkmnIDs) {
            PokemonManager.loadPokemonFromID(id).heal();
        }
    }

}
