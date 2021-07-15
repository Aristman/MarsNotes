package ru.marslab.marsnotes;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import ru.marslab.marsnotes.data.RepositoryImplFirestore;
import ru.marslab.marsnotes.domain.Repository;

public class App extends Application {
    static private Repository repository;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate() {
        super.onCreate();
//        repository = new RepositoryImpl();
        repository = new RepositoryImplFirestore();
    }

    static public Repository getRepository() {
        return repository;
    }
}
