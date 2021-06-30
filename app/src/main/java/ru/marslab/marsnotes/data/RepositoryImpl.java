package ru.marslab.marsnotes.data;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ru.marslab.marsnotes.domain.Callback;
import ru.marslab.marsnotes.domain.Repository;
import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.domain.model.NoteCategory;
import ru.marslab.marsnotes.domain.model.NoteColor;

public class RepositoryImpl implements Repository {
    public static final int NO_ITEM_INDEX = -1;
    public static final String ERROR_TAG = "NOTES_ERROR_TAG";


    private final List<NoteCategory> categories;
    private final List<Note> notes;

    public RepositoryImpl() {
        notes = new ArrayList<>();
        categories = new ArrayList<>();
        int id = 10;
        categories.add(new NoteCategory(id++, "Категория 1"));
        categories.add(new NoteCategory(id++, "Категория 2"));
        categories.add(new NoteCategory(id++, "Категория 3"));
        categories.add(new NoteCategory(id, "Категория 4"));
        id = 1;
        notes.add(new Note(
                id++,
                "Запись 1",
                "описание записки номер 1",
                Calendar.getInstance().getTime(),
                11,
                NoteColor.RED));
        notes.add(new Note(
                id++,
                "Запись 2",
                "описание sdfad asdfasdf asdfasdf asdfasdf asdfasdf asdfasdf asdfasdf asdfasdf asdfasd fasdfasdf" +
                        "asdfasdf asdfasdf asdfasdf asdfa sdf asdfasdfasdf asdfasdfas df asdfasd fasd f asdf" +
                        "asdfasdf asdfasdf assdfasdf asdfasdf asdf   dsa f a sdf as dfasdfas записки номер 2",
                Calendar.getInstance().getTime(),
                12,
                NoteColor.YELLOW));
        notes.add(new Note(
                id,
                "Запись 3",
                "описание записки номер 3",
                Calendar.getInstance().getTime(),
                10,
                NoteColor.VIOLET));
    }

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        callback.onSuccess(notes);
    }

    @Override
    public void getCategories(Callback<List<NoteCategory>> callback) {
        callback.onSuccess(categories);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getCategoryNames(Callback<List<String>> callback) {
        callback.onSuccess(categories.stream().map(NoteCategory::getCategoryName)
                .collect(Collectors.toList()));
    }

    @Override
    public void getCategory(int categoryId, Callback<NoteCategory> callback) {
        for (NoteCategory category : categories) {
            if (category.getCategoryId() == categoryId) {
                callback.onSuccess(category);
            }
        }
    }

    @Override
    public void getCategoryByName(String categoryName, Callback<NoteCategory> callback) {
        for (NoteCategory category : categories) {
            if (category.getCategoryName().equals(categoryName)) {
                callback.onSuccess(category);
            }
        }
    }

    @Override
    public void deleteNote(int deleteIndex, Callback<Note> callback) {
        callback.onSuccess(notes.remove(deleteIndex));
    }

    @Override
    public void deleteNote(Note note, Callback<Boolean> callback) {
        callback.onSuccess(notes.remove(note));
    }

    @Override
    public void addNote(Note note, Callback<Integer> callback) {
        if (notes.add(note)) {
            callback.onSuccess(notes.size() - 1);
        } else {
            callback.onSuccess(NO_ITEM_INDEX);
        }
    }

    @Override
    public void modifyNote(
            @NonNull Note note,
            @Nullable String title,
            @Nullable String description,
            @Nullable Date date,
            @Nullable NoteCategory category,
            @Nullable NoteColor color,
            Callback<Note> callback) {
        if (title != null) {
            note.setTitle(title);
        }
        if (description != null) {
            note.setDescription(description);
        }
        if (date != null) {
            note.setDate(date);
        }
        if (category != null) {
            note.setCategory(category.getCategoryId());
        }
        if (color != null) {
            note.setColor(color);
        }
        callback.onSuccess(note);
    }
}
