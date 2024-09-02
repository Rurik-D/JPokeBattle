package it.uniroma1.jtrash.model.state.turn_2;

public class TurnData {
    private int playerID;
    private int nextPlayer;
    private int previousPlayer;
    private int lastPlayer;
    private int playerCount;

    public TurnData(int playerID, int playerCount) {
        playerID %= playerCount;
        this.playerID = playerID;
        this.nextPlayer = (playerID + 1) % playerCount;
        this.previousPlayer = playerID - 1;
        if (previousPlayer < 0) {previousPlayer = playerCount - 1;}
        this.lastPlayer = - 1;
        this.playerCount = playerCount;
    }

    public TurnData next(boolean lastTurn){
        TurnData newTurn = new TurnData(playerID + 1, playerCount);
        newTurn.lastPlayer = lastTurn ? this.previousPlayer : this.lastPlayer;
        return newTurn;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getNextPlayer() {
        return nextPlayer;
    }

    public int getPreviousPlayer() {
        return previousPlayer;
    }

    public int getLastPlayer() {
        return lastPlayer;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}
