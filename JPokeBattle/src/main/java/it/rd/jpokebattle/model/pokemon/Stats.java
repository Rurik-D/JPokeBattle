package it.rd.jpokebattle.model.pokemon;

public enum Stats {
    HP("hp", ""),
    ATK("atk", "L'attacco"),
    DEF("def", "La difesa"),
    SPEC_ATK("spec_atk", "L'attacco speciale"),
    SPEC_DEF("spec_def", "La difesa speciale"),
    SPEED("speed", "La velocità"),
    XP("xp", "L'esperienza"),
    PRECISION("precision", "La precisione"),
    ELUSION("elusion", "L'elusione"),
    CRIT_PROB("crit_prob", "La probabilità di colpo critico");

    private final String NAME_ID;
    private final String formattedName;

    private Stats (String nameID, String name) {
        this.NAME_ID = nameID;
        this.formattedName = name;
    }

    public String getFormattedName() {return formattedName;}

    public static Stats fromName(String name) {
        for (Stats stat : Stats.values())
            if (stat.NAME_ID.equalsIgnoreCase(name))
                return stat;
        return null;
    }
}
