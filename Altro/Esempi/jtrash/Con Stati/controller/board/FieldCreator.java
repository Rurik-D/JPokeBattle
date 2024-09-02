package it.uniroma1.jtrash.controller.board;

import it.uniroma1.jtrash.controller.card.CardDispenser;
import it.uniroma1.jtrash.controller.card.CardGenerator;
import it.uniroma1.jtrash.controller.card.CardShuffler;
import it.uniroma1.jtrash.model.board.Field;
import it.uniroma1.jtrash.model.board.PlayerBoard;
import it.uniroma1.jtrash.model.card.Card;
import it.uniroma1.jtrash.model.player.Player;

import java.util.List;
import java.util.Stack;

public class FieldCreator {
    private final Player[] players;

    public FieldCreator(Player[] players) {
        this.players = players;
    }

    public Field create(){
        PlayerBoard[] playerBoards = new PlayerBoard[players.length];
        Stack<Card> discardPile = new Stack<>();
        CardGenerator cardGenerator = new CardGenerator(players.length);
        List<Card> mazzi = cardGenerator.generate();
        mazzi = new CardShuffler(mazzi).shuffle();
        CardDispenser dispenser = new CardDispenser(mazzi);
        for (int i = 0; i < players.length; i++){
            Card[] cards = dispenser.dispense(players[i]);
            playerBoards[i] = new PlayerBoard(cards);
        }
        Stack<Card> drawPile = new Stack<>();
        drawPile.addAll(mazzi);
        return new Field(playerBoards, discardPile, drawPile);
    }
}
