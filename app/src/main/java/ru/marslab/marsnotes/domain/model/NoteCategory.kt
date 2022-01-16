package ru.marslab.marsnotes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteCategory(
    val categoryId: Int,
    val categoryName: String
) : Parcelable
