package ru.marslab.marsnotes.domain;

import java.util.Date;
import java.util.List;

import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.domain.model.NoteCategory;

public interface Repository {

    void getNotes(Callback<List<Note>> callback);

    void getCategories(Callback<List<NoteCategory>> callback);

    void getCategoryNames(Callback<List<String>> callback);

    void getCategory(int categoryId, Callback<NoteCategory> callback);

    void deleteNote(int deleteIndex, Callback<Note> deletedNote);

    void deleteNote(Note note, Callback<Boolean> deletedIndex);

    void addNote(Note note, Callback<Integer> newIndex);

    void modifyNote(String noteText, Note note, Callback<Integer> result);

    void modifyNote(Date noteDate,Note note, Callback<Integer> result);

    void modifyNote(NoteCategory noteCategory,Note note, Callback<Integer> result);
}
