package ru.marslab.marsnotes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.util.Calendar

@Parcelize
data class Note(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: LocalDateTime = LocalDateTime.now(),
    val calendar: Calendar = Calendar.getInstance(),
    val categoryId: Int = 0,
    val color: NoteColor = NoteColor.YELLOW,
) : Parcelable
