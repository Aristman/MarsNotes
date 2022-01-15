package ru.marslab.marsnotes.domain

import ru.marslab.marsnotes.domain.model.Note

interface Observer {
    fun updateNote(note: Note)
}
