package ru.marslab.marsnotes.domain.model;

public enum NoteColor {
    WHITE(0xFFFFFFFF, "Белый"),
    RED(0xFFFF0000, "Красный"),
    BLUE(0xFF0000FF, "Синий"),
    GREEN(0xFF00FF00, "Зеленый"),
    VIOLET(0xFFFF00FF, "Фиолетовый"),
    SKY(0xFF00FFFF, "Голубой"),
    YELLOW(0xFFFFFF00, "Желтый"),
    GRAY(0xFF555555, "Серый");

    private final int colorId;
    private final String colorName;

    NoteColor(int colorId, String colorName) {
        this.colorId = colorId;
        this.colorName = colorName;
    }

    public int getColorId() {
        return colorId;
    }

    public String getColorName() {
        return colorName;
    }
}
