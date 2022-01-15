package ru.marslab.marsnotes.data.model;

import java.util.Date;

import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.domain.model.NoteColor;

public class NoteFirestore {
    private String title;
    private String description;
    private Date date;
    private int category;
    private String color;

    public NoteFirestore() {
    }

    public NoteFirestore(String title, String description, Date date, int category, String color) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.category = category;
        this.color = color;
    }

    public Note toNote(String id) {
        return new Note(
                id,
                this.title,
                this.description,
                this.date,
                this.category,
                NoteColor.valueOf(color)
        );
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
