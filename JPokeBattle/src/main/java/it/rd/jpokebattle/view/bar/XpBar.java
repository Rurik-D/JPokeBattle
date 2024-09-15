package it.rd.jpokebattle.view.bar;

import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.PokemonManager;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

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

        long length = Math.round(calcCurrLength((xpRange - remainingXp), xpRange));

        setEndX(length);
    }

}
