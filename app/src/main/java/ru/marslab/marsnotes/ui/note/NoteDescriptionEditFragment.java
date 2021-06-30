package ru.marslab.marsnotes.ui.note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import ru.marslab.marsnotes.R;

public class NoteDescriptionEditFragment extends DialogFragment {
    public static final String TAG = "NoteEditFragment";
    public static final String NOTE_TEXT_KEY = "NOTE_ID_KEY";
    public static final String NOTE_DESCRIPTION_EDIT_RESULT = "NoteEditFragment";

    private String text;
    private TextInputEditText textView;

    public static NoteDescriptionEditFragment newInstance(String text) {
        NoteDescriptionEditFragment noteEditFragment = new NoteDescriptionEditFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NOTE_TEXT_KEY, text);
        noteEditFragment.setArguments(bundle);
        return noteEditFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text = getArguments().getString(NOTE_TEXT_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_description_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(false);
        textView = view.findViewById(R.id.modify_description_text);
        textView.setText(text);
        view.findViewById(R.id.description_save_btn).setOnClickListener(v -> {
            if (textView.getText() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString(NOTE_TEXT_KEY, textView.getText().toString());
                    getParentFragmentManager().setFragmentResult(NOTE_DESCRIPTION_EDIT_RESULT, bundle);
            }
            dismiss();
        });
        view.findViewById(R.id.description_cancel_btn).setOnClickListener(v -> dismiss());
    }
}
