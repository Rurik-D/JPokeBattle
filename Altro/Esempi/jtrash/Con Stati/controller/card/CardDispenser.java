package it.uniroma1.jtrash.controller.card;

import it.uniroma1.jtrash.model.card.Card;
import it.uniroma1.jtrash.model.player.Player;

import java.util.List;

public class CardDispenser {
    private List<Card> cardsToDispense;

    public CardDispenser(List<Card> cardsToDispense) {
        this.cardsToDispense = cardsToDispense;
    }

    public Card[] dispense(Player player){
        int numOfCards = player.getCardTotal();
        List<Card> subList = cardsToDispense.subList(0,numOfCards);
        Card[] cardToSet = subList.toArray(new Card[0]);
        subList.clear();
        return cardToSet;
    }
}
