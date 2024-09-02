package it.uniroma1.jtrash.model.card.values;

import it.uniroma1.jtrash.model.card.Placeable;

public enum ValueWildcard implements Value, Placeable {
    JOLLY, KING;
    @Override
    public int getPositionInBoard() {
        return -1;
    }
}
