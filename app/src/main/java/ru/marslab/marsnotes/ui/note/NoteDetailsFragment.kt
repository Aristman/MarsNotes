package ru.marslab.marsnotes.ui.note

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.marslab.marslablib.FragmentBinding
import ru.marslab.marsnotes.App
import ru.marslab.marsnotes.R
import ru.marslab.marsnotes.databinding.FragmentNoteDetailsBinding
import ru.marslab.marsnotes.domain.Callback
import ru.marslab.marsnotes.domain.Observer
import ru.marslab.marsnotes.domain.Publisher
import ru.marslab.marsnotes.domain.PublisherHolder
import ru.marslab.marsnotes.domain.Repository
import ru.marslab.marsnotes.domain.model.Note
import ru.marslab.marsnotes.domain.model.NoteCategory
import java.time.LocalDateTime
import java.util.Calendar
import java.util.concurrent.atomic.AtomicInteger

class NoteDetailsFragment :
    FragmentBinding<FragmentNoteDetailsBinding>(FragmentNoteDetailsBinding::inflate), Observer {
    private val repository: Repository by lazy { App.repository }
    private val publisher: Publisher by lazy { (requireActivity() as PublisherHolder).publisher }
    private var note: Note = Note()
    private var noteCategories: List<NoteCategory> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_list_item_context_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_note -> {
                Toast.makeText(requireContext(), "Edit this Note...", Toast.LENGTH_SHORT).show()
                // TODO ("Изменение открытой заметки")
                return true
            }
            R.id.delete_note -> {
                Toast.makeText(requireContext(), "Delete this Note...", Toast.LENGTH_SHORT).show()
                // TODO ("Удаление открытой заметки")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        childFragmentManager.setFragmentResultListener(
            NoteDescriptionEditFragment.NOTE_DESCRIPTION_EDIT_RESULT,
            viewLifecycleOwner,
            { requestKey: String?, result: Bundle ->
                val newNoteText =
                    result.getString(NoteDescriptionEditFragment.NOTE_TEXT_KEY).toString()
                binding.noteDescription.text = newNoteText
                repository.modifyNote(
                    note,
                    null,
                    newNoteText,
                    null,
                    null,
                    null,
                    NoteDetailsCallback(
                        callbackSuccessful = { updateNote(it) }
                    )
                )
            }
        )
        arguments?.let {
            note = it.getParcelable(NOTE_KEY) ?: Note()
            updateNoteInfo()
        }
        repository.getCategories(
            NoteDetailsCallback(
                callbackSuccessful = { noteCategories = it }
            )
        )
        repository.getCategory(
            note.categoryId,
            NoteDetailsCallback(
                callbackSuccessful = { note = note.copy(categoryId = it.categoryId) }
            )
        )
    }

    private fun setListeners() {
        binding.noteDate.setOnClickListener { showDataPicker() }
        binding.noteTime.setOnClickListener { showTimePicker() }
        binding.noteTitle.setOnClickListener { showEditTitleDialog() }
        binding.noteDescription.setOnClickListener { showEditDescriptionDialog() }
        binding.noteCategory.setOnClickListener { showCategoriesDialog() }
    }

    private fun showEditTitleDialog() {
        val view =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_title, null, false)
        val textTitle = view.findViewById<EditText>(R.id.title_edit_text_view)
        textTitle.setText(note.title)
        AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setView(view)
            .setPositiveButton(R.string.save_string) { dialog: DialogInterface?, which: Int ->
                repository.modifyNote(
                    note,
                    textTitle.text.toString(),
                    null,
                    null,
                    null,
                    null,
                    NoteDetailsCallback(
                        callbackSuccessful = { updateNote(it) }
                    )
                )
            }
            .setNegativeButton(R.string.cancel_string) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            .show()
    }

    private fun showCategoriesDialog() {
        val selectIndex = AtomicInteger(note.categoryId)
        MaterialAlertDialogBuilder(requireContext())
            .setSingleChoiceItems(
                noteCategories.map { it.categoryName }.toTypedArray(),
                selectIndex.get()
            ) { dialog: DialogInterface?, which: Int -> selectIndex.set(which) }
            .setPositiveButton(R.string.save_string) { dialog: DialogInterface?, which: Int ->
                val index = selectIndex.get()
                val selectCategory = noteCategories[index]
                if (selectCategory.categoryId != note.categoryId) {
                    repository.modifyNote(
                        note,
                        null,
                        null,
                        null,
                        selectCategory,
                        null,
                        NoteDetailsCallback(
                            callbackSuccessful = { updateNote(it) }
                        )
                    )
                }
            }
            .setNegativeButton(R.string.cancel_string, null)
            .show()
    }

    private fun showEditDescriptionDialog() {
        NoteDescriptionEditFragment.newInstance(binding.noteDescription.text.toString())
            .show(childFragmentManager, NoteDescriptionEditFragment.TAG)
    }

    private fun showTimePicker() {
        val dataPicker = TimePickerDialog(
            requireContext(),
            { view12: TimePicker?, hourOfDay: Int, minute: Int ->
                val newDate = note.calendar
                newDate[Calendar.HOUR_OF_DAY] = hourOfDay
                newDate[Calendar.MINUTE] = minute
                modifyNoteDate(newDate)
            },
            note.calendar[Calendar.HOUR_OF_DAY],
            note.calendar[Calendar.MINUTE],
            true
        )
        dataPicker.show()
    }

    private fun showDataPicker() {
        val dataPicker = DatePickerDialog(
            requireContext(),
            { view1: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                val newDate = note.calendar
                newDate[Calendar.YEAR] = year
                newDate[Calendar.MONTH] = month
                newDate[Calendar.DAY_OF_MONTH] = dayOfMonth
                modifyNoteDate(newDate)
            },
            note.calendar[Calendar.YEAR],
            note.calendar[Calendar.MONTH],
            note.calendar[Calendar.DAY_OF_MONTH]
        )
        dataPicker.show()
    }

    private fun modifyNoteDate(newDate: Calendar) {
        repository.modifyNote(
            note,
            null,
            null,
            LocalDateTime.parse(newDate.toString()),
            null,
            null,
            NoteDetailsCallback(
                callbackSuccessful = { updateNote(it) }
            )
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        publisher.subscribe(this)
    }

    override fun onDetach() {
        publisher.unSubscribe(this)
        super.onDetach()
    }

    private fun updateNoteInfo() {
        binding.noteTitle.text = note.title
        binding.noteDescription.text = note.description
        repository.getCategory(
            note.categoryId,
            NoteDetailsCallback<NoteCategory>(
                callbackSuccessful = { result ->
                    binding.noteCategory.text = result.categoryName
                    noteCategories.find { it == result }
                        ?.let { note = note.copy(categoryId = it.categoryId) }
                }
            )
        )
        binding.noteDate.text = note.date.toLocalDate().toString()
        binding.noteTime.text = note.date.toLocalTime().toString()
        binding.noteDescriptionCard.setCardBackgroundColor(note.color.colorId)
        binding.root.setBackgroundColor(note.color.colorId)
    }

    override fun updateNote(note: Note) {
        this.note = note
        updateNoteInfo()
    }

    companion object {
        private const val NOTE_KEY = "note_key"
        const val TAG = "NoteDetailsFragment"
        fun newInstance(note: Note?): NoteDetailsFragment {
            val fragment = NoteDetailsFragment()
            val argsBundle = Bundle()
            argsBundle.putParcelable(NOTE_KEY, note)
            fragment.arguments = argsBundle
            return fragment
        }
    }
}

class NoteDetailsCallback<R>(
    private val callbackSuccessful: (data: R) -> Unit,
    private val callbackError: (() -> Unit)? = null
) : Callback<R> {
    override fun onSuccess(result: R) {
        callbackSuccessful(result)
    }

    override fun onError() {
        callbackError?.invoke()
    }
}
