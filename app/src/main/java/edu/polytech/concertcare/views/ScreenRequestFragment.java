package edu.polytech.concertcare.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import edu.polytech.concertcare.R;

public class ScreenRequestFragment extends Fragment {

    private Button foundItemButton;
    private Button lostItemButton;
    private Button needAssistanceButton;

    public ScreenRequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_screen_request, container, false);

        foundItemButton = view.findViewById(R.id.foundItemButton);
        lostItemButton = view.findViewById(R.id.lostItemButton);
        needAssistanceButton = view.findViewById(R.id.needAssistanceButton);

        foundItemButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_main, new FoundItemFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        lostItemButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_main, new LostItemFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });


        needAssistanceButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_main, new AssistanceRequestFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
