package ru.marslab.marsnotes.ui.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import ru.marslab.marsnotes.R

class NoteDescriptionEditFragment : DialogFragment() {
    private var text: String = ""
    private var textView: TextInputEditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            text = it.getString(NOTE_TEXT_KEY, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_description_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        textView = view.findViewById(R.id.modify_description_text)
        textView?.setText(text)
        view.findViewById<View>(R.id.description_save_btn).setOnClickListener {
            textView?.text?.let {
                val bundle = Bundle()
                bundle.putString(NOTE_TEXT_KEY, it.toString())
                parentFragmentManager.setFragmentResult(NOTE_DESCRIPTION_EDIT_RESULT, bundle)
            }
            dismiss()
        }
        view.findViewById<View>(R.id.description_cancel_btn)
            .setOnClickListener { dismiss() }
    }

    companion object {
        const val TAG = "NoteEditFragment"
        const val NOTE_TEXT_KEY = "NOTE_ID_KEY"
        const val NOTE_DESCRIPTION_EDIT_RESULT = "NoteEditFragment"
        fun newInstance(text: String?): NoteDescriptionEditFragment {
            val noteEditFragment = NoteDescriptionEditFragment()
            val bundle = Bundle()
            bundle.putString(NOTE_TEXT_KEY, text)
            noteEditFragment.arguments = bundle
            return noteEditFragment
        }
    }
}
