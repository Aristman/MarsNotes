package ru.marslab.marsnotes.ui

import androidx.fragment.app.FragmentManager
import ru.marslab.marsnotes.R
import ru.marslab.marsnotes.domain.model.Note
import ru.marslab.marsnotes.ui.about.AboutFragment
import ru.marslab.marsnotes.ui.auth.GoogleAuthFragment
import ru.marslab.marsnotes.ui.note.NoteDetailsFragment
import ru.marslab.marsnotes.ui.note.NoteEditFragment
import ru.marslab.marsnotes.ui.note.NotesListFragment
import ru.marslab.marsnotes.ui.settings.SettingsFragment

class FragmentRouter(private val fragmentManager: FragmentManager) {
    fun showNotesList() {
        fragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                NotesListFragment.newInstance(),
                NotesListFragment.TAG
            )
            .commit()
    }

    fun showDetailsNote(note: Note?) {
        fragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                NoteDetailsFragment.newInstance(note),
                NoteDetailsFragment.TAG
            )
            .addToBackStack(NoteDetailsFragment.TAG)
            .commit()
    }

    fun showEditNote(note: Note?) {
        fragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                NoteEditFragment.newInstance(note),
                NoteEditFragment.TAG
            )
            .addToBackStack(NoteEditFragment.TAG)
            .commit()
    }

    fun showNewNote() {
        fragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                NoteEditFragment(),
                NoteEditFragment.TAG
            )
            .addToBackStack(NoteEditFragment.TAG)
            .commit()
    }

    fun showAbout() {
        fragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                AboutFragment.newInstance(),
                AboutFragment.TAG
            )
            .addToBackStack(AboutFragment.TAG)
            .commit()
    }

    fun showSettings() {
        fragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                SettingsFragment.newInstance(),
                SettingsFragment.TAG
            )
            .addToBackStack(SettingsFragment.TAG)
            .commit()
    }

    fun showGoogleAuth() {
        fragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                GoogleAuthFragment.newInstance(),
                GoogleAuthFragment.TAG
            )
            .addToBackStack(GoogleAuthFragment.TAG)
            .commit()
    }
}
