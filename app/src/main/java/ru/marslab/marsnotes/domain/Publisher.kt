package ru.marslab.marsnotes.domain;

import java.util.ArrayList;
import java.util.List;

import ru.marslab.marsnotes.domain.model.Note;

public class Publisher {

    private final List<Observer> observers = new ArrayList<>();

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unSubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notify(Note note) {
        for (Observer observer : observers) {
            observer.updateNote(note);
        }
    }

}
