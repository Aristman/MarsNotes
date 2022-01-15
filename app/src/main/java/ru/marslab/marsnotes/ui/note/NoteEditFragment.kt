package ru.marslab.marsnotes.ui.note

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import com.google.android.material.chip.Chip
import ru.marslab.marslablib.FragmentBinding
import ru.marslab.marsnotes.App
import ru.marslab.marsnotes.databinding.FragmentNoteEditBinding
import ru.marslab.marsnotes.domain.Repository
import ru.marslab.marsnotes.domain.model.Note
import ru.marslab.marsnotes.domain.model.NoteColor

class NoteEditFragment :
    FragmentBinding<FragmentNoteEditBinding>(FragmentNoteEditBinding::inflate) {
    private val repository: Repository by lazy { App.repository }
    private var isNewNoteMode = false
    private var note: Note = Note()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListeners()
    }

    private fun setListeners() {
        binding.noteEditSaveButton.setOnClickListener {
            if (isNewNoteMode) {
                repository.modifyNote(
                    note,
                    binding.noteEditTitle.text.toString(),
                    binding.noteEditDescription.text.toString(),
                    note.date,
                    null,
                    note.color,
                    NoteDetailsCallback(
                        callbackSuccessful = { closeFragment() }
                    )
                )
            } else {
                repository.addNote(
                    Note(
                        description = binding.noteEditDescription.text.toString(),
                        title = binding.noteEditTitle.text.toString(),
                        color = binding.noteEditColorsGroup.getChildAt(binding.noteEditColorsGroup.checkedChipId).tag as NoteColor
                    ),
                    NoteDetailsCallback(
                        callbackSuccessful = { parentFragmentManager.popBackStack() }
                    )
                )
            }
        }
        binding.noteEditCancelButton.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    private fun closeFragment() {
        parentFragmentManager.popBackStack()
    }

    private fun initView() {
        isNewNoteMode = arguments == null
        note = arguments?.getParcelable(ARG_NOTE) ?: Note()
        binding.noteEditTitle.setText(note.title)
        NoteColor.values().forEach { color ->
            val chip = Chip(requireContext())
            chip.chipBackgroundColor = ColorStateList.valueOf(color.colorId)
            chip.text = color.colorName
            chip.tag = color
            chip.setOnClickListener { v: View ->
                val chipColor = v.tag as NoteColor
                requireView().setBackgroundColor(chipColor.colorId)
//                note = note.copy(color = chipColor)
            }
            binding.noteEditColorsGroup.addView(chip)
        }
        binding.noteEditDescription.setText(note.description)
        requireView().setBackgroundColor(note.color.colorId)
    }

    companion object {
        const val TAG = "NoteEditFragment"
        private const val ARG_NOTE = "ARG_NOTE"
        fun newInstance(note: Note?): NoteEditFragment {
            val bundle = Bundle()
            val noteEditFragment = NoteEditFragment()
            bundle.putParcelable(ARG_NOTE, note)
            noteEditFragment.arguments = bundle
            return noteEditFragment
        }
    }
}
