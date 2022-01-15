package ru.marslab.marsnotes.ui.note;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.model.Note;

@RequiresApi(api = Build.VERSION_CODES.R)
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {


    public interface OnNoteClickListener {

        void onClickListener(@NonNull Note note);
    }

    public interface OnNoteLongClickListener {

        void onLongClickListener(@NonNull Note note, int index);
    }

    private final Fragment fragment;
    private List<Note> notes = List.of();

    private OnNoteClickListener noteClickListener;

    private OnNoteLongClickListener noteLongClickListener;

    public OnNoteLongClickListener getNoteLongClickListener() {
        return noteLongClickListener;
    }

    public void setNoteLongClickListener(OnNoteLongClickListener noteLongClickListener) {
        this.noteLongClickListener = noteLongClickListener;
    }


    public OnNoteClickListener getNoteClickListener() {
        return noteClickListener;
    }

    public void setNoteClickListeners(OnNoteClickListener noteClickListener) {
        this.noteClickListener = noteClickListener;
    }

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
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
        notifyDataSetChanged();
    }

    public void deleteNote(Note deleteNote) {
        notes.remove(deleteNote);
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
            fragment.registerForContextMenu(itemView);
            itemView.setOnClickListener(v -> {
                if (getNoteClickListener() != null) {
                    getNoteClickListener().onClickListener(notes.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(v -> {
                itemView.showContextMenu();
                if (getNoteLongClickListener() != null) {
                    int index = getAdapterPosition();
                    getNoteLongClickListener().onLongClickListener(notes.get(index), index);
                }
                return true;
            });
            noteCard = itemView.findViewById(R.id.note_card);
            noteTitle = itemView.findViewById(R.id.item_notes_list_title);
            noteDescription = itemView.findViewById(R.id.item_notes_list_description);
        }
    }
}
