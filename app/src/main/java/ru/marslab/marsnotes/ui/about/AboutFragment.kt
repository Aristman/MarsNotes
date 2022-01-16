package ru.marslab.marsnotes.ui.about

import android.os.Bundle
import android.view.View
import ru.marslab.marslablib.FragmentBinding
import ru.marslab.marsnotes.databinding.FragmentAboutBinding

class AboutFragment : FragmentBinding<FragmentAboutBinding>(FragmentAboutBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAboutBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    companion object {
        const val TAG = "AboutFragment"
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }
}
