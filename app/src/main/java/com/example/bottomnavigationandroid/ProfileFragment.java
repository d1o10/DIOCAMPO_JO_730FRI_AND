package com.example.bottomnavigationandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText editTextName, editTextEmail;
    private RadioGroup radioGroupGender;
    private CheckBox checkBoxRecycling, checkBoxVolunteering, checkBoxEvents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI components
        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        radioGroupGender = view.findViewById(R.id.radioGroupGender);
        checkBoxRecycling = view.findViewById(R.id.checkBoxRecycling);
        checkBoxVolunteering = view.findViewById(R.id.checkBoxVolunteering);
        checkBoxEvents = view.findViewById(R.id.checkBoxEvents);

        // Optionally load saved profile data here (e.g., from Firebase)

        return view;
    }
}
