package it.rd.jpokebattle.model.item;

import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.model.profile.Profile;

public interface ItemStrategy {
    public void use(Profile profile, Pokemon pokemon);
}
