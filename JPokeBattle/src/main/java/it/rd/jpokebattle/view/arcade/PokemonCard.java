package it.rd.jpokebattle.view.arcade;


import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.PokemonManager;
import it.rd.jpokebattle.model.pokemon.Stats;
import it.rd.jpokebattle.util.file.ResourceLoader;
import it.rd.jpokebattle.view.bar.LifeBar;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class PokemonCard extends GridPane {
    private OwnedPokemon pkmn;
    private ImageView pkmnImg;
    private ImageView lifeCard = new ImageView();
    private Line lifeBar;
    private Line xpLine = new Line();
    private Label nameLbl = new Label();
    private Label levelLbl = new Label();
    private Label lifeLbl = new Label();

    public PokemonCard(OwnedPokemon pkmn) {
        this.pkmn = pkmn;
        setPane();
        setNameLbl();
        setPkmnImage();
        setLevelLbl();
        setLifeCard();
        setLifeBar();
        setXpLine();
        setLifeLbl();
    }


    private void setPane() {
        this.setCursor(Cursor.HAND);
        this.setPrefSize(240, 110);
        this.setVisible(true);

        BackgroundFill bg = new BackgroundFill(
                Color.rgb(30,30,30),
                new CornerRadii(18, 1, 18, 1, false),
                Insets.EMPTY
        );

        BorderStroke bs = new BorderStroke(
                Color.rgb(85, 85, 10),
                BorderStrokeStyle.SOLID,
                new CornerRadii(15, 0, 15, 0, false),
                new BorderWidths(3)
        );

        this.setBackground(new Background(bg));
        this.setBorder(new Border(bs));

    }

    private void setLevelLbl() {
        String text = "Lv. " + pkmn.getLevel();
        levelLbl.setText(text);
        levelLbl.setTranslateY(10);
        this.add(levelLbl, 1, 1);
        setColumnSpan(levelLbl, 2);
        setValignment(levelLbl, VPos.TOP);
        setHalignment(levelLbl, HPos.CENTER);
    }

    private void setNameLbl() {
        nameLbl.setText(pkmn.getName());
        nameLbl.setStyle("-fx-font-size: 18px;");
        nameLbl.setTranslateY(5);
        this.add(nameLbl, 1, 1);
        setColumnSpan(nameLbl, 2);
        setValignment(nameLbl, VPos.CENTER);
        setHalignment(nameLbl, HPos.CENTER);
    }

    private void setPkmnImage() {
        Image img;
        if (pkmn.getCurrHP() > 0)
            img = pkmn.getBreed().getFrontGif();
        else
            img = pkmn.getBreed().getAvatar();

        pkmnImg = new ImageView(img);
        pkmnImg.setPreserveRatio(true);
        pkmnImg.setFitWidth(80);
        pkmnImg.setTranslateX(5);
        pkmnImg.setTranslateY(15);
        this.add(pkmnImg, 0, 0);
        setRowSpan(pkmnImg, 2);
        setValignment(pkmnImg, VPos.BOTTOM);
    }

    private void setLifeCard() {
        String src = "img.lifeBar";
        Image img = ResourceLoader.loadImage(src);
        lifeCard = new ImageView(img);
        lifeCard.setPreserveRatio(true);
        lifeCard.setFitWidth(140);
        lifeCard.setTranslateX(5);
        lifeCard.setTranslateY(-20);
        this.add(lifeCard, 1, 1);
        setColumnSpan(lifeCard, 2);
        setRowSpan(lifeCard, 2);
        setValignment(lifeCard, VPos.BOTTOM);
        setHalignment(lifeCard, HPos.CENTER);
    }

    private void setLifeBar() {
        lifeBar = new LifeBar(pkmn, 105.0, 6.7);
        lifeBar.setTranslateX(29.5);
        lifeBar.setTranslateY(-6.7);
        this.add(lifeBar, 1, 0);
        setColumnSpan(lifeBar, 2);
        setRowSpan(lifeBar, 2);
        setValignment(lifeBar, VPos.BOTTOM);
    }


    private void setXpLine() {
        double maxL = 108;
        double xpRange =
                PokemonManager.getXPTreshold(pkmn.getLevel()+1) -
                PokemonManager.getXPTreshold(pkmn.getLevel());
        double xpToNxtLv = pkmn.getXpToNextLv();
        // length : maxL = (xr - xtnl) : xr
        // length = ((xr - xtnl) * maxL) / xr
        double length = ((xpRange - xpToNxtLv) * maxL) / xpRange;

        xpLine.setStartX(0);
        xpLine.setEndX(length);
        xpLine.setStroke(Color.rgb(52, 234, 234));
        xpLine.setStrokeWidth(2);
        xpLine.setTranslateX(30);
        xpLine.setTranslateY(-1.5);
        this.add(xpLine, 1, 0);
        setColumnSpan(xpLine, 2);
        setRowSpan(xpLine, 2);
        setValignment(xpLine, VPos.BOTTOM);
    }


    private void setLifeLbl() {
        String text = pkmn.getCurrHP() + " / " + pkmn.getStat(Stats.HP);
        lifeLbl.setText(text);
        this.add(lifeLbl, 2, 2);
        setValignment(lifeLbl, VPos.TOP);
        setHalignment(lifeLbl, HPos.CENTER);
    }
}



//        BackgroundFill bg = new BackgroundFill(
//        Color.AQUAMARINE,
//        new CornerRadii(10),
//        Insets.EMPTY);
//
//        this.setBackground(new Background(bg));
//        BorderStroke borderStroke = new BorderStroke(
//        Color.BLACK,
//        BorderStrokeStyle.SOLID,
//        new CornerRadii(10),
//        new BorderWidths(5)
//);

