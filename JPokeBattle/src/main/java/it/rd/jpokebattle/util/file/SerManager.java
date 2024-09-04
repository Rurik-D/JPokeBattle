package it.rd.jpokebattle.util.file;

import java.io.*;
import java.util.HashMap;

public class SerManager extends FileManager{
    // accesso al file resources.properties contenente i path a tutti i file

    /**
     * Svuota il contenuto di un file.
     *
     * @param serSrcName
     */
    public static void clearSER(String serSrcName) {
        String path = src.getString(serSrcName);
        File file = new File(dataDir, path);

        try (FileOutputStream fileOut = new FileOutputStream(file)) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Restituisce una hashmap a partire da un file ser.
     * Ogni hashmap è composta da stringhe e oggetti.
     *
     * @param serSrcName    nome di risorsa del file
     * @return              hashmap caricata
     * @see it.rd.jpokebattle.properties
     * */
    public static <V> HashMap<Integer, V> loadSER(String serSrcName) {
        String path = src.getString(serSrcName);
        HashMap<Integer, V> map = null;

        File file = new File(dataDir, path);
        if (!file.exists())
            return null;
        if (file.length() == 0)
            return null;

        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            map = (HashMap<Integer, V>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * Scrive su un file ser una hashmap.
     * Ogni hashmap è composta da stringhe e oggetti.
     *
     * @param map           hashmap da scrivere
     * @param serSrcName    nome di risorsa del file
     * @see it.rd.jpokebattle.properties
     * */
    public static <V> void writeSER(HashMap<Integer, V> map, String serSrcName) {
        String path = src.getString(serSrcName);
        File file = new File(dataDir, path);

        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
