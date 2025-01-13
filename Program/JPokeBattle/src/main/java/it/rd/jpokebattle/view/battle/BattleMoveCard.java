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
 * Classe che rappresenta una card per la selezione delle mosse durante una battaglia.
 * Ogni card è un {@link GridPane} che visualizza le informazioni principali di una mossa,
 * inclusi nome, PP rimanenti, e tipo.
 */
public class BattleMoveCard extends GridPane {
    private Move move;
    private Color borderColor;
    private Label nameLbl = new Label();
    private Label ppLbl = new Label();
    private Label typeLbl = new Label();
    private ImageView bg;

    /**
     * Costruttore che crea una card della mossa associata a un Pokémon e un indice di mossa.
     * La card visualizza dinamicamente i PP rimanenti della mossa.
     *
     * @param pkmn il Pokémon che conosce la mossa.
     * @param moveIndex l'indice della mossa nel set di mosse del Pokémon.
     */
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

    /**
     * Costruttore che crea una card per una mossa generica.
     * I PP rimanenti sono inizializzati al massimo.
     *
     * @param move la mossa da rappresentare nella card.
     */
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

    /**
     * Inizializza la card aggiungendo gli elementi grafici principali.
     */
    private void initialize() {
        setPane();
        setBg();
        setNameLbl();
        setPPLbl();
        setTypeLbl();
    }

    /**
     * Configura il layout principale della card, impostando dimensioni e margini.
     */
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

    /**
     * Imposta l'immagine di sfondo della card.
     */
    private void setBg() {
        bg = new ImageView(ResourceLoader.loadImage("img.mvCardBg"));
        bg.setFitWidth(165);
        bg.setFitHeight(70);
        this.add(bg, 0, 0, 3, 2);
    }

    /**
     * Configura l'etichetta per il nome della mossa.
     */
    private void setNameLbl() {
        nameLbl.setText(move.getName());
        nameLbl.setStyle("-fx-font-size: 18px;");
        nameLbl.setTextFill(borderColor.brighter());
        this.add(nameLbl, 0, 0);
        setColumnSpan(nameLbl, 3);
        nameLbl.setTranslateX(10);
    }

    /**
     * Configura l'etichetta per il tipo della mossa.
     */
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

    /**
     * Configura l'etichetta per i PP rimanenti della mossa.
     */
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
