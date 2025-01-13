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

/**
 * Classe che rappresenta una "card" grafica selezionabile per visualizzare le informazioni
 * di un Pokémon posseduto. Le card mostrano il nome, il livello, l'immagine, i punti vita (HP),
 * e una barra dell'esperienza del Pokémon.
 */
public class PokemonCard extends GridPane {
    private OwnedPokemon pkmn;
    private ImageView pkmnImg;
    private ImageView lifeCard = new ImageView();
    private Line lifeBar;
    private Line xpLine = new Line();
    private Label nameLbl = new Label();
    private Label levelLbl = new Label();
    private Label lifeLbl = new Label();

    /**
     * Costruttore che inizializza una nuova card per il Pokémon specificato.
     *
     * @param pkmn Il Pokémon da rappresentare nella card.
     */
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

    /**
     * Configura il layout principale del pannello della card.
     * Imposta dimensioni, colore di sfondo, e stile del bordo.
     */
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

    /**
     * Configura l'etichetta per il livello del Pokémon.
     * Mostra il livello corrente.
     */
    private void setLevelLbl() {
        String text = "Lv. " + pkmn.getLevel();
        levelLbl.setText(text);
        levelLbl.setTranslateY(10);
        this.add(levelLbl, 1, 1);
        setColumnSpan(levelLbl, 2);
        setValignment(levelLbl, VPos.TOP);
        setHalignment(levelLbl, HPos.CENTER);
    }

    /**
     * Configura l'etichetta per il nome del Pokémon.
     * Mostra il nome corrente.
     */
    private void setNameLbl() {
        nameLbl.setText(pkmn.getBreed().getName());
        nameLbl.setStyle("-fx-font-size: 18px;");
        nameLbl.setTranslateY(5);
        this.add(nameLbl, 1, 1);
        setColumnSpan(nameLbl, 2);
        setValignment(nameLbl, VPos.CENTER);
        setHalignment(nameLbl, HPos.CENTER);
    }

    /**
     * Configura l'immagine del Pokémon.
     * Mostra un'animazione o un'immagine statica in base ai suoi punti vita (HP).
     */
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

    /**
     * Configura la cornice della barra della vita.
     * Utilizza un'immagine come sfondo.
     */
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

    /**
     * Configura la barra della vita del Pokémon.
     * Mostra graficamente i punti vita attuali rispetto al massimo.
     */
    private void setLifeBar() {
        lifeBar = new LifeBar(pkmn, 105.0, 6.7);
        lifeBar.setTranslateX(29.5);
        lifeBar.setTranslateY(-6.7);
        this.add(lifeBar, 1, 0);
        setColumnSpan(lifeBar, 2);
        setRowSpan(lifeBar, 2);
        setValignment(lifeBar, VPos.BOTTOM);
    }

    /**
     * Configura la barra della vita del Pokémon.
     * Mostra graficamente i punti vita attuali rispetto al massimo.
     */
    private void setXpLine() {
        double maxL = 108;
        double xpRange =
                PokemonManager.getXPTreshold(pkmn.getLevel()+1) -
                PokemonManager.getXPTreshold(pkmn.getLevel());
        double xpToNxtLv = pkmn.getXpToNextLv();
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

    /**
     * Configura l'etichetta per i punti vita (HP) del Pokémon.
     * Mostra gli HP attuali e il massimo.
     */
    private void setLifeLbl() {
        String text = pkmn.getCurrHP() + " / " + pkmn.getStat(Stats.HP);
        lifeLbl.setText(text);
        this.add(lifeLbl, 2, 2);
        setValignment(lifeLbl, VPos.TOP);
        setHalignment(lifeLbl, HPos.CENTER);
    }
}
