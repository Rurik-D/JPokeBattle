package it.rd.jpokebattle.model;


import it.rd.jpokebattle.model.profile.Profile;
import it.rd.jpokebattle.util.file.SerManager;

import java.util.HashMap;

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
     *
     */
    static <V> void save(HashMap<Integer, V> map, String serSrcName) {
        if (map == null)
            map = new HashMap<>();

        SerManager.writeSER(map, serSrcName);
    }

    /**
     *
     */
    static <V> void save(V istance, int id, String serSrcName) {
        HashMap<Integer, V> map = SerManager.loadSER(serSrcName);

        if (map == null)
            map = new HashMap<>();

        map.put(id, istance);  // aggiunge o sostituisce

        SerManager.writeSER(map, serSrcName);
    }

    /**
     *
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
