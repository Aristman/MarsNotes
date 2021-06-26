package ru.marslab.marsnotes.ui.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.model.Note;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    public interface OnNoteClickListener {
        void onClickListener(@NonNull Note note);
    }

    private List<Note> notes;
    private OnNoteClickListener noteClickListener;

    public OnNoteClickListener getNoteClickListener() {
        return noteClickListener;
    }

    public void setNoteClickListeners(OnNoteClickListener noteClickListener) {
        this.noteClickListener = noteClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes_list, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bing(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setListNotes(List<Note> notes) {
        this.notes = notes;
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteTitle;
        private final TextView noteDescription;
        private final CardView noteCard;

        private void bing(Note note) {
            noteCard.setCardBackgroundColor(note.getColor().getColorId());
            noteTitle.setText(note.getTitle());
            noteDescription.setText(note.getDescription());
        }

        private NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if (getNoteClickListener() != null) {
                    getNoteClickListener().onClickListener(notes.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(v -> {
                return true;
            });
            noteCard = itemView.findViewById(R.id.note_card);
            noteTitle = itemView.findViewById(R.id.item_notes_list_title);
            noteDescription = itemView.findViewById(R.id.item_notes_list_description);
        }
    }
}
