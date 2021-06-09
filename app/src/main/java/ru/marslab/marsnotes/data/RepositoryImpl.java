package ru.marslab.marsnotes.data;

import java.util.ArrayList;
import java.util.List;

import ru.marslab.marsnotes.domain.Repository;
import ru.marslab.marsnotes.domain.model.Note;

public class RepositoryImpl implements Repository {
    @Override
    public List<Note> getNotes() {
        List<Note> result = new ArrayList<>();
        result.add(new Note("Запись 1", "описание записки номер 1"));
        result.add(new Note("Запись 2", "описание записки номер 2"));
        result.add(new Note("Запись 3", "описание записки номер 3"));
        return result;
    }
}
