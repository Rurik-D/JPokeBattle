package it.rd.jpokebattle.model.pokemon;

/**
 * Enum che rappresenta le zone di spawn dei Pokémon nel gioco.
 * Ogni zona di spawn è identificata da un nome univoco.
 */
public enum SpawnZone {
    SPAWN_R1("spawn_r1"),
    SPAWN_R2("spawn_r2"),
    SPAWN_R3("spawn_r3"),
    SPAWN_R4("spawn_r4"),
    SPAWN_R5("spawn_r5");

    private final String nameID;

    /**
     * Costruttore dell'enum SpawnZone.
     *
     * @param nameID Nome univoco della zona di spawn.
     */
    private SpawnZone(String nameID) {
        this.nameID = nameID;
    }

    /**
     * Restituisce il nome identificativo della zona di spawn.
     *
     * @return Nome univoco della zona di spawn.
     */
    public String getNameID() {
        return nameID;
    }

    /**
     * Converte un nome di zona in un'istanza dell'enum SpawnZone.
     *
     * @param name Nome della zona di spawn.
     * @return Istanza corrispondente dell'enum SpawnZone, o null se non trovata.
     */
    public static SpawnZone fromName(String name) {
        for (SpawnZone type : SpawnZone.values()) {
            if (type.nameID.equalsIgnoreCase(name))
                return type;
        }
        return null;
    }
}
