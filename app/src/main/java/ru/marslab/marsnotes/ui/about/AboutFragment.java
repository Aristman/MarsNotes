package ru.marslab.marsnotes.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.ui.FragmentRouterHolder;

public class AboutFragment extends Fragment {
    public static final String TAG = "AboutFragment";

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button backButton = view.findViewById(R.id.about_back_btn);
        backButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());
    }
}
