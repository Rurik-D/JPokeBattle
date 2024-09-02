package it.uniroma1.jtrash.model.player;

public class Player {
    private int cardTotal;
    private int playerID;
    private boolean saidTrash;

    public Player(int cardTotal, int playerID){
        this.cardTotal = cardTotal;
        this.playerID = playerID;
    }

    public int getCardTotal() {
        return cardTotal;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public boolean hasSaidTrash() {
        return saidTrash;
    }
}
