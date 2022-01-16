package ru.marslab.marsnotes.ui.note

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.marslab.marsnotes.databinding.ItemNotesListBinding
import ru.marslab.marsnotes.domain.model.Note
import ru.marslab.marsnotes.ui.note.NotesAdapter.NoteViewHolder

class NotesAdapter(
    private val onClickListener: (note: Note) -> Unit,
    private val onLongClickListener: (note: Note, index: Int) -> Unit
) : RecyclerView.Adapter<NoteViewHolder>() {

    private val notes = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ItemNotesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding, onClickListener, onLongClickListener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bing(notes[position])
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListNotes(notes: List<Note>) {
        this.notes.apply {
            clear()
            addAll(notes)
        }
        notifyDataSetChanged()
    }

    fun deleteNote(deleteNote: Note?) {
        notes.remove(deleteNote)
    }

    inner class NoteViewHolder(
        private val binding: ItemNotesListBinding,
        private val onClickListener: (note: Note) -> Unit,
        private val onLongClickListener: (note: Note, index: Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bing(note: Note) {
            binding.noteCard.setCardBackgroundColor(note.color.colorId)
            binding.itemNotesListTitle.text = note.title
            binding.itemNotesListDescription.text = note.description
            binding.root.setOnClickListener {
                onClickListener(note)
            }
            binding.root.setOnLongClickListener {
                onLongClickListener(note, adapterPosition)
                true
            }
        }
    }
}
