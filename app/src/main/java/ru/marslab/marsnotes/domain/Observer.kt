package ru.marslab.marsnotes.domain;

import ru.marslab.marsnotes.domain.model.Note;

public interface Observer {

    void updateNote(Note note);
}
