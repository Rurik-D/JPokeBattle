package it.rd.jpokebattle.view.bar;

import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.PokemonManager;

public class XpBar extends Bar {
    private int xpRange;
    private int remainingXp;

    public XpBar(double maxL, double strokeW) {
        super(maxL, strokeW);
        setStroke(BLUE);
    }

    public XpBar(OwnedPokemon pkmn, double maxL, double strokeW) {
        super(maxL, strokeW);
        setStroke(BLUE);
        setListener(pkmn);
    }

    public void setListener(OwnedPokemon pkmn) {
        updateLength(pkmn);

        pkmn.xpProperty().addListener((obs, oldV, newV) -> {
            updateLength(pkmn);
        });
    }

    private void updateLength(OwnedPokemon pkmn) {
        xpRange =
                PokemonManager.getXPTreshold(pkmn.getLevel()+1) -
                PokemonManager.getXPTreshold(pkmn.getLevel());
        remainingXp = pkmn.getXpToNextLv();

        double length = calcCurrLength((xpRange - remainingXp), xpRange);
        //TODO QUANDO LA BARRA VA OLTRE LA LUNGHEZZA NON SI RESETTA CORRETTAMENTE
        //TODO QUANDO LA BARRA VA OLTRE LA LUNGHEZZA NON SI RESETTA CORRETTAMENTE
        //TODO QUANDO LA BARRA VA OLTRE LA LUNGHEZZA NON SI RESETTA CORRETTAMENTE
        //TODO QUANDO LA BARRA VA OLTRE LA LUNGHEZZA NON SI RESETTA CORRETTAMENTE
        if (length >= getMaxLength())
            length = 0;

        setEndX(length);
    }
}
