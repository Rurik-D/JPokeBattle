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

/**
 * Classe che definisce le move card per la lotta.
 * Tali card sono dei grid pane che mostrano tutte le informazioni della mossa.
 */
public class BattleMoveCard extends GridPane {
    private Move move;
    private Color borderColor;
    private Label nameLbl = new Label();
    private Label ppLbl = new Label();
    private Label typeLbl = new Label();
    private ImageView bg;


    public BattleMoveCard(Pokemon pkmn, int moveIndex) {
        this.move = pkmn.getMove(moveIndex);
        this.borderColor = Type.getLabelBorderColor(pkmn.getMove(moveIndex).getType());
        initialize();
        ppLbl.textProperty().bind(Bindings.format("%d/%d",
                        pkmn.getPpProperties().get(moveIndex),
                        move.getPP()
                )
        );
    }

    public BattleMoveCard(Move move) {
        this.move = move;
        this.borderColor = Type.getLabelBorderColor(move.getType());
        initialize();
        ppLbl.textProperty().bind(Bindings.format("%d/%d",
                        move.getPP(),
                        move.getPP()
                )
        );
    }

    private void initialize() {
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
        nameLbl.setText(move.getName());
        nameLbl.setStyle("-fx-font-size: 18px;");
        nameLbl.setTextFill(borderColor.brighter());
        this.add(nameLbl, 0, 0);
        setColumnSpan(nameLbl, 3);
        nameLbl.setTranslateX(10);
    }

    private void setTypeLbl() {
        typeLbl.setText(move.getType().getFormattedName());
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
        ppLbl.setStyle("-fx-font-size: 15px; -fx-text-fill: #D1D1D1;");
        ppLbl.setTranslateX(-20);
        ppLbl.setTranslateY(-10);
        this.add(ppLbl, 1, 1);
        setColumnSpan(ppLbl, 2);
        setValignment(ppLbl, VPos.BOTTOM);
        setHalignment(ppLbl, HPos.RIGHT);
    }
}
