package ru.marslab.marsnotes.domain;

import java.util.List;

import ru.marslab.marsnotes.domain.model.Note;

public interface Repository {

    List<Note> getNotes();
}
