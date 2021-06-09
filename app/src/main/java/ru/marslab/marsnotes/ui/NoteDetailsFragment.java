package ru.marslab.marsnotes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.marslab.marsnotes.R;

public class NoteDetailsFragment extends Fragment {

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

    }
}
