package ru.marslab.marsnotes.data

import ru.marslab.marsnotes.domain.Callback
import ru.marslab.marsnotes.domain.Repository
import ru.marslab.marsnotes.domain.model.Note
import ru.marslab.marsnotes.domain.model.NoteCategory
import ru.marslab.marsnotes.domain.model.NoteColor
import java.time.LocalDateTime

class RepositoryImpl : Repository {
    private val categories = mutableListOf<NoteCategory>()
    private val notes = mutableListOf<Note>()
    override fun init() {
    }

    override fun getNotes(callback: Callback<List<Note>>) {
        callback.onSuccess(notes)
    }

    override fun getCategories(callback: Callback<List<NoteCategory>>) {
        callback.onSuccess(categories)
    }

    override fun getCategoryNames(callback: Callback<List<String>>) {
        callback.onSuccess(categories.map { it.categoryName })
    }

    override fun getCategory(categoryId: Int, callback: Callback<NoteCategory>) {
        for (category in categories) {
            if (category.categoryId == categoryId) {
                callback.onSuccess(category)
            }
        }
    }

    override fun getCategoryByName(categoryName: String, callback: Callback<NoteCategory>) {
        for (category in categories) {
            if (category.categoryName == categoryName) {
                callback.onSuccess(category)
            }
        }
    }

    override fun deleteNote(deleteIndex: Int, callback: Callback<Note>) {
        callback.onSuccess(notes.removeAt(deleteIndex))
    }

    override fun deleteNote(note: Note, callback: Callback<Boolean>) {
        callback.onSuccess(notes.remove(note))
    }

    override fun addNote(note: Note, callback: Callback<Int>) {
        if (notes.add(note)) {
            callback.onSuccess(notes.size - 1)
        } else {
            callback.onSuccess(NO_ITEM_INDEX)
        }
    }

    override fun modifyNote(
        note: Note,
        title: String?,
        description: String?,
        date: LocalDateTime?,
        category: NoteCategory?,
        color: NoteColor?,
        callback: Callback<Note>
    ) {
        callback.onSuccess(
            note.also { modifyNote ->
                title?.let { modifyNote.copy(title = it) }
                description?.let { modifyNote.copy(description = it) }
                date?.let { modifyNote.copy(date = it) }
                category?.let { modifyNote.copy(categoryId = it.categoryId) }
                color?.let { modifyNote.copy(color = it) }
                callback.onSuccess(modifyNote)
            }
        )
    }

    companion object {
        const val NO_ITEM_INDEX = -1
        const val ERROR_TAG = "NOTES_ERROR_TAG"
    }
}
