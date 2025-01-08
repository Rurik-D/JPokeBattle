package it.rd.jpokebattle.model.pokemon;

/**
 * Enumerazione che rappresenta le statistiche dei Pokémon.
 * Ogni statistica è definita con un identificatore univoco (NAME_ID) e un nome formattato
 * per una migliore leggibilità. Questa enumerazione include statistiche di base come
 * punti salute (HP), attacco, difesa, e altre caratteristiche utili nei combattimenti Pokémon.
 */
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

    /**
     * Restituisce il nome formattato della statistica.
     *
     * @return Una stringa contenente il nome formattato.
     */
    public String getFormattedName() {return formattedName;}

    /**
     * Restituisce un'istanza di {@code Stats} corrispondente al nome passato.
     *
     * @param name Il nome della statistica da cercare.
     * @return L'enumerazione {@code Stats} corrispondente al nome, oppure {@code null} se non trovato.
     */
    public static Stats fromName(String name) {
        for (Stats stat : Stats.values())
            if (stat.NAME_ID.equalsIgnoreCase(name))
                return stat;
        return null;
    }
}
