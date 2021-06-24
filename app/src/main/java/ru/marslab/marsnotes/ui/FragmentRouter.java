package ru.marslab.marsnotes.ui;

import androidx.fragment.app.FragmentManager;

import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.ui.about.AboutFragment;
import ru.marslab.marsnotes.ui.note.NoteDetailsFragment;
import ru.marslab.marsnotes.ui.note.NotesListFragment;
import ru.marslab.marsnotes.ui.settings.SettingsFragment;

public class FragmentRouter {

    private final FragmentManager fragmentManager;

    public FragmentRouter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showNotesList() {
        fragmentManager.beginTransaction()
                .replace(
                        R.id.main_fragment_container,
                        NotesListFragment.newInstance(),
                        NotesListFragment.TAG
                )
                .commit();
    }

    public void showDetailsNote(int noteId) {
        fragmentManager.beginTransaction()
                .replace(
                        R.id.main_fragment_container,
                        NoteDetailsFragment.newInstance(noteId),
                        NoteDetailsFragment.TAG
                )
                .addToBackStack(NoteDetailsFragment.TAG)
                .commit();
    }

    public void showAbout() {
        fragmentManager.beginTransaction()
                .replace(
                        R.id.main_fragment_container,
                        AboutFragment.newInstance(),
                        AboutFragment.TAG
                )
                .addToBackStack(AboutFragment.TAG)
                .commit();
    }

    public void showSettings() {
        fragmentManager.beginTransaction()
                .replace(
                        R.id.main_fragment_container,
                        SettingsFragment.newInstance(),
                        SettingsFragment.TAG
                )
                .addToBackStack(SettingsFragment.TAG)
                .commit();
    }



}
