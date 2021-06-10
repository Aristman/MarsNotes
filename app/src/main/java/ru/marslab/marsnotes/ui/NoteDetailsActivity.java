package ru.marslab.marsnotes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;

import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.model.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    public static final String NOTE_KEY = "NOTE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        if (savedInstanceState == null) {
            if (getIntent() != null) {
                int noteId = getIntent().getIntExtra(NOTE_KEY, 1);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.note_details_fragment, NoteDetailsFragment.newInstance(noteId))
                        .commit();
            }
        }
    }
}