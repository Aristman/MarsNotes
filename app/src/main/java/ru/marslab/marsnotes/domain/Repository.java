package ru.marslab.marsnotes.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.domain.model.NoteCategory;
import ru.marslab.marsnotes.domain.model.NoteColor;

public interface Repository {

    void getNotes(Callback<List<Note>> callback);

    void getCategories(Callback<List<NoteCategory>> callback);

    void getCategoryNames(Callback<List<String>> callback);

    void getCategory(int categoryId, Callback<NoteCategory> callback);

    void getCategoryByName(String categoryName, Callback<NoteCategory> callback);

    void deleteNote(int deleteIndex, Callback<Note> callback);

    void deleteNote(Note note, Callback<Boolean> callback);

    void addNote(Note note, Callback<Integer> callback);

    void modifyNote(
            @NonNull Note note,
            @Nullable String title,
            @Nullable String description,
            @Nullable Date date,
            @Nullable NoteCategory category,
            @Nullable NoteColor color,
            Callback<Note> callback);
}
