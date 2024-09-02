package it.rd.jpokebattle.model.area;

public enum AreaType {
    DEFAULT("default"),
    STARTER_SELECT("starterSelect"),
    TALL_GRASS("tallGrass"),
    POKE_CENTER("pokeCenter"),
    POKE_MART("pokeMart");

    private final String NAME;


    private AreaType(String name) {
        this.NAME = name;
    }

    public static AreaType fromName(String name) {
        for (AreaType type : AreaType.values()) {
            if (type.NAME.equalsIgnoreCase(name))
                return type;
        }

        return null;
    }
}
