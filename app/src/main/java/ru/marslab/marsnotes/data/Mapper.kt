package ru.marslab.marsnotes.data

import ru.marslab.marsnotes.data.model.NoteFirestore
import ru.marslab.marsnotes.domain.model.Note
import ru.marslab.marsnotes.domain.model.NoteColor
import java.util.Calendar

fun NoteFirestore.toNote(id: String): Note =
    Note(
        id = id,
        title = title,
        description = description,
        date = date,
        calendar = Calendar.getInstance(),
        categoryId = category,
        color = NoteColor.valueOf(color)
    )

fun Note.toFirestore(): NoteFirestore =
    NoteFirestore(
        title = title,
        description = description,
        date = date,
        category = categoryId,
        color = color.name
    )
