package it.uniroma1.jtrash;

import it.uniroma1.jtrash.controller.GameController;

public class Main {

    public static void main(String[] args) {
        GameController gc = new GameController();
        gc.addPlayer(true);
        gc.addPlayer(true);
        gc.addPlayer(true);
        gc.addPlayer(true);
        gc.start();

    }
}
