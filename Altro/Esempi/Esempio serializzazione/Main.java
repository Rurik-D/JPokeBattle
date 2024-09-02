import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // new Pokemon("Pikachu", 5, 100, 35);
        // new Pokemon("Charmander", 8, 200, 39);
        // new Pokemon("Bulbasaur", 10, 300, 45);
        // new Pokemon("Boh", 30, 3240, 445);

        HashMap<Integer, Pokemon> pokMap = FileManager.loadPokemonMap("pokemon.ser");
        Pokemon pok = pokMap.get(4);
        System.out.println("\n\n" + pok + "\n\n");

    }

}

