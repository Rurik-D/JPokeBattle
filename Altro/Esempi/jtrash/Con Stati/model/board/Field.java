package it.uniroma1.jtrash.model.board;

import it.uniroma1.jtrash.controller.card.CardDispenser;
import it.uniroma1.jtrash.controller.card.CardGenerator;
import it.uniroma1.jtrash.controller.card.CardShuffler;
import it.uniroma1.jtrash.model.card.Card;
import it.uniroma1.jtrash.model.player.Player;

import java.util.List;
import java.util.Stack;

public class Field {
    private PlayerBoard[] playerBoards;
    private Stack<Card> discardPile;
    private Stack<Card> drawPile;

    public Field(PlayerBoard[] playerBoards, Stack<Card> discardPile, Stack<Card> drawPile){
        this.playerBoards = playerBoards;
        this.discardPile = discardPile;
        this.drawPile = drawPile;
    }

    public PlayerBoard getPlayerBoard(int i) {
        return playerBoards[i];
    }

    public Stack<Card> getDrawPile() {
        return drawPile;
    }
}
