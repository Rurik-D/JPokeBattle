package it.uniroma1.jtrash;

public interface Observer <E extends Observable>{
    void onUpdate(E observable);
}
