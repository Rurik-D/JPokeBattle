package it.rd.jpokebattle.model.move;

public enum MoveCategory {
    PHYSICAL("physical", "Fisico"),
    STATE("state", "Stato"),
    SPECIAL("special", "Speciale");

    private final String NAME_ID;
    private final String name;

    private MoveCategory (String nameID, String name) {
        this.NAME_ID = nameID;
        this.name = name;
    }

    public String getFormattedName() {
        return name;
    }

    public static String getFormattedName(String nameID) {
        return fromName(nameID).getFormattedName();
    }

    public static MoveCategory fromName(String name) {
        for (MoveCategory cat : MoveCategory.values())
            if (cat.NAME_ID.equalsIgnoreCase(name))
                return cat;
        return null;
    }
}
