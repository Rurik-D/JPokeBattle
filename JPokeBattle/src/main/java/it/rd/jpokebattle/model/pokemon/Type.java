package it.rd.jpokebattle.model.pokemon;


import it.rd.jpokebattle.util.file.DataMapLoader;

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

    public String getNameID() {
        return NAME_ID;
    }

    public Double getTypeEff(String typeID) {
        return switch (effMap.getOrDefault(typeID, "")) {
            case "immune" -> 0.0;
            case "not_eff" -> 0.5;
            case "super_eff" -> 2.0;
            default -> 1.0;
        };
    }

    public Double getTypeEff(String typeID1, String typeID2) {
        return this.getTypeEff(typeID1) * this.getTypeEff(typeID2);
    }

    public static Type fromName(String name) {
        for (Type type : Type.values()) {
            if (type.NAME_ID.equalsIgnoreCase(name))
                return type;
        }

        return null;
    }
}
