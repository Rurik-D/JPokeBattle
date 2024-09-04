package it.rd.jpokebattle.view.arcade;


import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
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

public class PokemonCard extends GridPane {
    private OwnedPokemon pkmn;
    private ImageView pkmnGif;
    private ImageView lifeBar = new ImageView();
    private Label nameLbl = new Label();
    private Label levelLbl = new Label();
    private Label lifeLbl = new Label();

    public PokemonCard(OwnedPokemon pkmn) {
        this.pkmn = pkmn;
        setPane();
        setNameLbl();
        setPkmnGif();
        setLevelLbl();
        setLifeBar();
        setLifeLbl();
    }


    private void setPane() {
        this.setCursor(Cursor.HAND);
        this.setPrefSize(240, 110);
        this.setVisible(true);

        BackgroundFill bg = new BackgroundFill(
        Color.rgb(40,40,40),
        new CornerRadii(10),
        Insets.EMPTY);

        this.setBackground(new Background(bg));

    }

    private void setLevelLbl() {
        String text = "Lv. " + pkmn.getLevel();
        levelLbl.setText(text);
        levelLbl.setTranslateY(8);
        this.add(levelLbl, 1, 1);
        setColumnSpan(levelLbl, 2);
        setValignment(levelLbl, VPos.TOP);
        setHalignment(levelLbl, HPos.CENTER);
    }

    private void setNameLbl() {
        nameLbl.setText(pkmn.getName());
        levelLbl.setTranslateY(12);
        this.add(nameLbl, 1, 1);
        setColumnSpan(nameLbl, 2);
        setValignment(nameLbl, VPos.CENTER);
        setHalignment(nameLbl, HPos.CENTER);
    }

    private void setPkmnGif() {
        pkmnGif = new ImageView(pkmn.getBreed().getFrontGif());
        pkmnGif.setPreserveRatio(true);
        pkmnGif.setFitWidth(80);
        pkmnGif.setTranslateX(5);
        pkmnGif.setTranslateY(5);
        this.add(pkmnGif, 0, 0);
        setRowSpan(pkmnGif, 2);
        setValignment(pkmnGif, VPos.BOTTOM);
    }

    private void setLifeBar() {
        Image img = ResourceLoader.loadImage("img.lifeBar");
        lifeBar = new ImageView(img);
        lifeBar.setPreserveRatio(true);
        lifeBar.setFitWidth(140);
        lifeBar.setTranslateX(5);
        lifeBar.setTranslateY(20);
        this.add(lifeBar, 1, 1);
        setColumnSpan(lifeBar, 2);
        setRowSpan(lifeBar, 2);
        setValignment(lifeBar, VPos.CENTER);
        setHalignment(lifeBar, HPos.CENTER);
    }

    private void setLifeLbl() {
        String text = pkmn.getCurrHP() + " / " + pkmn.getStat(Stats.HP);
        lifeLbl.setText(text);
        lifeLbl.setTranslateY(7);
        this.add(lifeLbl, 2, 1);
        setValignment(lifeLbl, VPos.BOTTOM);
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

