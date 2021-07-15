package ru.marslab.marsnotes.ui.note;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.marslab.marsnotes.App;
import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.Publisher;
import ru.marslab.marsnotes.domain.PublisherHolder;
import ru.marslab.marsnotes.domain.Repository;
import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.ui.FragmentRouterHolder;

@RequiresApi(api = Build.VERSION_CODES.R)
public class NotesListFragment extends Fragment {

    public static final String TAG = "NotesListFragment";

    private Repository repository;

    private Publisher publisher;
    private NotesAdapter notesListAdapter;
    private Note noteOnLongClicked;
    private int noteIndexOnLongClicked;

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
        initNotesListAdapter();
        notesList.setAdapter(notesListAdapter);
        repository.getNotes(result -> notesListAdapter.setListNotes(result));
    }


    private void initNotesListAdapter() {
        notesListAdapter = new NotesAdapter(this);
        notesListAdapter.setNoteClickListeners(note -> {
            if (publisher != null) {
                publisher.notify(note);
            }
            if (!getResources().getBoolean(R.bool.isLandscape)) {
                if (requireActivity() instanceof FragmentRouterHolder) {
                    ((FragmentRouterHolder) requireActivity()).getRouter().showDetailsNote(note);
                }
            }
        });
        notesListAdapter.setNoteLongClickListener((note, index) -> {
            noteOnLongClicked = note;
            noteIndexOnLongClicked = index;
        });
    }

    @Override
    public void onCreateContextMenu(
            @NonNull ContextMenu menu,
            @NonNull View v,
            @Nullable ContextMenu.ContextMenuInfo menuInfo
    ) {
        requireActivity().getMenuInflater().inflate(R.menu.notes_list_item_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit_note) {
            if (requireActivity() instanceof FragmentRouterHolder) {
                ((FragmentRouterHolder) requireActivity()).getRouter().showEditNote(noteOnLongClicked);
            }
        }
        if (item.getItemId() == R.id.delete_note) {
            repository.deleteNote(noteOnLongClicked, result -> {
                notesListAdapter.deleteNote(noteOnLongClicked);
                notesListAdapter.notifyItemRemoved(noteIndexOnLongClicked);
            });
        }
        return super.onContextItemSelected(item);
    }
}