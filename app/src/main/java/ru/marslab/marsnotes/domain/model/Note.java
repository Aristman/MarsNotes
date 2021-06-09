package ru.marslab.marsnotes.domain.model;

import java.util.Date;

public class Note {

    private String title;
    private String description;
    private Long created;
    private NoteCategory category;
    private NoteColor color;

    public Note(String title, String description, Long created, NoteCategory category, NoteColor color) {
        this.title = title;
        this.description = description;
        this.created = created;
        this.category = category;
        this.color = color;
    }

    public Note(String title, String description) {
        this(
                title,
                description,
                new Date().getTime(),
                new NoteCategory(NoteCategory.ID_NO_CATEGORY, NoteCategory.NO_CATEGORY),
                NoteColor.WHITE
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

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public NoteCategory getCategory() {
        return category;
    }

    public void setCategory(NoteCategory category) {
        this.category = category;
    }

    public NoteColor getColor() {
        return color;
    }

    public void setColor(NoteColor color) {
        this.color = color;
    }
}
