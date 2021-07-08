package ru.marslab.marsnotes.ui.note;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import ru.marslab.marsnotes.App;
import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.Repository;
import ru.marslab.marsnotes.domain.model.Note;
import ru.marslab.marsnotes.domain.model.NoteCategory;
import ru.marslab.marsnotes.domain.model.NoteColor;

public class NoteEditFragment extends Fragment {
    public static final String TAG = "NoteEditFragment";
    private static final String ARG_NOTE = "ARG_NOTE";

    private Repository repository;

    private boolean isEditMode;
    private Note note;

    private TextInputEditText title;
    private TextView category;
    private EditText description;
    private NoteCategory noteCategory;
    private NoteColor noteColor;
    private final ArrayList<Chip> colorChips = new ArrayList<>();
    private ChipGroup chipGroup;

    public static NoteEditFragment newInstance(@Nullable Note note) {
        Bundle bundle = new Bundle();
        NoteEditFragment noteEditFragment = new NoteEditFragment();
        bundle.putParcelable(ARG_NOTE, note);
        noteEditFragment.setArguments(bundle);
        return noteEditFragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        repository = App.getRepository();
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setListeners();
    }

    private void setListeners() {
        requireView().findViewById(R.id.note_edit_save_button).setOnClickListener(v -> {
            note.setDate(new Date());
            if (isEditMode) {
                repository.modifyNote(
                        note,
                        Objects.requireNonNull(title.getText()).toString(),
                        description.getText().toString(),
                        new Date(),
                        null,
                        noteColor,
                        this::closeFragment
                );
            } else {
                note.setColor(noteColor);
                note.setDate(new Date());
                note.setCategory(NoteCategory.getInstance().getCategoryId());
                note.setDescription(description.getText().toString());
                note.setTitle(Objects.requireNonNull(title.getText()).toString());
                repository.addNote(note, result -> getParentFragmentManager().popBackStack());
            }
        });
        requireView().findViewById(R.id.note_edit_cancel_button).setOnClickListener(
                v -> getParentFragmentManager().popBackStack()
        );
    }

    private void closeFragment(Note result) {
        getParentFragmentManager().popBackStack();
    }

    private void initView(View view) {
        if (getArguments() != null) {
            isEditMode = true;
            note = getArguments().getParcelable(ARG_NOTE);
        } else {
            isEditMode = false;
            note = Note.newInstance();
            noteColor = NoteColor.YELLOW;
        }
        title = view.findViewById(R.id.note_edit_title);
        category = view.findViewById(R.id.note_edit_category);
        description = view.findViewById(R.id.note_edit_description);
        chipGroup = view.findViewById(R.id.note_edit_colors_group);

        title.setText(note.getTitle());
        for (NoteColor value : NoteColor.values()) {
            Chip chip = new Chip(requireContext());
            chip.setChipBackgroundColor(ColorStateList.valueOf(value.getColorId()));
            chip.setText(value.getColorName());
            chip.setTag(value);
            chip.setOnClickListener(v -> {
                NoteColor color = (NoteColor) v.getTag();
                requireView().setBackgroundColor(color.getColorId());
                noteColor = color;
            });
            chipGroup.addView(chip);
            colorChips.add(chip);
        }
        description.setText(note.getDescription());
        requireView().setBackgroundColor(note.getColor().getColorId());
    }
}
