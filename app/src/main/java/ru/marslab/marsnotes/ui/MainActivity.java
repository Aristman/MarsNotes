package ru.marslab.marsnotes.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.Publisher;
import ru.marslab.marsnotes.domain.PublisherHolder;


public class MainActivity extends AppCompatActivity implements PublisherHolder {

    private final Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public Publisher getPublisher() {
        return publisher;
    }

}
