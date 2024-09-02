package it.rd.jpokebattle.model.pokemon;

import java.util.HashMap;

public class Pokemon {
    protected String name;
    protected Breed breed;
    protected HashMap<String, Integer> moveSet = new HashMap<>();
    protected int hp;
    protected int atk;
    protected int def;
    protected int specAtk;
    protected int specDef;
    protected int speed;
    protected int level;


    public Pokemon(Breed breed, int lv) {
        this.breed = breed;
        this.level = lv;
        setStats();
    }


    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
    }

    public String getName() {return name;}

    public int getHp() {return this.hp;}

    public int getAtk() {return this.atk;}

    public int getDef() {return this.def;}

    public int getSpecialAtk() {return this.specAtk;}

    public int getSpecialDef() {return this.specDef;}

    public int getSpeed() {return this.speed;}

    public int getLevel() {return this.level;}

    public Breed getBreed() {
        return breed;
    }


    private void setStats(){
        this.hp = 1;
        this.atk = 1;
        this.def = 1;
        this.specAtk = 1;
        this.specDef = 1;
        this.speed = 1;
        this.level = 1;
    }
}
