package ru.marslab.marsnotes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.data.RepositoryImpl;
import ru.marslab.marsnotes.domain.Repository;
import ru.marslab.marsnotes.domain.model.Note;

public class NotesListFragment extends Fragment {

    private Repository repository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new RepositoryImpl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout notesList = view.findViewById(R.id.notes_list_container);
        List<Note> notes = repository.getNotes();
        for (Note note : notes) {
            View noteView =
                    LayoutInflater
                            .from(requireContext())
                            .inflate(R.layout.item_notes_list, notesList, false);
            TextView noteTitle = noteView.findViewById(R.id.title_note_list_item);
            TextView noteDescription = noteView.findViewById(R.id.description_note_list_item);
            noteTitle.setText(note.getTitle());
            noteDescription.setText(note.getDescription());
            notesList.addView(noteView);
        }
    }
}