package edu.polytech.concertcare;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

public class Screen4Fragment extends Fragment {

    private Spinner languageSpinner;
    private Spinner themeSpinner;
    private Button logoutButton;
    private Button deleteAccountButton;

    public Screen4Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_screen4, container, false);

        languageSpinner = view.findViewById(R.id.languageSpinner);
        themeSpinner = view.findViewById(R.id.themeSpinner);
        logoutButton = view.findViewById(R.id.logoutButton);
        deleteAccountButton = view.findViewById(R.id.deleteAccountButton);

        logoutButton.setOnClickListener(v -> {
            //TODO
        });

        deleteAccountButton.setOnClickListener(v -> {
            //TODO
        });

        return view;
    }
}
