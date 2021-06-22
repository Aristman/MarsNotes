package ru.marslab.marsnotes.ui.note;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

import ru.marslab.marsnotes.App;
import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.Observer;
import ru.marslab.marsnotes.domain.Repository;
import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.domain.Publisher;
import ru.marslab.marsnotes.domain.PublisherHolder;


public class NoteDetailsFragment extends Fragment implements Observer {

    private static final String NOTE_KEY = "note_key";

    private Repository repository;
    private Publisher publisher;
    private Note note;

    private TextView noteTitle;
    private TextView noteDescription;
    private Spinner category;
    private EditText date;
    private EditText time;
    private CardView noteDescriptionCard;

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
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notes_list_item_popup_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_note) {
            Toast.makeText(requireContext(), "Edit this Note...", Toast.LENGTH_SHORT).show();
            // TODO ("Изменение открытой заметки")
        } else if (id == R.id.delete_note) {
            Toast.makeText(requireContext(), "Delete this Note...", Toast.LENGTH_SHORT).show();
            // TODO ("Удаление открытой заметки")
        }
        return super.onOptionsItemSelected(item);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noteTitle = view.findViewById(R.id.note_title);
        noteDescription = view.findViewById(R.id.note_description);
        category = view.findViewById(R.id.note_category);
        date = view.findViewById(R.id.note_date);
        time = view.findViewById(R.id.note_time);
        noteDescriptionCard = view.findViewById(R.id.note_description_card);
        view.findViewById(R.id.note_date_picker_btn).setOnClickListener(v -> {
            DatePickerDialog dataPicker = new DatePickerDialog(
                    requireContext(),
                    (view1, year, month, dayOfMonth) -> {
                        note.setCalendarDate(year, month, dayOfMonth);
                        updateNoteInfo();
                    },
                    note.getCalendar().get(Calendar.YEAR),
                    note.getCalendar().get(Calendar.MONTH),
                    note.getCalendar().get(Calendar.DAY_OF_MONTH)
            );
            dataPicker.show();
        });
        view.findViewById(R.id.note_time_picker_btn).setOnClickListener(v -> {
            TimePickerDialog dataPicker = new TimePickerDialog(
                    requireContext(),
                    (view12, hourOfDay, minute) -> {
                        note.setCalendarTime(hourOfDay, minute);
                        updateNoteInfo();
                    },
                    note.getCalendar().get(Calendar.HOUR_OF_DAY),
                    note.getCalendar().get(Calendar.MINUTE),
                    true
            );
            dataPicker.show();
        });

        if (getArguments() != null) {
            note = repository.getNote(
                    getArguments().getInt(NOTE_KEY)
            );
            updateNoteInfo();
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PublisherHolder) {
            publisher = ((PublisherHolder) context).getPublisher();
            publisher.subscribe(this);
        }

    }

    @Override
    public void onDetach() {
        if (publisher != null) {
            publisher.unSubscribe(this);
        }
        super.onDetach();
    }

    private void updateNoteInfo() {
        View view = this.getView();
        noteTitle.setText(note.getTitle());
        noteDescription.setText(note.getDescription());
        category.setAdapter(getCategoryListAdapter());
        category.setSelection(note.getCategoryId());
        date.setText(note.getDate());
        time.setText(note.getTime());
        noteDescriptionCard.setCardBackgroundColor(note.getColor().getColorId());
        if (view != null) {
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

    @Override
    public void updateNoteId(int noteId) {
        note = repository.getNote(noteId);
        updateNoteInfo();
    }
}
