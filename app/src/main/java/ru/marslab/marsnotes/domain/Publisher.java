package ru.marslab.marsnotes.domain;

import java.util.ArrayList;
import java.util.List;

import ru.marslab.marsnotes.domain.Observer;

public class Publisher {

    private final List<Observer> observers = new ArrayList<>();

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unSubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notify(int noteId) {
        for (Observer observer : observers) {
            observer.updateNoteId(noteId);
        }
    }

}
