package ru.marslab.marsnotes

import android.app.Application
import ru.marslab.marsnotes.data.RepositoryImplFirestore
import ru.marslab.marsnotes.domain.Repository

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        repository = RepositoryImplFirestore()
    }

    companion object {
        lateinit var repository: Repository
    }
}
