package ru.marslab.marsnotes.domain.model

enum class NoteColor(val colorId: Int, val colorName: String) {
    WHITE(0xFFFFFF, "Белый"),
    RED(0xFF0000, "Красный"),
    BLUE(0x00FFFF, "Синий"),
    GREEN(0x00FF00, "Зеленый"),
    VIOLET(0xFF00FF, "Фиолетовый"),
    SKY(0x00FFFF, "Голубой"),
    YELLOW(0xFFFF00, "Желтый"),
    GRAY(0x555555, "Серый");
}
