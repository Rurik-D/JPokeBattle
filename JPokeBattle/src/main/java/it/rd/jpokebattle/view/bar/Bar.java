package it.rd.jpokebattle.view.bar;

import it.rd.jpokebattle.model.pokemon.Pokemon;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public abstract class Bar extends Line {
    protected final Color GREEN = Color.rgb(30, 230, 30);
    protected final Color YELLOW = Color.rgb(230, 220, 30);
    protected final Color RED = Color.rgb(230, 30, 30);
    protected final Color BLUE = Color.rgb(52, 234, 234);
    private double maxLength;

    public Bar(double maxL, double strokeW) {
        super(0, 0, maxL, 0);

        setMaxLength(maxL);
        setStrokeWidth(strokeW);
    }

    public double getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(double maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * currLength : maxLength = currVal : maxVal
     * @return currLength = (currVal * maxLength) / maxVal
     */
    public double calcCurrLength(double currVal, double maxVal) {
        return Math.min(maxLength, (currVal * maxLength) / maxVal);
    }

}
