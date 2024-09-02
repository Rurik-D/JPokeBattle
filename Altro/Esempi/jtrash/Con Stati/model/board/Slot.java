package it.uniroma1.jtrash.model.board;

import it.uniroma1.jtrash.model.card.Card;

 class Slot {
    private Card card;
    private boolean faceUp;

    public Slot(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp() {
        this.faceUp = true;
    }
}
