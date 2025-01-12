package it.rd.jpokebattle.model.move;

/**
 * Enum che rappresenta le categorie di mosse.
 */
public enum MoveCategory {
    PHYSICAL("physical", "Fisico"),
    STATE("state", "Stato"),
    SPECIAL("special", "Speciale");

    private final String NAME_ID;
    private final String formattedName;

    /**
     * Costruttore privato per l'enum MoveCategory.
     *
     * @param nameID       Identificatore univoco della categoria.
     * @param formattedName Nome formattato della categoria.
     */
    private MoveCategory (String nameID, String formattedName) {
        this.NAME_ID = nameID;
        this.formattedName = formattedName;
    }

    /**
     * Restituisce il nome formattato della categoria.
     *
     * @return Il nome formattato.
     */
    public String getFormattedName() {
        return formattedName;
    }

    /**
     * Restituisce il nome formattato dato un identificatore univoco.
     *
     * @param nameID Identificatore univoco della categoria.
     * @return Il nome formattato, o una stringa vuota se l'identificatore non è valido.
     */
    public static String getFormattedName(String nameID) {
        return fromName(nameID).getFormattedName();
    }

    /**
     * Recupera un'istanza di MoveCategory dall'identificatore univoco.
     *
     * @param nameID Identificatore univoco della categoria.
     * @return L'istanza di MoveCategory corrispondente, o null se non trovata.
     */
    public static MoveCategory fromName(String nameID) {
        for (MoveCategory cat : MoveCategory.values())
            if (cat.NAME_ID.equalsIgnoreCase(nameID))
                return cat;
        return null;
    }
}
