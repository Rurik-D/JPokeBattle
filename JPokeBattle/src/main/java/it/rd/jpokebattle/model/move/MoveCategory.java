package it.rd.jpokebattle.model.move;

public enum MoveCategory {
    PHYSICAL("physical"),
    STATE("state"),
    SPECIAL("special");

    private final String NAME_ID;

    private MoveCategory (String name) {
        this.NAME_ID = name;
    }

    public static MoveCategory fromName(String name) {
        for (MoveCategory cat : MoveCategory.values())
            if (cat.NAME_ID.equalsIgnoreCase(name))
                return cat;
        return null;
    }
}
