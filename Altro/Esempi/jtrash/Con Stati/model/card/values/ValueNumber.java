package it.uniroma1.jtrash.model.card.values;

import it.uniroma1.jtrash.model.card.Placeable;

import java.util.Objects;

public class ValueNumber implements Value, Placeable {
    private final int value;

    public ValueNumber(int value) {
        if (value > 10 || value <= 0) throw new AssertionError("Value must be from 1 to 10");
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int getPositionInBoard() {
        return value - 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueNumber that = (ValueNumber) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
