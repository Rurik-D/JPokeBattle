package it.rd.jpokebattle.model.move;

/**
 * Enum che definisce gli effetti speciali implementati delle mosse.
 *
 * <p>Effetti speciali delle mosse:</p>
 * <ul>
 *     <li><b>HIGH_CRIT_PROB</b>: Aumenta la probabilità di colpo critico del 30%.</li>
 *     <li><b>HESITATION</b>: Conferisce una probabilità del 30% di tentennamento al bersaglio.</li>
 *     <li><b>MULTI_ATTACK</b>: Permette di colpire consecutivamente da 2 a 5 volte.</li>
 *     <li><b>PROTECT</b>: Permette di eludere tutti gli attacchi nel turno corrente.</li>
 * </ul>
 */
public enum MoveSpecialEffect {
    HIGH_CRIT_PROB("high_crit_prob"),
    HESITATION("hesitation"),
    MULTI_ATTACK("multi_attack"),
    PROTECT("protect");

    private final String NAME_ID;

    /**
     * Costruttore privato per l'enum MoveSpecialEffect.
     *
     * @param nameID Identificatore univoco dell'effetto speciale.
     */
    private MoveSpecialEffect(String nameID) {
        this.NAME_ID = nameID;
    }

    /**
     * Restituisce l'effetto speciale corrispondente al nome fornito.
     *
     * @param name Identificatore univoco dell'effetto speciale.
     * @return L'istanza di MoveSpecialEffect corrispondente, o {@code null} se non trovata.
     */
    public static MoveSpecialEffect fromName(String name) {
        for (MoveSpecialEffect move : MoveSpecialEffect.values()) {
            if (move.NAME_ID.equalsIgnoreCase(name))
                return move;
        }
        return null;
    }
}
