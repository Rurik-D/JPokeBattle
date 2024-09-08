package it.rd.jpokebattle.controller.battle;

import it.rd.jpokebattle.controller.NodeManager;

public class BattleNodeManager extends NodeManager {
    private static BattleController ctrl;

    public static void setController(BattleController controller) {
        ctrl = controller;
    }
}
