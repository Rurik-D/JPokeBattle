import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    public static <V> HashMap<Integer, V> loadPokemonMap(String path) {
        HashMap<Integer, V> pokemonMap = null;

        // Deserializzazione della mappa di Pokémon
        try (FileInputStream fileIn = new FileInputStream(path); 
            ObjectInputStream in = new ObjectInputStream(fileIn)) {
            pokemonMap = (HashMap<Integer, V>) in.readObject();

        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        
        return pokemonMap;
    }


    public static void writePokemonMap(Map<Integer, Pokemon> pokemonMap, String path) {
        try (FileOutputStream fileOut = new FileOutputStream(path);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(pokemonMap);

        } catch (IOException i) {
            i.printStackTrace();
        }
    }



}
