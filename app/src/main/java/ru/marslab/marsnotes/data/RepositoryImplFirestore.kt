package ru.marslab.marsnotes.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import ru.marslab.marsnotes.data.model.NoteFirestore
import ru.marslab.marsnotes.domain.Callback
import ru.marslab.marsnotes.domain.Repository
import ru.marslab.marsnotes.domain.model.Note
import ru.marslab.marsnotes.domain.model.NoteCategory
import ru.marslab.marsnotes.domain.model.NoteColor
import java.time.LocalDateTime
import java.util.function.Consumer

private const val COLLECTION_NOTES = "marsNotes"
private const val COLLECTION_NOTES_CATEGORY = "noteCategories"
private const val CATEGORY_NAME = "name"

class RepositoryImplFirestore : Repository {

    private val categories = mutableListOf<NoteCategory>()
    private val notes = mutableListOf<Note>()
    private val fireStore = FirebaseFirestore.getInstance()

    override fun init() {
        fireStore.collection(COLLECTION_NOTES)
            .get()
            .addOnSuccessListener { command: QuerySnapshot ->
                command.documents.forEach(
                    Consumer { documentSnapshot: DocumentSnapshot ->
                        val newNote = documentSnapshot.toObject(
                            NoteFirestore::class.java
                        )
                        newNote?.let {
                            notes.add(it.toNote(documentSnapshot.id))
                        }
                    }
                )
            }
        fireStore.collection(COLLECTION_NOTES_CATEGORY)
            .get()
            .addOnCompleteListener { task: Task<QuerySnapshot?> ->
                val resultDocuments = task.result
                if (resultDocuments != null) {
                    for (document in resultDocuments.documents) {
                        categories.add(
                            NoteCategory(
                                categoryId = document.id.toInt(),
                                categoryName = document.getString(CATEGORY_NAME).toString()
                            )
                        )
                    }
                }
            }
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
        categories.find { it.categoryId == categoryId }?.let { callback.onSuccess(it) }
            ?: callback.onError()
    }

    override fun getCategoryByName(categoryName: String, callback: Callback<NoteCategory>) {
        categories.find { it.categoryName == categoryName }?.let { callback.onSuccess(it) }
            ?: callback.onError()
    }

    override fun deleteNote(deleteIndex: Int, callback: Callback<Note>) {
        val removeNote = notes.removeAt(deleteIndex)
        fireStore.collection(COLLECTION_NOTES)
            .document(removeNote.id)
            .delete()
            .addOnSuccessListener { callback.onSuccess(removeNote) }
            .addOnFailureListener { notes.add(deleteIndex, removeNote) }
    }

    override fun deleteNote(note: Note, callback: Callback<Boolean>) {
        fireStore.collection(COLLECTION_NOTES)
            .document(note.id)
            .delete()
            .addOnSuccessListener {
                callback.onSuccess(notes.remove(note))
            }
    }

    override fun addNote(note: Note, callback: Callback<Int>) {
        fireStore.collection(COLLECTION_NOTES)
            .add(note.toFirestore())
            .addOnSuccessListener { command: DocumentReference? ->
                if (notes.add(note)) {
                    callback.onSuccess(notes.size - 1)
                } else {
                    callback.onSuccess(NO_ITEM_INDEX)
                }
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
        val noteFirestore = note.also { modifyNote ->
            title?.let { modifyNote.copy(title = it) }
            description?.let { modifyNote.copy(description = it) }
            date?.let { modifyNote.copy(date = it) }
            category?.let { modifyNote.copy(categoryId = it.categoryId) }
            color?.let { modifyNote.copy(color = it) }
            callback.onSuccess(modifyNote)
        }.toFirestore()
        fireStore.collection(COLLECTION_NOTES)
            .document(note.id)
            .set(noteFirestore)
            .addOnSuccessListener {
                val replaceIndex = notes.indexOf(note)
                notes.remove(note)
                notes.add(replaceIndex, noteFirestore.toNote(note.id))
                callback.onSuccess(notes[replaceIndex])
            }
    }

    companion object {
        const val NO_ITEM_INDEX = -1
    }
}
