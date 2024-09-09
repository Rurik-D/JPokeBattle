package it.rd.jpokebattle.model.pokemon;

public enum SpawnZone {
    SPAWN_R1("spawn_r1"), SPAWN_R2("spawn_r2"), SPAWN_R3("spawn_r3");

    private final String NAME_ID;

    private SpawnZone(String name) {
        this.NAME_ID = name;
    }
    public String getNameID() {
        return NAME_ID;
    }

    public static SpawnZone fromName(String name) {
        for (SpawnZone type : SpawnZone.values()) {
            if (type.NAME_ID.equalsIgnoreCase(name))
                return type;
        }

        return null;
    }

}
