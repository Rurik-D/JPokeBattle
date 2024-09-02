package it.uniroma1.jtrash.controller.card;

import it.uniroma1.jtrash.model.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardShuffler {
    private final List<Card> mazzi;

    public CardShuffler(List<Card> mazzi){
        List<Card> mazziCopy = new ArrayList<>(mazzi);
        this.mazzi = mazziCopy;
    }

    public List<Card> shuffle(){
        Collections.shuffle(mazzi);
        return mazzi;
    }
}
