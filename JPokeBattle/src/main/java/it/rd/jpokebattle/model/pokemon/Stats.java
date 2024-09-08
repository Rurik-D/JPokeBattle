package it.rd.jpokebattle.model.pokemon;

public enum Stats {
    HP("hp"),
    ATK("atk"),
    DEF("def"),
    SPEC_ATK("spec_atk"),
    SPEC_DEF("spec_def"),
    SPEED("speed");

    private final String NAME_ID;

    private Stats (String name) {
        this.NAME_ID = name;
    }

    public Stats fromName(String name) {
        for (Stats stat : Stats.values())
            if (stat.NAME_ID.equalsIgnoreCase(name))
                return stat;
        return null;
    }
}
