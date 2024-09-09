package it.rd.jpokebattle.view.arcade;

import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.sql.Time;

public class BattleTransition {
    private static BattleTransition istance;
    private final Image transitionOpGif;
    private final Image transitionClsGif;
    private final ImageView transOp;
    private final ImageView transCls;

    private BattleTransition () {
        transitionOpGif = ResourceLoader.loadImage("gif.transOp");
        transOp = new ImageView(transitionOpGif);
        transOp.setId("battleTransition");
        transOp.setFitWidth(1000);

        transitionClsGif = ResourceLoader.loadImage("gif.transCls");
        transCls = new ImageView(transitionClsGif);
        transCls.setId("battleTransition");
        transCls.setFitWidth(1000);
    };

    public static BattleTransition getIstance() {
        if (istance == null)
            return new BattleTransition();
        return istance;
    }


    public void startCloseTrans(GridPane root) {
        root.add(transCls, 0, 0,5, 11);
    }

    public void startOpenTrans(GridPane root) {
        root.add(transOp, 0, 0,5, 11);
    }
}
