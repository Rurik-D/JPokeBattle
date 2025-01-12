package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.util.file.DataMapLoader;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * Enum che rappresenta i tipi di Pokémon, includendo il nome, l'identificatore e le mappe di efficacia.
 */
public enum Type {
    FIRE("fire", "Fuoco"), WATER("water", "Acqua"), GRASS("grass", "Erba"),
    NORMAL("normal", "Normale"), ELECTRIC("electric", "Elettro"), PSYCHIC("psychic", "Psico"),
    ICE("ice", "Ghiaccio"), DRAGON("dragon", "Drago"), GROUND("ground", "Terra"),
    FIGHTING("fighting", "Lotta"), FLYING("flying", "Volante"), POISON("poison", "Veleno"),
    BUG("bug", "Coleot."), GHOST("ghost", "Spettro"), ROCK("rock", "Roccia"),
    STEEL("steel", "Acciaio"), DARK("dark", "Buio");

    private final String NAME_ID;
    private final String name;
    private final HashMap<String, String> effMap;     // mappa delle efficacia

    /**
     * Costruttore dell'enum Type.
     *
     * @param nameID Identificatore unico del tipo.
     * @param name   Nome formattato del tipo.
     */
    private Type(String nameID, String name) {
        this.NAME_ID = nameID;
        this.name = name;
        this.effMap = DataMapLoader.getTypeEffMap().get(NAME_ID);
    }

    /**
     * Restituisce l'identificatore unico del tipo.
     *
     * @return Identificatore del tipo.
     */
    public String getNameID() {
        return NAME_ID;
    }

    /**
     * Restituisce il nome formattato del tipo.
     *
     * @return Nome formattato.
     */
    public String getFormattedName() {
        return name;
    }

    /**
     * Restituisce l'efficacia di un tipo rispetto a un altro.
     *
     * @param typeID Identificatore del tipo avversario.
     * @return Moltiplicatore di efficacia.
     */
    private Double getTypeEff(String typeID) {
        return switch (effMap.getOrDefault(typeID, "")) {
            case "immune" -> 0.0;
            case "not_eff" -> 0.5;
            case "super_eff" -> 2.0;
            default -> 1.0;
        };
    }

    /**
     * Restituisce il moltiplicatore di efficacia del tipo rispetto a due tipi avversari.
     *
     * @param typeID1 Identificatore del primo tipo avversario.
     * @param typeID2 Identificatore del secondo tipo avversario.
     * @return Moltiplicatore di efficacia combinato.
     */
    public Double getTypeEff(String typeID1, String typeID2) {
        return this.getTypeEff(typeID1) * this.getTypeEff(typeID2);
    }

    /**
     * Restituisce il colore del bordo dell'etichetta per un determinato tipo.
     *
     * @param type Tipo di cui ottenere il colore.
     * @return Colore RGB del bordo.
     */
    public static Color getLabelBorderColor(Type type) {
        return switch (type.getNameID()) {
            case "fire" -> Color.rgb(219, 35, 36);
            case "water" -> Color.rgb(39, 122, 236);
            case "grass" -> Color.rgb(59, 153, 38);
            case "normal" -> Color.rgb(152, 153, 152);
            case "electric" -> Color.rgb(248, 187, 2);
            case "psychic" -> Color.rgb(237, 62, 122);
            case "ice" -> Color.rgb(62, 214, 254);
            case "dragon" -> Color.rgb(81, 92, 224);
            case "ground" -> Color.rgb(147, 79, 34);
            case "fighting" -> Color.rgb(246, 122, 0);
            case "flying" -> Color.rgb(124, 179, 235);
            case "poison" -> Color.rgb(139, 59, 193);
            case "bug" -> Color.rgb(143, 159, 25);
            case "ghost" -> Color.rgb(111, 63, 113);
            case "rock" -> Color.rgb(173, 168, 128);
            case "steel" -> Color.rgb(96, 158, 182);
            case "dark" -> Color.rgb(80, 64, 64);

            default -> Color.rgb(0,0,0);
        };
    }

    /**
     * Restituisce un tipo a partire dal suo identificatore.
     *
     * @param name Nome o identificatore del tipo.
     * @return Tipo corrispondente o null se non esiste.
     */
    public static Type fromName(String name) {
        for (Type type : Type.values()) {
            if (type.NAME_ID.equalsIgnoreCase(name))
                return type;
        }

        return null;
    }
}
