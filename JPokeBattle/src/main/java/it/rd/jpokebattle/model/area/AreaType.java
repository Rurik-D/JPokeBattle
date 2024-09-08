package it.rd.jpokebattle.model.area;

public enum AreaType {
    DEFAULT("default"),
    STARTER_SELECT("starter_select"),
    TALL_GRASS("tall_grass"),
    HEAL("heal"),
    TRADE("trade"),
    BATTLE("battle"),
    TRY_ESCAPE("try_escape")
    ;

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
