package com.example.news;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HeadlineFragment  extends Fragment {

    private EditText etHeadline, etContent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        etHeadline = view.findViewById(R.id.etHeadline);
        etContent = view.findViewById(R.id.etContent);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String headline = etHeadline.getText().toString();
                String content = etContent.getText().toString();

                if (!headline.isEmpty() && !content.isEmpty()) {
                    ((MainActivity) getActivity()).addHeadline(headline, content);
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });

        return view;
    }
}
