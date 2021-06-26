package ru.marslab.marsnotes.domain;

import java.util.List;

import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.domain.model.NoteCategory;

public interface Repository {

    List<Note> getNotes();

    List<NoteCategory> getCategories();

    List<String> getCategoryNames();

    NoteCategory getCategory(int categoryId);

    void deleteNote(int deleteIndex);

    void deleteNote(Note note);
}
