package ru.marslab.marsnotes.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.marslab.marsnotes.R;

public class SettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.settings_save_btn).setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Save settings", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
        });
        view.findViewById(R.id.settings_cancel_btn).setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Cancel settings", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
        });
    }
}
