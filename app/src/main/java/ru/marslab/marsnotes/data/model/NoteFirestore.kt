package ru.marslab.marsnotes.data.model

data class NoteFirestore(
    val title: String,
    val description: String = "",
    val date: String = "",
    val category: Int = 0,
    val color: String = ""
) {
    constructor() : this(title = "")
}
