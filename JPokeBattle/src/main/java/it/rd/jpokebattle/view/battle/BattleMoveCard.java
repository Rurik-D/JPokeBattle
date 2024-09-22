package it.rd.jpokebattle.view.battle;

import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.model.pokemon.Type;
import it.rd.jpokebattle.util.file.ResourceLoader;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BattleMoveCard extends GridPane {
    private Pokemon pkmn;
    private int moveIndex;
    private Color borderColor;
    private Label nameLbl = new Label();
    private Label ppLbl = new Label();
    private Label typeLbl = new Label();
    private ImageView bg;


    public BattleMoveCard(Pokemon pkmn, int moveIndex) {
        this.pkmn = pkmn;
        this.moveIndex = moveIndex;
        this.borderColor = Type.getLabelBorderColor(pkmn.getMove(moveIndex).getType());
        setPane();
        setBg();
        setNameLbl();
        setPPLbl();
        setTypeLbl();
    }


    private void setPane() {
        this.setCursor(Cursor.HAND);
        this.setPrefSize(165, 70);
        this.setVisible(true);

        this.getColumnConstraints().add(new ColumnConstraints(55));
        this.getColumnConstraints().add(new ColumnConstraints(55));
        this.getColumnConstraints().add(new ColumnConstraints(55));

        this.getRowConstraints().add(new RowConstraints(35));
        this.getRowConstraints().add(new RowConstraints(35));


        BackgroundFill bf = new BackgroundFill(
                Color.TRANSPARENT,
                new CornerRadii(0, 0, 0, 0, false),
                new Insets(0,0,0,0)
        );

        BorderStroke bs = new BorderStroke(
                Color.TRANSPARENT,
                BorderStrokeStyle.SOLID,
                new CornerRadii(0, 0, 0, 0, false),
                new BorderWidths(0)
        );

        this.setBackground(new Background(bf));
        this.setBorder(new Border(bs));
    }


    private void setBg() {
        bg = new ImageView(ResourceLoader.loadImage("img.mvCardBg"));
        bg.setFitWidth(165);
        bg.setFitHeight(70);
        this.add(bg, 0, 0, 3, 2);
    }


    private void setNameLbl() {
        nameLbl.setText(pkmn.getMove(moveIndex).getName());
        nameLbl.setStyle("-fx-font-size: 18px;");
        nameLbl.setTextFill(borderColor.brighter());
        this.add(nameLbl, 0, 0);
        setColumnSpan(nameLbl, 3);
        nameLbl.setTranslateX(10);
    }

    private void setTypeLbl() {
        typeLbl.setText(pkmn.getMove(moveIndex).getType().getFormattedName());
        typeLbl.setStyle("-fx-font-size: 11px; -fx-text-fill: #D1D1D1;");
        typeLbl.setTranslateX(15);
        typeLbl.setTranslateY(-6);
        typeLbl.setPadding(new Insets(0, 3, 0, 3));
        this.add(typeLbl, 0, 1);
        setValignment(typeLbl, VPos.CENTER);

        BorderStroke b = new BorderStroke(
                borderColor,
                BorderStrokeStyle.SOLID,
                new CornerRadii(20),
                new BorderWidths(1.2)
        );

        typeLbl.setBorder(new Border (b));

    }

    private void setPPLbl() {
        int curPP = pkmn.getPP(moveIndex);
        ppLbl.textProperty().bind(Bindings.format("%d/%d",
                pkmn.getPpProperties().get(moveIndex),
                pkmn.getMove(moveIndex).getPP()
            )
        );

        ppLbl.setStyle("-fx-font-size: 15px; -fx-text-fill: #D1D1D1;");
        ppLbl.setTranslateX(-20);
        ppLbl.setTranslateY(-10);
        this.add(ppLbl, 1, 1);
        setColumnSpan(ppLbl, 2);
        setValignment(ppLbl, VPos.BOTTOM);
        setHalignment(ppLbl, HPos.RIGHT);
    }
}
