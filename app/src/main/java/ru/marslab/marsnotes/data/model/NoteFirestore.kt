package ru.marslab.marsnotes.data.model

import java.time.LocalDateTime

data class NoteFirestore(
    val title: String,
    val description: String,
    val date: LocalDateTime,
    val category: Int,
    val color: String
)
