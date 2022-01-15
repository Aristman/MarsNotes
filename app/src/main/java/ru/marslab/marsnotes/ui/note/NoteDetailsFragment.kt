package ru.marslab.marsnotes.ui.note;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import ru.marslab.marsnotes.App;
import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.Observer;
import ru.marslab.marsnotes.domain.Publisher;
import ru.marslab.marsnotes.domain.PublisherHolder;
import ru.marslab.marsnotes.domain.Repository;
import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.domain.model.NoteCategory;


public class NoteDetailsFragment extends Fragment implements Observer {

    private static final String NOTE_KEY = "note_key";
    public static final String TAG = "NoteDetailsFragment";

    private Repository repository;
    private Publisher publisher;
    private Note note;
    private List<NoteCategory> noteCategories;
    private NoteCategory noteCategory;

    private TextView noteTitle;
    private TextView noteDescription;
    private TextView category;
    private TextView date;
    private TextView time;
    private CardView noteDescriptionCard;

    public static NoteDetailsFragment newInstance(Note note) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle argsBundle = new Bundle();
        argsBundle.putParcelable(NOTE_KEY, note);
        fragment.setArguments(argsBundle);
        return fragment;
    }

    private static void onSuccess(Integer result) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = App.getRepository();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.notes_list_item_context_menu, menu);
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
        initView(view);
        setListeners(view);

        getChildFragmentManager().setFragmentResultListener(
                NoteDescriptionEditFragment.NOTE_DESCRIPTION_EDIT_RESULT,
                getViewLifecycleOwner(),
                (requestKey, result) -> {
                    String newNoteText = result.getString(NoteDescriptionEditFragment.NOTE_TEXT_KEY);
                    noteDescription.setText(newNoteText);
                    repository.modifyNote(
                            note,
                            null,
                            newNoteText,
                            null,
                            null,
                            null,
                            this::updateNote);
                });
        if (getArguments() != null) {
            note = getArguments().getParcelable(NOTE_KEY);
            updateNoteInfo();
        }

        repository.getCategories(result -> noteCategories = result);
        repository.getCategory(note.getCategoryId(), result -> noteCategory = result);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setListeners(View view) {
        view.findViewById(R.id.note_date).setOnClickListener(v -> showDataPicker());
        view.findViewById(R.id.note_time).setOnClickListener(v -> showTimePicker());

        noteTitle.setOnClickListener(v -> showEditTitleDialog());

        noteDescription.setOnClickListener(v -> showEditDescriptionDialog());

        category.setOnClickListener(v -> showCategoriesDialog());
    }

    private void showEditTitleDialog() {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_title, null, false);
        EditText textTitle = view.findViewById(R.id.title_edit_text_view);
        textTitle.setText(note.getTitle());
        new AlertDialog.Builder(requireContext())
                .setCancelable(false)
                .setView(view)
                .setPositiveButton(R.string.save_string, (dialog, which) -> repository.modifyNote(
                        note,
                        textTitle.getText().toString(),
                        null,
                        null,
                        null,
                        null,
                        this::updateNote
                ))
                .setNegativeButton(R.string.cancel_string, (dialog, which) -> dialog.dismiss())
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showCategoriesDialog() {
        AtomicInteger selectIndex = new AtomicInteger(noteCategories.indexOf(noteCategory));
        new MaterialAlertDialogBuilder(requireContext())
                .setSingleChoiceItems(
                        noteCategories.stream().map(NoteCategory::getCategoryName).toArray(String[]::new),
                        selectIndex.get(),
                        (dialog, which) -> selectIndex.set(which))
                .setPositiveButton(R.string.save_string, (dialog, which) -> {
                    int index = selectIndex.get();
                    NoteCategory selectCategory = noteCategories.get(index);
                    if (selectCategory.getCategoryId() != note.getCategoryId()) {
                        repository.modifyNote(
                                note,
                                null,
                                null,
                                null,
                                selectCategory,
                                null,
                                this::updateNote
                        );
                    }
                })
                .setNegativeButton(R.string.cancel_string, null)
                .show();
    }

    private void initView(View view) {
        noteTitle = view.findViewById(R.id.note_title);
        noteDescription = view.findViewById(R.id.note_description);
        category = view.findViewById(R.id.note_category);
        date = view.findViewById(R.id.note_date);
        time = view.findViewById(R.id.note_time);
        noteDescriptionCard = view.findViewById(R.id.note_description_card);
    }

    private void showEditDescriptionDialog() {
        NoteDescriptionEditFragment.newInstance(noteDescription.getText().toString())
                .show(getChildFragmentManager(), NoteDescriptionEditFragment.TAG);
    }

    private void showTimePicker() {
        TimePickerDialog dataPicker = new TimePickerDialog(
                requireContext(),
                (view12, hourOfDay, minute) -> {
                    Calendar newDate = note.getCalendar();
                    newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    newDate.set(Calendar.MINUTE, minute);
                    modifyNoteDate(newDate);
                },
                note.getCalendar().get(Calendar.HOUR_OF_DAY),
                note.getCalendar().get(Calendar.MINUTE),
                true
        );
        dataPicker.show();
    }

    private void showDataPicker() {
        DatePickerDialog dataPicker = new DatePickerDialog(
                requireContext(),
                (view1, year, month, dayOfMonth) -> {
                    Calendar newDate = note.getCalendar();
                    newDate.set(Calendar.YEAR, year);
                    newDate.set(Calendar.MONTH, month);
                    newDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    modifyNoteDate(newDate);
                },
                note.getCalendar().get(Calendar.YEAR),
                note.getCalendar().get(Calendar.MONTH),
                note.getCalendar().get(Calendar.DAY_OF_MONTH)
        );
        dataPicker.show();
    }

    private void modifyNoteDate(Calendar newDate) {
        repository.modifyNote(
                note,
                null,
                null,
                newDate.getTime(),
                null,
                null,
                this::updateNote);
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
        repository.getCategory(note.getCategoryId(), result -> {
            category.setText(result.getCategoryName());
            noteCategory = result;
        });
        date.setText(note.getDate());
        time.setText(note.getTime());
        noteDescriptionCard.setCardBackgroundColor(note.getColor().getColorId());
        if (view != null) {
            view.setBackgroundColor(note.getColor().getColorId());

        }
    }

    @Override
    public void updateNote(Note note) {
        this.note = note;
        updateNoteInfo();
    }
}
