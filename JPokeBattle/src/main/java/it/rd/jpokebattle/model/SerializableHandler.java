package it.rd.jpokebattle.model;


import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.ResourceLoader;
import it.rd.jpokebattle.util.file.SerManager;

import java.util.HashMap;

public interface SerializableHandler {
    /**
     * Genera un nuovo ID univoco basato sui profili esistenti memorizzati in un file serializzato.
     * Questo metodo carica una mappa di profili da un file specificato, trova l'ID numerico massimo tra i profili
     * esistenti, e restituisce un nuovo ID incrementato di uno rispetto al massimo trovato.
     * Se la mappa dei profili è vuota o non esiste, restituisce "1" come ID iniziale.
     *
     * @return Una stringa che rappresenta il nuovo ID generato.
     *
     * @see ResourceLoader
     */
    static int getMaxID(String serSrcName){
        HashMap<Integer, Profile> map = SerManager.loadSER(serSrcName);

        if (map == null)
            return 0;
        else {
            // Converte ogni chiave in intero, trova il massimo e restituisce il nuovo ID incrementato di uno
            return map.keySet().stream().max(Integer::compareTo).get();
        }
    }

    /**
     * Salva un'istanza specifica del tipo V su un file serializzato.
     * Questo metodo carica una mappa esistente di oggetti dal file specificato,
     * aggiorna la mappa con l'istanza fornita e riscrive il file con la mappa aggiornata.
     * Se la mappa non esiste, viene creata una nuova mappa.
     *

     *
     * @see SerManager
     */
    static <V> void save(V istance, int id, String serSrcName) {
        HashMap<Integer, V> map = SerManager.loadSER(serSrcName);

        if (map == null)
            map = new HashMap<>();

        map.put(id, istance);  // aggiunge o sostituisce il profilo

        SerManager.writeSER(map, serSrcName);
    }


    /**
     * Elimina un'istanza specifica di tipo V da un file serializzato.
     * Questo metodo carica una mappa di oggetti dal file specificato, rimuove l'istanza
     * corrispondente all'ID dell'oggetto passato come parametro, e aggiorna il file.
     * Se la mappa diventa vuota dopo la rimozione, il file viene cancellato.
     *

     *
     * @see SerManager
     */
    static void delete(Integer id, String serSrcName) {
        HashMap<Integer, Profile> map = SerManager.loadSER(serSrcName);
        map.remove(id); // Rimuove l'istanza dalla mappa utilizzando il suo ID

        if (map.isEmpty()) {
            SerManager.clearSER(serSrcName); // Cancella il file se la mappa è vuota
        } else {
            SerManager.writeSER(map, serSrcName); // Scrive la mappa aggiornata nel file
        }
    }
}
