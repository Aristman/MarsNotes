package ru.marslab.marsnotes.data;

import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
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
        int id = 1;
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
                1,
                NoteColor.RED));
        notes.add(new Note(
                id++,
                "Запись 2",
                "описание sdfad asdfasdf asdfasdf asdfasdf asdfasdf asdfasdf asdfasdf asdfasdf asdfasd fasdfasdf" +
                        "asdfasdf asdfasdf asdfasdf asdfa sdf asdfasdfasdf asdfasdfas df asdfasd fasd f asdf" +
                        "asdfasdf asdfasdf assdfasdf asdfasdf asdf   dsa f a sdf as dfasdfas записки номер 2",
                Calendar.getInstance().getTime(),
                2,
                NoteColor.YELLOW));
        notes.add(new Note(
                id,
                "Запись 3",
                "описание записки номер 3",
                Calendar.getInstance().getTime(),
                3,
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
        callback.onSuccess(categories.stream().map(NoteCategory::getName)
                .collect(Collectors.toList()));
    }

    @Override
    public void getCategory(int categoryId, Callback<NoteCategory> callback) {
        callback.onSuccess(categories.get(categoryId));
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
    public void modifyNote(String newText, Note note, Callback<Integer> callback) {
        int index = NO_ITEM_INDEX;
        if (notes.contains(note)) {
            index = notes.indexOf(note);
            notes.remove(note);
            note.setDescription(newText);
            notes.add(index, note);
        }
        callback.onSuccess(index);
    }

    @Override
    public void modifyNote(Date newDate, Note note, Callback<Integer> callback) {
        int index = NO_ITEM_INDEX;
        if (notes.contains(note)) {
            index = notes.indexOf(note);
            notes.remove(note);
            note.setDate(newDate);
            notes.add(index, note);
        }
        callback.onSuccess(index);
    }

    @Override
    public void modifyNote(NoteCategory newCategory, Note note, Callback<Integer> callback) {
        int index = NO_ITEM_INDEX;
        if (notes.contains(note)) {
            index = notes.indexOf(note);
            notes.remove(note);
            note.setCategory(newCategory.getId());
            notes.add(index, note);
        }
        callback.onSuccess(index);
    }


}
