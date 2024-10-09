package it.rd.jpokebattle.view.infoPane;

import it.rd.jpokebattle.model.pokemon.OwnedPokemon;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import static it.rd.jpokebattle.model.pokemon.Stats.*;
import static it.rd.jpokebattle.model.pokemon.Stats.SPEED;

public class PokemonInfoPane extends InfoPane{
    private OwnedPokemon pkmn;
    private final ImageView avatarImgView = new ImageView();
    private final Label nameLbl = new Label();
    private final Label lvLbl = new Label();
    private final Label xpLbl = new Label();
    private final Label hpLbl = new Label();
    private final Label attLbl = new Label();
    private final Label difLbl = new Label();
    private final Label sAttLbl = new Label();
    private final Label sDifLbl = new Label();
    private final Label velLbl = new Label();

    public PokemonInfoPane(OwnedPokemon pokemon) {
        this.pkmn = pokemon;
        setLabels();
    }

    private void setLabels() {
        avatarImgView.setImage(pkmn.getBreed().getAvatar());
        nameLbl.setText("Nome: " + pkmn.getName());
        lvLbl.setText("Livello: " + pkmn.getLevel());
        xpLbl.setText("Esperienza: " + pkmn.getXp());
        hpLbl.setText("Punti Salute: " + pkmn.getStat(HP));
        attLbl.setText("Attacco: " + pkmn.getStat(ATK));
        difLbl.setText("Difesa: " + pkmn.getStat(DEF));
        sAttLbl.setText("Att. Speciale: " + pkmn.getStat(SPEC_ATK));
        sDifLbl.setText("Dif. Speciale: " + pkmn.getStat(SPEC_DEF));
        velLbl.setText("Velocità: " + pkmn.getStat(SPEED));
    }
}
