package it.rd.jpokebattle.view.bar;

import it.rd.jpokebattle.model.pokemon.Pokemon;

import javafx.scene.paint.Color;

public class LifeBar extends Bar {
    private int hp;
    private int maxHP;

    public LifeBar(double maxL, double strokeW) {
        super(maxL, strokeW);
        setStroke(GREEN);
    }

    public LifeBar(Pokemon pkmn, double maxL, double strokeW) {
        super(maxL, strokeW);
        setListener(pkmn);
    }


    public void setListener(Pokemon pkmn) {
        updateBar(pkmn);

        pkmn.currHPProperty().addListener((obs, oldV, newV) -> {
            hp = newV.intValue();
            maxHP = pkmn.getMaxHP();

            updateColor(hp, maxHP);
            setEndX(calcCurrLength(hp, maxHP));
        });
    }

    private void updateBar(Pokemon pkmn) {
        hp = pkmn.getCurrHP();
        maxHP = pkmn.getMaxHP();

        updateColor(hp, maxHP);
        setEndX(calcCurrLength(hp, maxHP));
    }

    private void updateColor(double hp, double maxHP) {
        if (hp > maxHP/2)
            setStroke(GREEN);
        else if (hp > maxHP/4)
            setStroke(YELLOW);
        else if (hp > 0)
            setStroke(RED);
        else
            setStroke(Color.TRANSPARENT);
    }
}
