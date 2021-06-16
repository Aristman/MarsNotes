package ru.marslab.marsnotes.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.marslab.marsnotes.domain.Repository;
import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.domain.model.NoteCategory;
import ru.marslab.marsnotes.domain.model.NoteColor;

public class RepositoryImpl implements Repository {
    private List<NoteCategory> categories;
    private List<Note> notes;

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
    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public List<NoteCategory> getCategories() {
        return categories;
    }

    @Override
    public Note getNote(int noteId) {
        for (Note note : notes) {
            if (note.getId() == noteId) {
                return note;
            }
        }
        return null;
    }

    @Override
    public List<String> getCategoryNames() {
        List<String> result = new ArrayList<>();
        for (NoteCategory category : categories) {
            result.add(category.getName());
        }
        return result;
    }

    @Override
    public NoteCategory getCategory(int categoryId) {
        for (NoteCategory category : categories) {
            if (category.getId() == categoryId) {
                return category;
            }
        }
        return NoteCategory.getInstance();
    }

}
