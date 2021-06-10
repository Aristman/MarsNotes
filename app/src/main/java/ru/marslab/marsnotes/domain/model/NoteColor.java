package ru.marslab.marsnotes.domain.model;

import androidx.annotation.ColorRes;

import ru.marslab.marsnotes.R;

public enum NoteColor {
    WHITE(0xFFFFFFFF),
    RED(0xFFFF0000),
    BLUE(0xFF0000FF),
    GREEN(0xFF00FF00),
    PURPLE(0xFFFF00FF),
    SKY(0xFF00FFFF),
    YELLOW(0xFFFFFF00),
    GRAY(0xFF555555);

    private final int colorId;

    NoteColor(int colorId) {
        this.colorId = colorId;
    }

    public int getColorId() {
        return colorId;
    }
}
