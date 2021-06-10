package ru.marslab.marsnotes;

import android.app.Application;

import ru.marslab.marsnotes.data.RepositoryImpl;
import ru.marslab.marsnotes.domain.Repository;

public class App extends Application {
    static private final Repository repository = new RepositoryImpl();

    @Override
    public void onCreate() {
        super.onCreate();

    }

    static public Repository getRepository() {
        return repository;
    }
}
