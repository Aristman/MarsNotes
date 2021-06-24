package ru.marslab.marsnotes.ui.note;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.marslab.marsnotes.App;
import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.Repository;
import ru.marslab.marsnotes.domain.Publisher;
import ru.marslab.marsnotes.domain.PublisherHolder;
import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.ui.FragmentRouterHolder;
import ru.marslab.marsnotes.ui.MainActivity;

public class NotesListFragment extends Fragment {

    public static final String TAG = "NotesListFragment";

    private Repository repository;

    private Publisher publisher;

    public static NotesListFragment newInstance() {
        return new NotesListFragment();
    }

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
        RecyclerView notesList = view.findViewById(R.id.notes_list_rv);
        notesList.setLayoutManager(new LinearLayoutManager(requireContext()));
        NotesAdapter notesListAdapter = new NotesAdapter();
        notesListAdapter.setNoteClickListeners(new NotesAdapter.OnNoteClickListener() {
            @Override
            public void onClickListener(@NonNull Note note) {
                if (publisher != null) {
                    publisher.notify(note.getId());
                }
                if (!getResources().getBoolean(R.bool.isLandscape)) {
                    if (requireActivity() instanceof FragmentRouterHolder) {
                        ((FragmentRouterHolder) requireActivity()).getRouter().showDetailsNote(note.getId());
                    }
                }
            }

            @Override
            public void onLongClickListener(@NonNull Note note, View v) {
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
            }
        });
        notesList.setAdapter(notesListAdapter);
        notesListAdapter.setListNotes(repository.getNotes());
    }
}