package it.rd.jpokebattle.view.arcade;


import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import it.rd.jpokebattle.model.pokemon.PokemonManager;
import it.rd.jpokebattle.model.pokemon.Stats;
import it.rd.jpokebattle.util.file.ResourceLoader;
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
import javafx.scene.text.Font;

public class PokemonCard extends GridPane {
    private OwnedPokemon pkmn;
    private ImageView pkmnImg;
    private ImageView lifeBar = new ImageView();
    private Line lifeLine = new Line();
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
        setLifeBar();
        setLifeLine();
        setXpLine();
        setLifeLbl();
    }


    private void setPane() {
        this.setCursor(Cursor.HAND);
        this.setPrefSize(240, 110);
        this.setVisible(true);

        BackgroundFill bg = new BackgroundFill(
        Color.rgb(40,40,40),
        new CornerRadii(18),
        Insets.EMPTY);

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

    private void setLifeBar() {
        String src = "img.lifeBarE";
        Image img = ResourceLoader.loadImage(src);
        lifeBar = new ImageView(img);
        lifeBar.setPreserveRatio(true);
        lifeBar.setFitWidth(140);
        lifeBar.setTranslateX(5);
        lifeBar.setTranslateY(-20);
        this.add(lifeBar, 1, 1);
        setColumnSpan(lifeBar, 2);
        setRowSpan(lifeBar, 2);
        setValignment(lifeBar, VPos.BOTTOM);
        setHalignment(lifeBar, HPos.CENTER);
    }

    private void setLifeLine() {
        double maxL = 105;
        double maxHP = pkmn.getStat(Stats.HP);
        double hp = pkmn.getCurrHP();
        // length : maxL = hp : maxHP
        double length = (hp * maxL) / maxHP;

        Color lineColor;
        if (hp > maxHP/2)
            lineColor = Color.rgb(30, 230, 30);
        else if (hp > maxHP/4)
            lineColor = Color.rgb(230, 220, 30);
        else
            lineColor = Color.rgb(230, 30, 30);

        lifeLine.setStartX(0);
        lifeLine.setEndX(length);
        lifeLine.setStroke(lineColor);
        lifeLine.setStrokeWidth(6.7);
        lifeLine.setTranslateX(29.5);
        lifeLine.setTranslateY(-6.7);
        this.add(lifeLine, 1, 0);
        setColumnSpan(lifeLine, 2);
        setRowSpan(lifeLine, 2);
        setValignment(lifeLine, VPos.BOTTOM);
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
        System.out.println(length);

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

