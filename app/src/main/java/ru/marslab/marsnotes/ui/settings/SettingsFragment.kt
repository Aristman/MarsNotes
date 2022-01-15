package ru.marslab.marsnotes.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import ru.marslab.marslablib.FragmentBinding
import ru.marslab.marsnotes.databinding.FragmentSettingsBinding

class SettingsFragment :
    FragmentBinding<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            settingsSaveBtn.setOnClickListener { saveSettings() }
            settingsCancelBtn.setOnClickListener { cancelSettings() }
        }
    }

    private fun cancelSettings() {
        Toast.makeText(requireContext(), "Cancel settings", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }

    private fun saveSettings() {
        Toast.makeText(requireContext(), "Save settings", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack()
    }

    companion object {
        const val TAG = "SettingsFragment"
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}
