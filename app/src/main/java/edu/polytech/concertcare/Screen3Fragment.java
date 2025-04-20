package edu.polytech.concertcare;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Screen3Fragment extends Fragment {

    private ImageButton addRequestButton;

    public Screen3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_screen3, container, false);

        addRequestButton = view.findViewById(R.id.addRequestButton);

        addRequestButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new ScreenRequestFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
