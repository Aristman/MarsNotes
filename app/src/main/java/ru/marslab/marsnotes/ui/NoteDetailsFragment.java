package ru.marslab.marsnotes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.marslab.marsnotes.App;
import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.Repository;
import ru.marslab.marsnotes.domain.model.Note;

import static ru.marslab.marsnotes.ui.NoteDetailsActivity.NOTE_KEY;

public class NoteDetailsFragment extends Fragment {


    private Repository repository;
    private Note note;

    public static NoteDetailsFragment newInstance(int noteId) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle argsBundle = new Bundle();
        argsBundle.putInt(NOTE_KEY, noteId);
        fragment.setArguments(argsBundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = App.getRepository();

    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView noteTitle = view.findViewById(R.id.note_title);
        TextView noteDescription = view.findViewById(R.id.note_description);
        Spinner category = view.findViewById(R.id.note_category);
        EditText date = view.findViewById(R.id.note_date);
        EditText time = view.findViewById(R.id.note_time);
        view.findViewById(R.id.note_date_picker_btn).setOnClickListener(v -> {
            // TODO("Реализация клика по кнопке даты")
        });
        view.findViewById(R.id.note_time_picker_btn).setOnClickListener(v -> {
            // TODO("Реализация клика по кнопке времени")
        });

        if (getArguments() != null) {
            Note note = repository.getNote(
                    getArguments().getInt(NOTE_KEY)
            );
            noteTitle.setText(note.getTitle());
            noteDescription.setText(note.getDescription());
            category.setAdapter(getCategoryListAdapter());
            category.setSelection(note.getCategoryId());
            date.setText(note.getDate());
            time.setText(note.getTime());
            view.setBackgroundColor(note.getColor().getColorId());
        }

    }

    private ArrayAdapter<String> getCategoryListAdapter() {
        ArrayAdapter<String> categoryAdapter =
                new ArrayAdapter<>(
                        requireContext(),
                        R.layout.item_category,
                        repository.getCategoryNames()
                );
        categoryAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return categoryAdapter;
    }

}
