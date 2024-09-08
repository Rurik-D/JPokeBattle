package it.rd.jpokebattle.model.move;

/**
 * Effetti speciali delle mosse:
 *     HIGH_CRIT_PROB:  Prob. colpo critico +30%;
 *     RAPID_SPIN:      elimina gli effetti delle mosse Legatutto, Avvolgibotta, Parassiseme e Punte;
 *     HESITATION:      Prob. di tentennamento (30%);
 *     CRIT_STRIKE:     Colpo critico assicurato;
 *     MULTI_ATTACK:    Colpi consecutivi da 2 a 5 volte;
 *     ASSURANCE:       Se il nemico ha già subito dei danni nello stesso turno, la potenza di questa mossa raddoppia;
 *     RAIN_DANCE:      Danno fuoco -33%, danno acqua +33%;
 *     PROTECT:         Permette di eludere tutti gli attacchi, se usata in successione può fallire;
 *     SYNTHESIS:       PS recuperati in base dall'ora del giorno.
 */
public enum MoveSideEffect {
    HIGH_CRIT_PROB,
    RAPID_SPIN,
    HESITATION,
    CRIT_STRIKE,
    MULTI_ATTACK,
    ASSURANCE,
    RAIN_DANCE,
    PROTECT,
    SYNTHESIS;
}
