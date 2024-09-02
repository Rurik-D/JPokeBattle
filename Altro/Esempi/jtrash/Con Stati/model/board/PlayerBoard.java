package it.uniroma1.jtrash.model.board;

import it.uniroma1.jtrash.AbstractObservable;
import it.uniroma1.jtrash.model.card.Card;

import java.util.Arrays;

public class PlayerBoard extends AbstractObservable {
    private final Slot[] slots;

    public PlayerBoard(Card[] cards) {
        slots = Arrays.stream(cards)
                .map(Slot::new)             //.map((card)->new Slot(card))
                .toArray(Slot[]::new);
    }

    /**
     * Return the card in the slot.
     */
    public Card getCardInSlot(int slot){
        return slots[slot].getCard();
    }

    /**
     * Places the card in the given slot.
     *
     * @return the old Card that was in the slot.
     */
    public Card setCardInSlot(int slot, Card newCard){
        Card oldCard = this.getCardInSlot(slot);
        slots[slot].setCard(newCard);
        this.update();
        return oldCard;
    }

    public int getSlotSize(){
        return slots.length;
    }
}
