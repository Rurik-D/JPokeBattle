package it.rd.jpokebattle.view.bar;

import it.rd.jpokebattle.model.pokemon.Pokemon;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class LifeBar extends Bar {
    private int hp;
    private int maxHP;
    private double currLength;

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

            long newLength = Math.round(calcCurrLength(hp, maxHP));
            movingAnimation(newLength);

        });
    }


    private void movingAnimation(long newLength) {
        double step = (newLength - currLength) / 80;

        Timeline fadeOutIn = new Timeline(
                new KeyFrame(Duration.millis(10),
                        e -> {
                            currLength = currLength + step;
                            setEndX(currLength);
                            updateColor(currLength, getMaxLength());
                        }
                )
        );
        fadeOutIn.setCycleCount(80);
        fadeOutIn.setOnFinished(e -> {
            currLength = newLength;
            setLength(currLength);
        });
        fadeOutIn.play();
    }

    private void updateBar(Pokemon pkmn) {
        hp = pkmn.getCurrHP();
        maxHP = pkmn.getMaxHP();

        updateColor(hp, maxHP);

        currLength = (int) Math.round(calcCurrLength(hp, maxHP));
        setLength(currLength);

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
