package it.rd.jpokebattle.view.bar;

import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.PokemonManager;

/**
 * Classe che rappresenta la barra dell'esperienza (XP) per un Pokémon.
 * La barra visualizza il progresso del Pokémon verso il livello successivo.
 */
public class XpBar extends Bar {
    private int xpRange;
    private int remainingXp;

    /**
     * Costruttore che crea una barra dell'XP con lunghezza e spessore specificati.
     *
     * @param maxL    lunghezza massima della barra.
     * @param strokeW spessore della barra.
     */
    public XpBar(double maxL, double strokeW) {
        super(maxL, strokeW);
        setStroke(BLUE);
    }

    /**
     * Imposta un listener sull'esperienza del Pokémon.
     * La barra si aggiorna automaticamente ogni volta che il valore dell'XP cambia.
     *
     * @param pkmn il Pokémon associato.
     */
    public void setListener(OwnedPokemon pkmn) {
        updateLength(pkmn);

        pkmn.xpProperty().addListener((obs, oldV, newV) -> {
            updateLength(pkmn);
        });
    }

    /**
     * Aggiorna la lunghezza della barra in base all'esperienza corrente del Pokémon.
     * Calcola l'intervallo di esperienza necessario per il livello successivo e la lunghezza attuale della barra.
     *
     * @param pkmn il Pokémon associato.
     */
    private void updateLength(OwnedPokemon pkmn) {
        xpRange =
                PokemonManager.getXPTreshold(pkmn.getLevel()+1) -
                PokemonManager.getXPTreshold(pkmn.getLevel());
        remainingXp = pkmn.getXpToNextLv();

        long length = Math.round(calcCurrLength((xpRange - remainingXp), xpRange));

        setEndX(length);
    }
}
