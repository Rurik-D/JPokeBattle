package it.rd.jpokebattle.view;

import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.model.pokemon.Stats;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LifeBar extends Line {
    private final Color GREEN = Color.rgb(30, 230, 30);
    private final Color YELLOW = Color.rgb(230, 220, 30);
    private final Color RED = Color.rgb(230, 30, 30);
    private double maxLength;


    public LifeBar(double maxL, double strokeW) {
        super(0, 0, maxL, 0);

        this.maxLength = maxL;
        setStrokeWidth(strokeW);
        setStroke(GREEN);
    }

    public LifeBar(Pokemon pkmn, double maxL, double strokeW) {
        super(0, 0, maxL, 0);

        this.maxLength = maxL;
        setStrokeWidth(strokeW);


        setListener(pkmn);
    }

    private void updateBar(Pokemon pkmn) {
        double maxHP = pkmn.getMaxHP();
        double hp = pkmn.getCurrHP();
        // length : maxL = hp : maxHP
        double length = (hp * maxLength) / maxHP;


        if (pkmn.getCurrHP() > maxHP/2)
            setStroke(GREEN);
        else if (hp > maxHP/4)
            setStroke(YELLOW);
        else if (hp > 0)
            setStroke(RED);
        else
            setStroke(Color.TRANSPARENT);

        setEndX(length);
    }


    public void setListener(Pokemon pkmn) {
        updateBar(pkmn);
        pkmn.currHPProperty().addListener((obs, oldV, newV) -> {
            int hp = newV.intValue();
            double maxHP = pkmn.getMaxHP();
            // length : maxLength = hp : maxHP
            double length = (hp * maxLength) / maxHP;

            if (hp > maxHP/2)
                setStroke(GREEN);
            else if (hp > maxHP/4)
                setStroke(YELLOW);
            else if (hp > 0)
                setStroke(RED);
            else
                setStroke(Color.TRANSPARENT);

            setEndX(length);
        });
    }
}
