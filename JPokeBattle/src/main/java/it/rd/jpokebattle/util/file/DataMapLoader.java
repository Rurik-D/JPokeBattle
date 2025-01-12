package it.rd.jpokebattle.util.file;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe utility per il caricamento di mappe di dati da file JSON.
 * Utilizza la libreria Gson per il parsing dei dati.
 */
public class DataMapLoader extends FileManager{
    private static Gson gson = new Gson();
    private static HashMap<String, HashMap<String, String>> typeEffMap = loadMap("json.type");

    /**
     * Restituisce la mappa di efficacia dei tipi caricata dai dati JSON.
     *
     * @return Mappa di efficacia dei tipi.
     */
    public static HashMap<String, HashMap<String, String>> getTypeEffMap() {
        return typeEffMap;
    }

    /**
     * Carica una mappa generica da un file JSON.
     *
     * @param <T>        Tipo dei valori nella mappa.
     * @param serSrcName Nome del file JSON da cui caricare i dati.
     * @param tClass     Classe del tipo dei valori nella mappa.
     * @return Mappa caricata dai dati JSON, o una mappa vuota in caso di errore.
     */
    public static <T> HashMap<String, T> loadMap(String serSrcName, Class<T> tClass) {
        HashMap<String, T> map = new HashMap<>();
        String path = getAbsPath(serSrcName);

        try (FileReader reader = new FileReader(path)){
            Type dataType = TypeToken.getParameterized(HashMap.class, String.class, tClass).getType();
            map = gson.fromJson(reader, dataType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Carica una mappa nidificata da un file JSON, utilizzata per l'efficacia dei tipi.
     *
     * @param serSrcName Nome del file JSON da cui caricare i dati.
     * @return Mappa nidificata caricata dai dati JSON, o una mappa vuota in caso di errore.
     */
    private static HashMap<String, HashMap<String, String>> loadMap(String serSrcName) {
        HashMap<String, HashMap<String, String>> map = new HashMap<>();
        String path = getAbsPath(serSrcName);

        try (FileReader reader = new FileReader(path)){
            Type dataType = new TypeToken<HashMap<String, HashMap<String, String>>>() {}.getType();
            map = gson.fromJson(reader, dataType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Carica una mappa nidificata per le zone di spawn e i livelli associati.
     *
     * @return Mappa di spawn caricata dai dati JSON, o una mappa vuota in caso di errore.
     */
    public static Map<String, Map<String, Integer[]>> loadSpawnMap() {
        Map<String, Map<String, Integer[]>> map = new HashMap<>();
        String path = getAbsPath("json.spawn");

        try (FileReader reader = new FileReader(path)) {
            Type dataType = new TypeToken<Map<String, Map<String, Integer[]>>>() {}.getType();
            map = gson.fromJson(reader, dataType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }
}
