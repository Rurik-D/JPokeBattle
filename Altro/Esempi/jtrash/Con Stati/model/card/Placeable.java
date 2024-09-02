package it.uniroma1.jtrash.model.card;

public interface Placeable {

    /**
     * Returns the position of a card with this value in the game board.
     *
     * <p>for example, Aces takes the first position. Wildcars returns -1
     */
    int getPositionInBoard();
}
