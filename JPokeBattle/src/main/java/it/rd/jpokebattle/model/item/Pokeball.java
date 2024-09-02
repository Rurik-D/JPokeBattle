package it.rd.jpokebattle.model.item;

import it.rd.jpokebattle.model.pokemon.Pokemon;
import it.rd.jpokebattle.model.profile.Profile;

import java.util.Random;

public class Pokeball implements ItemStrategy {
    private Random random = new Random();

    @Override
    public void use(Profile profile, Pokemon pokemon) {
        random.nextBoolean();
    }
}
