import java.io.Serializable;
import java.util.HashMap;

public class Pokemon implements Serializable{
    private String name;
    private int lv;
    private int xp;
    private int pv;
    
    private int id;

    public Pokemon(String name, int lv, int xp, int pv) {
        this.name = name;
        this.lv = lv;
        this.xp = xp;
        this.pv = pv;
        setId();
        addToMap();
    }

    private void addToMap(){
        HashMap<Integer, Pokemon> pokMap = FileManager.loadPokemonMap("pokemon.ser");

        if (pokMap == null)
            pokMap = new HashMap<>();

        pokMap.put(id, this);
        FileManager.writePokemonMap(pokMap, "pokemon.ser");
    }


    private void setId() {
        HashMap<String, Pokemon> pokMap = FileManager.loadPokemonMap("pokemon.ser");
        if (pokMap != null)
            this.id = pokMap.size() + 1;
        else
            this.id = 1;
    }

    public int getId() {
        return this.id;
    }


    @Override
    public String toString() {
        return "Pokemon [nome=" + name + ", livello=" + lv + 
               ", puntiEsperienza=" + xp + ", pv=" + pv + "]";
    }
}