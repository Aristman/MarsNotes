package ru.marslab.marsnotes.ui.note

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.marslab.marslablib.FragmentBinding
import ru.marslab.marsnotes.App
import ru.marslab.marsnotes.R
import ru.marslab.marsnotes.databinding.FragmentNotesListBinding
import ru.marslab.marsnotes.domain.Callback
import ru.marslab.marsnotes.domain.Publisher
import ru.marslab.marsnotes.domain.PublisherHolder
import ru.marslab.marsnotes.domain.Repository
import ru.marslab.marsnotes.domain.model.Note
import ru.marslab.marsnotes.ui.FragmentRouterHolder

class NotesListFragment :
    FragmentBinding<FragmentNotesListBinding>(FragmentNotesListBinding::inflate) {
    private val repository: Repository by lazy { App.repository }
    private var publisher: Publisher? = null
    private var notesListAdapter: NotesAdapter? = null
    private var noteOnLongClicked: Note? = null
    private var noteIndexOnLongClicked = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        publisher = (context as? PublisherHolder)?.publisher
    }

    override fun onDetach() {
        super.onDetach()
        publisher = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notesList: RecyclerView = view.findViewById(R.id.notes_list_rv)
        notesList.layoutManager = LinearLayoutManager(requireContext())
        initNotesListAdapter()
        notesList.adapter = notesListAdapter
        repository.getNotes(object : Callback<List<Note>> {
            override fun onSuccess(result: List<Note>) {
                notesListAdapter?.setListNotes(result)
            }

            override fun onError() {
            }
        })
    }

    private fun initNotesListAdapter() {
        notesListAdapter = NotesAdapter(
            onClickListener = { note: Note ->
                publisher?.notify(note)
                if (!resources.getBoolean(R.bool.isLandscape)) {
                    (requireActivity() as? FragmentRouterHolder)?.router?.showDetailsNote(note)
                }
            },
            onLongClickListener = { note: Note?, index: Int ->
                noteOnLongClicked = note
                noteIndexOnLongClicked = index
            }
        )
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenuInfo?
    ) {
        requireActivity().menuInflater.inflate(R.menu.notes_list_item_context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.edit_note) {
            (requireActivity() as? FragmentRouterHolder)?.router?.showEditNote(noteOnLongClicked)
        }
        if (item.itemId == R.id.delete_note) {
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.confirm_delete_note)
                .setPositiveButton(R.string.yes) { dialog: DialogInterface, which: Int ->
                    noteOnLongClicked?.let { note ->
                        repository.deleteNote(
                            note,
                            object : Callback<Boolean> {
                                override fun onSuccess(result: Boolean) {
                                    notesListAdapter?.let {
                                        it.deleteNote(noteOnLongClicked)
                                        it.notifyItemRemoved(noteIndexOnLongClicked)
                                    }
                                    dialog.dismiss()
                                }

                                override fun onError() {
                                }
                            }
                        )
                    }
                }
                .setNegativeButton(R.string.no) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
                .show()
        }
        return super.onContextItemSelected(item)
    }

    companion object {
        const val TAG = "NotesListFragment"
        fun newInstance(): NotesListFragment {
            return NotesListFragment()
        }
    }
}
