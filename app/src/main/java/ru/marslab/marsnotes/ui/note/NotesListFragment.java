package ru.marslab.marsnotes.ui.note;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import ru.marslab.marsnotes.App;
import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.Repository;
import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.domain.Publisher;
import ru.marslab.marsnotes.domain.PublisherHolder;

public class NotesListFragment extends Fragment {

    private Repository repository;

    private Publisher publisher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PublisherHolder) {
            publisher = ((PublisherHolder) context).getPublisher();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        publisher = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = App.getRepository();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout notesList = view.findViewById(R.id.notes_list);
        List<Note> notes = repository.getNotes();
        for (Note note : notes) {
            View noteView =
                    LayoutInflater
                            .from(requireContext())
                            .inflate(R.layout.item_notes_list, notesList, false);
            noteView.setBackgroundColor(note.getColor().getColorId());
            noteView.setOnClickListener(v -> {
                if (publisher != null) {
                    publisher.notify(note.getId());
                }
                if (!getResources().getBoolean(R.bool.isLandscape)) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.main_fragment_container, NoteDetailsFragment.newInstance(note.getId()), null)
                            .addToBackStack(null)
                            .commit();
                }
            });
            noteView.setOnLongClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(requireContext(), v);
                popupMenu.inflate(R.menu.notes_list_item_popup_menu);
                popupMenu.setOnMenuItemClickListener(item -> {
                    int id = item.getItemId();
                    if (id == R.id.edit_note) {
                        // TODO ("Редактирование заметки")
                        Toast.makeText(requireContext(), "Edit Note", Toast.LENGTH_SHORT).show();
                    } else if (id == R.id.delete_note) {
                        // TODO ("Удаление заметки")
                        Toast.makeText(requireContext(), "Delete Note", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                });
                popupMenu.show();
                return true;
            });
            TextView noteTitle = noteView.findViewById(R.id.item_notes_list_title);
            TextView noteDescription = noteView.findViewById(R.id.item_notes_list_description);
            noteTitle.setText(note.getTitle());
            noteDescription.setText(note.getDescription());
            notesList.addView(noteView);
        }
    }
}