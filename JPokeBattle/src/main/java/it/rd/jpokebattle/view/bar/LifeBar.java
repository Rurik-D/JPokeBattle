package it.rd.jpokebattle.view.bar;

import it.rd.jpokebattle.model.pokemon.Pokemon;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Rappresenta una barra della vita che mostra graficamente lo stato degli HP di un Pokémon.
 * La barra si aggiorna dinamicamente quando gli HP del Pokémon cambiano e include animazioni fluide
 * per rendere visibile la variazione.
 */
public class LifeBar extends Bar {
    private int hp;
    private int maxHP;
    private double currLength;

    /**
     * Costruisce una barra della vita con una lunghezza massima e uno spessore specificati.
     * @param maxL lunghezza massima della barra.
     * @param strokeW spessore della barra.
     */
    public LifeBar(double maxL, double strokeW) {
        super(maxL, strokeW);
        setStroke(GREEN);
    }

    /**
     * Costruisce una barra della vita associata a un Pokémon. La barra si aggiorna dinamicamente
     * in base agli HP del Pokémon.
     * @param pkmn il Pokémon a cui è associata la barra.
     * @param maxL lunghezza massima della barra.
     * @param strokeW spessore della barra.
     */
    public LifeBar(Pokemon pkmn, double maxL, double strokeW) {
        super(maxL, strokeW);
        setListener(pkmn);
    }

    /**
     * Collega un listener alla proprietà degli HP correnti del Pokémon, aggiornando la barra
     * ogni volta che gli HP cambiano.
     * @param pkmn il Pokémon a cui collegare il listener.
     */
    public void setListener(Pokemon pkmn) {
        updateBar(pkmn);

        pkmn.currHPProperty().addListener((obs, oldV, newV) -> {
            hp = newV.intValue();
            maxHP = pkmn.getMaxHP();

            long newLength = Math.round(calcCurrLength(hp, maxHP));
            movingAnimation(newLength);

        });
    }

    /**
     * Esegue un'animazione fluida per aggiornare la lunghezza della barra verso un nuovo valore.
     * @param newLength la nuova lunghezza della barra.
     */
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

    /**
     * Aggiorna la barra della vita basandosi sugli HP attuali e massimi del Pokémon.
     * @param pkmn il Pokémon di riferimento.
     */
    private void updateBar(Pokemon pkmn) {
        hp = pkmn.getCurrHP();
        maxHP = pkmn.getMaxHP();

        updateColor(hp, maxHP);

        currLength = (int) Math.round(calcCurrLength(hp, maxHP));
        setLength(currLength);

    }

    /**
     * Aggiorna il colore della barra della vita in base alla percentuale di HP rimasti.
     * @param hp gli HP attuali.
     * @param maxHP gli HP massimi.
     */
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
