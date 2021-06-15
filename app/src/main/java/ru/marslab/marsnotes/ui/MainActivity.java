package ru.marslab.marsnotes.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.Observer;

import static ru.marslab.marsnotes.ui.NoteDetailsActivity.NOTE_KEY;


public class MainActivity extends AppCompatActivity implements PublisherHolder, Observer {

    private final Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        publisher.subscribe(this);
    }


    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public void updateNoteId(int noteId) {
        if (!getResources().getBoolean(R.bool.isLandscape)) {
            Intent intent = new Intent(this, NoteDetailsActivity.class);
            intent.putExtra(NOTE_KEY, noteId);
            startActivity(intent);
        }
    }
}
