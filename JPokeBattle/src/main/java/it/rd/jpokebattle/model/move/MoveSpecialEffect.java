package it.rd.jpokebattle.model.move;

/**
 * Effetti speciali delle mosse:
 *     HIGH_CRIT_PROB:  Prob. colpo critico +30%;
 *     HESITATION:      Prob. di tentennamento (30%);
 *     MULTI_ATTACK:    Colpi consecutivi da 2 a 5 volte;
 *     PROTECT:         Permette di eludere tutti gli attacchi;
 */
public enum MoveSpecialEffect {
    HIGH_CRIT_PROB("high_crit_prob"),
    HESITATION("hesitation"),
    MULTI_ATTACK("multi_attack"),
    PROTECT("protect");

    private final String NAME_ID;

    private MoveSpecialEffect(String nameID) {
        this.NAME_ID = nameID;
    }

    /**
     *
     * @return
     */
    public static MoveSpecialEffect fromName(String name) {
        for (MoveSpecialEffect move : MoveSpecialEffect.values()) {
            if (move.NAME_ID.equalsIgnoreCase(name))
                return move;
        }

        return null;
    }
}
