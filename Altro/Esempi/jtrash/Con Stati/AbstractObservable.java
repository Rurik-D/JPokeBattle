package it.uniroma1.jtrash;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractObservable implements Observable{
    private List<Observer <?>> observerList;

    public AbstractObservable(){
        this.observerList = new ArrayList<>();
    }

    @Override
    public void update() {
        for (Observer obs : observerList) {
            obs.onUpdate(this);
        }
    }

    public void addObserver(Observer <?> o){
        observerList.add(o);
    }

    public void removeObserver(Observer <?> o){
        observerList.remove(o);
    }

    public void clearObservers(){
        observerList.clear();
    }
}
