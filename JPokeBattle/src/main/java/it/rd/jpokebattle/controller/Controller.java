package it.rd.jpokebattle.controller;

import it.rd.jpokebattle.model.profile.Profile;

import java.util.Random;

public class Controller {
    private static Profile player;
    protected Random rand = new Random();

    public static Profile getPlayer() {
        return player;
    }

    public static void setPlayer(Profile p) {
        player = p;
    }

}
