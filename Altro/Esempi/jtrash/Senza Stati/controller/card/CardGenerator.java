package it.uniroma1.jtrash.controller.card;

import it.uniroma1.jtrash.model.card.Card;
import it.uniroma1.jtrash.model.card.Suit;
import it.uniroma1.jtrash.model.card.values.ValueDiscard;
import it.uniroma1.jtrash.model.card.values.ValueNumber;
import it.uniroma1.jtrash.model.card.values.ValueWildcard;

import java.util.ArrayList;
import java.util.List;

public class CardGenerator {
    private final int playerCount;

    public CardGenerator(int playerCount){
        this.playerCount = playerCount;
    }

    public List<Card> generate(){
        List<Card> mazzo = new ArrayList<>();

        for (Suit s : Suit.values()) {
            for (int i = 1; i <= 10; i++){
                Card c = new Card(s, new ValueNumber(i));
                mazzo.add(c);
            }
            mazzo.add(new Card(s, ValueDiscard.JACK));
            mazzo.add(new Card(s, ValueDiscard.QUEEN));
            mazzo.add(new Card(s, ValueWildcard.KING));
        }
        for (int i = 0; i < 2; i++){
            mazzo.add(new Card(null,ValueWildcard.JOLLY));
        }
        if (playerCount >= 3) mazzo.addAll(mazzo);
        return mazzo;
    }
}
