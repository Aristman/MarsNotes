package ru.marslab.marsnotes.domain

import ru.marslab.marsnotes.domain.model.Note
import ru.marslab.marsnotes.domain.model.NoteCategory
import ru.marslab.marsnotes.domain.model.NoteColor
import java.time.LocalDateTime

interface Repository {
    fun init()
    fun getNotes(callback: Callback<List<Note>>)
    fun getCategories(callback: Callback<List<NoteCategory>>)
    fun getCategoryNames(callback: Callback<List<String>>)
    fun getCategory(categoryId: Int, callback: Callback<NoteCategory>)
    fun getCategoryByName(categoryName: String, callback: Callback<NoteCategory>)
    fun deleteNote(deleteIndex: Int, callback: Callback<Note>)
    fun deleteNote(note: Note, callback: Callback<Boolean>)
    fun addNote(note: Note, callback: Callback<Int>)
    fun modifyNote(
        note: Note,
        title: String?,
        description: String?,
        date: LocalDateTime?,
        category: NoteCategory?,
        color: NoteColor?,
        callback: Callback<Note>
    )
}
