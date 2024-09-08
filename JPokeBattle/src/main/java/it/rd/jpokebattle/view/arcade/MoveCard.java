package it.rd.jpokebattle.view.arcade;

import it.rd.jpokebattle.model.move.Move;
import it.rd.jpokebattle.model.pokemon.Type;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class MoveCard extends GridPane {
    private Move move;
    private int curPP;
    private Color borderColor;
    private Label nameLbl = new Label();
    private Label ppLbl = new Label();
    private Label typeLbl = new Label();


    public MoveCard(Move move, int curPP) {
        this.move = move;
        this.curPP = curPP;
        this.borderColor = Type.getLabelBorderColor(move.getType());
        setPane();
        setNameLbl();
        setPPLbl();
        setTypeLbl();
    }


    private void setPane() {
        this.setCursor(Cursor.HAND);
        this.setMinSize(240, 70);
        this.setPrefSize(240, 70);
        this.setMaxSize(240, 70);
        this.setVisible(true);

        this.getColumnConstraints().add(new ColumnConstraints(80));
        this.getColumnConstraints().add(new ColumnConstraints(80));
        this.getColumnConstraints().add(new ColumnConstraints(80));

        this.getRowConstraints().add(new RowConstraints(35));
        this.getRowConstraints().add(new RowConstraints(35));



        BorderStroke bs = new BorderStroke(
                borderColor,
                BorderStrokeStyle.SOLID,
                new CornerRadii(15, 0, 15, 0, false),
                new BorderWidths(2.5)
        );

        this.setBorder(new Border(bs));
    }

    private void setNameLbl() {
        nameLbl.setText(move.getName());
        nameLbl.setStyle("-fx-font-size: 20px;");
        this.add(nameLbl, 0, 0);
        setColumnSpan(nameLbl, 3);
        nameLbl.setTranslateX(5);
    }

    private void setTypeLbl() {
        typeLbl.setText(move.getType());
        typeLbl.setStyle("-fx-font-size: 18px;");
        typeLbl.setTranslateX(15);
        typeLbl.setTranslateY(-6);
        typeLbl.setPadding(new Insets(0, 3, 0, 3));
        this.add(typeLbl, 0, 1);
        setValignment(typeLbl, VPos.CENTER);

        BorderStroke b = new BorderStroke(
                borderColor,
                BorderStrokeStyle.SOLID,
                new CornerRadii(20),
                new BorderWidths(1)
        );

        typeLbl.setBorder(new Border (b));

    }

    private void setPPLbl() {
        ppLbl.setText("PP " + curPP + " / " + move.getPP());
        ppLbl.setStyle("-fx-font-size: 18px;");
        ppLbl.setTranslateX(-20);
        ppLbl.setTranslateY(-10);
        this.add(ppLbl, 1, 1);
        setColumnSpan(ppLbl, 2);
        setValignment(ppLbl, VPos.BOTTOM);
        setHalignment(ppLbl, HPos.RIGHT);
    }


}
