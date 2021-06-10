package ru.marslab.marsnotes.domain.model;

public class NoteCategory {
    static final String NO_CATEGORY = "No category";
    static final int ID_NO_CATEGORY = 0;

    public static NoteCategory getInstance() {
        return new NoteCategory(ID_NO_CATEGORY, NO_CATEGORY);
    }

    private final int id;
    private final String name;

    public NoteCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
