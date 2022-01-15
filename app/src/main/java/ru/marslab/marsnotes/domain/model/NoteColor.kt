package ru.marslab.marsnotes.domain.model

enum class NoteColor(val colorId: Int, val colorName: String) {
    WHITE(0xFFFFFFFF.toInt(), "Белый"),
    RED(0xFFFF0000.toInt(), "Красный"),
    BLUE(0xFF0000FF.toInt(), "Синий"),
    GREEN(0xFF00FF00.toInt(), "Зеленый"),
    VIOLET(0xFFFF00FF.toInt(), "Фиолетовый"),
    SKY(0xFF00FFFF.toInt(), "Голубой"),
    YELLOW(0xFFFFFF00.toInt(), "Желтый"),
    GRAY(0xFF555555.toInt(), "Серый");
}
