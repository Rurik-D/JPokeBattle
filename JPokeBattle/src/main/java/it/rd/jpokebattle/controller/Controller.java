package it.rd.jpokebattle.controller;

import it.rd.jpokebattle.model.profile.Profile;

public class Controller {
    private static Profile player;

    public static Profile getPlayer() {
        return player;
    }

    public static void setPlayer(Profile p) {
        player = p;
    }
}
