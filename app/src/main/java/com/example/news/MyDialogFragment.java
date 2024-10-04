package com.example.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MyDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_dialog, container, false);

        TextView title = view.findViewById(R.id.dialog_title);
        TextView message = view.findViewById(R.id.dialog_message);
        Button positiveButton = view.findViewById(R.id.dialog_positive_button);
        Button negativeButton = view.findViewById(R.id.dialog_negative_button);

        positiveButton.setOnClickListener(v -> {
            Fragment firstFragment = new FirstFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container_view, firstFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            dismiss();
        });

        negativeButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "2nd Menu ", Toast.LENGTH_SHORT).show();
            dismiss();

        });

        return view;
    }
}