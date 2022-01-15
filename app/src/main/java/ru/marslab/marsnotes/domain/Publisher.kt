package ru.marslab.marsnotes.domain

import ru.marslab.marsnotes.domain.model.Note

class Publisher {
    private val observers = mutableListOf<Observer>()
    fun subscribe(observer: Observer) {
        observers.add(observer)
    }

    fun unSubscribe(observer: Observer) {
        observers.remove(observer)
    }

    fun notify(note: Note) {
        observers.forEach { it.updateNote(note) }
    }
}
