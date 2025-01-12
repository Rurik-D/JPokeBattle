package it.rd.jpokebattle.model;


import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.SerManager;

import java.util.HashMap;

/**
 * Interfaccia che gestisce la serializzazione e deserializzazione di oggetti.
 * Fornisce metodi utili per calcolare ID, salvare e rimuovere oggetti da file serializzati.
 */
public interface SerializableHandler {
    /**
     * Calcola e ritorna l'ID più grande generato per una certa mappa.
     */
    static int calcMaxID(String serSrcName){
        HashMap<Integer, Profile> map = SerManager.loadSER(serSrcName);

        if (map == null || map.isEmpty())
            return 0;
        else
            return map.keySet().stream().max(Integer::compareTo).get();
    }

    /**
     * Calcola e restituisce l'ID più grande generato per una certa mappa serializzata.
     *
     * @param serSrcName Nome della risorsa serializzata.
     */
    static <V> void save(HashMap<Integer, V> map, String serSrcName) {
        if (map == null)
            map = new HashMap<>();

        SerManager.writeSER(map, serSrcName);
    }

    /**
     * Salva una mappa serializzata nel file specificato.
     *
     * @param istance    L'oggetto che si vuole salvare nella mappa da serializzare.
     * @param serSrcName Nome della risorsa serializzata.
     * @param id         Indice univoco nella mappa dell'oggetto da sostituire.
     */
    static <V> void save(V istance, int id, String serSrcName) {
        HashMap<Integer, V> map = SerManager.loadSER(serSrcName);

        if (map == null)
            map = new HashMap<>();

        map.put(id, istance);  // aggiunge o sostituisce

        SerManager.writeSER(map, serSrcName);
    }

    /**
     * Salva un'istanza specifica in una mappa serializzata.
     * Se l'ID esiste già nella mappa, l'oggetto viene sostituito.
     *
     * @param id         ID associato all'istanza.
     * @param serSrcName Nome della risorsa serializzata.
     */
    static void delete(int id, String serSrcName) {
        HashMap<Integer, Profile> map = SerManager.loadSER(serSrcName);
        map.remove(id);

        if (map.isEmpty())
            SerManager.clearSER(serSrcName);
        else
            SerManager.writeSER(map, serSrcName);
    }
}
