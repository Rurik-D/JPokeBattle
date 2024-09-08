package it.rd.jpokebattle.model.pokemon;

import it.rd.jpokebattle.util.file.DataMapLoader;
import javafx.scene.paint.Color;

import java.util.HashMap;

public enum Type {
    FIRE("fire"), WATER("water"), GRASS("grass"), NORMAL("normal"), ELECTRIC("electric"), PSYCHIC("psychic"),
    ICE("ice"), DRAGON("dragon"), GROUND("ground"), FIGHTING("fighting"), FLYING("flying"), POISON("poison"),
    BUG("bug"), GHOST("ghost"), ROCK("rock"), STEEL("steel"), DARK("dark");

    private final String NAME_ID;
    private final HashMap<String, String> effMap;     // mappa delle efficacia

    private Type(String name) {
        this.NAME_ID = name;
        this.effMap = DataMapLoader.getTypeEffMap().get(NAME_ID);
    }

    /**
     *
     * @return
     */
    public String getNameID() {
        return NAME_ID;
    }

    /**
     *
     * @return
     */
    public Double getTypeEff(String typeID) {
        return switch (effMap.getOrDefault(typeID, "")) {
            case "immune" -> 0.0;
            case "not_eff" -> 0.5;
            case "super_eff" -> 2.0;
            default -> 1.0;
        };
    }

    /**
     *
     * @return
     */
    public Double getTypeEff(String typeID1, String typeID2) {
        return this.getTypeEff(typeID1) * this.getTypeEff(typeID2);
    }

    /**
     *
     * @return
     */
    public static Color getLabelBorderColor(String name) {
        return switch (name) {
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
     *
     * @return
     */
    public static Type fromName(String name) {
        for (Type type : Type.values()) {
            if (type.NAME_ID.equalsIgnoreCase(name))
                return type;
        }

        return null;
    }
}
