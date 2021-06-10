package ru.marslab.marsnotes.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.marslab.marsnotes.R;

import static ru.marslab.marsnotes.ui.NoteDetailsActivity.NOTE_KEY;


public class MainActivity extends AppCompatActivity implements NotesListFragment.OnNoteClicked {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onNoteClicked(int noteId) {
        if (getResources().getBoolean(R.bool.isLandscape)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.note_details_fragment, NoteDetailsFragment.newInstance(noteId))
                    .commit();
        } else {
            Intent intent = new Intent(this, NoteDetailsActivity.class);
            intent.putExtra(NOTE_KEY, noteId);
            startActivity(intent);
        }
    }
}
