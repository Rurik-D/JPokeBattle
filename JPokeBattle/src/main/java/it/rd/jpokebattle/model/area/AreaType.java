package it.rd.jpokebattle.model.area;

/**
 * Enumerazione che rappresenta i diversi tipi di area presenti nel gioco.
 * Ogni tipo di area è associato a un nome univoco.
 */
public enum AreaType {
    /** Area di tipo predefinito. */
    DEFAULT("default"),

    /** Area per la selezione del Pokémon iniziale. */
    STARTER_SELECT("starter_select"),

    /** Area con erba alta, dove si possono incontrare Pokémon selvatici. */
    TALL_GRASS("tall_grass"),

    /** Area dedicata alla guarigione dei Pokémon. */
    HEAL("heal"),

    /** Area dedicata al pokemart. */
    TRADE("trade"),     // NON IMPLEMENTATO

    /** Area dove si svolgono le battaglie. */
    BATTLE("battle"),

    /** Area in cui il giocatore può tentare di scappare. */
    TRY_ESCAPE("try_escape");

    private final String NAME;

    /**
     * Costruttore privato per l'associazione del nome al tipo di area.
     *
     * @param name Nome univoco del tipo di area.
     */
    private AreaType(String name) {
        this.NAME = name;
    }

    /**
     * Restituisce il tipo di area corrispondente al nome fornito.
     * Il confronto è case-insensitive.
     *
     * @param name Nome del tipo di area da cercare.
     * @return L'istanza di {@link AreaType} corrispondente al nome,
     *         oppure null se non esiste un tipo di area con quel nome.
     */
    public static AreaType fromName(String name) {
        for (AreaType type : AreaType.values()) {
            if (type.NAME.equalsIgnoreCase(name))
                return type;
        }

        return null;
    }
}
