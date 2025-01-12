package it.rd.jpokebattle.view.bar;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Classe astratta che rappresenta una barra generica, basata su una linea orizzontale.
 * La barra può essere utilizzata per rappresentare valori percentuali, come punti vita o energia.
 */
public abstract class Bar extends Line {
    // Costanti per i colori standard utilizzati nelle barre
    protected final Color GREEN = Color.rgb(30, 230, 30);
    protected final Color YELLOW = Color.rgb(230, 220, 30);
    protected final Color RED = Color.rgb(230, 30, 30);
    protected final Color BLUE = Color.rgb(52, 234, 234);
    private double maxLength;

    /**
     * Costruttore per creare una barra con lunghezza massima e spessore specificati.
     *
     * @param maxL    lunghezza massima della barra.
     * @param strokeW spessore della linea che rappresenta la barra.
     */
    public Bar(double maxL, double strokeW) {
        super(0, 0, maxL, 0);
        setVisible(true);
        setMaxLength(maxL);
        setStrokeWidth(strokeW);
    }

    /**
     * Imposta la lunghezza attuale della barra.
     * Se la lunghezza è pari a zero, la barra diventa invisibile.
     *
     * @param length lunghezza attuale della barra.
     */
    public void setLength(double length) {
        setEndX(length);
        if (length == 0)
            setVisible(false);
    }

    /**
     * Restituisce la lunghezza massima della barra.
     *
     * @return la lunghezza massima.
     */
    public double getMaxLength() {
        return maxLength;
    }

    /**
     * Imposta la lunghezza massima della barra.
     *
     * @param maxLength la lunghezza massima desiderata.
     */
    public void setMaxLength(double maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * Calcola la lunghezza attuale della barra in base al valore corrente e al valore massimo.
     * Formula: <code>currLength : maxLength = currVal : maxVal</code>
     *
     * @param currVal valore corrente.
     * @param maxVal valore massimo.
     * @return la lunghezza attuale calcolata per la barra.
     */
    public double calcCurrLength(double currVal, double maxVal) {
        return Math.min(maxLength, (currVal * maxLength) / maxVal);
    }

}
