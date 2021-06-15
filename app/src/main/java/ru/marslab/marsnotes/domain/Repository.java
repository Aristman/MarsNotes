package ru.marslab.marsnotes.domain;

import java.util.List;

import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.domain.model.NoteCategory;

public interface Repository {

    List<Note> getNotes();

    List<NoteCategory> getCategories();

    Note getNote(int noteId);

    List<String> getCategoryNames();

    NoteCategory getCategory(int categoryId);
}
