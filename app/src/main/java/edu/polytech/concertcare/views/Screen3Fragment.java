package edu.polytech.concertcare.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.polytech.concertcare.R;
import edu.polytech.concertcare.adapters.RequestAdapter;
import edu.polytech.concertcare.models.Request;

public class Screen3Fragment extends Fragment {

    private ImageButton addRequestButton;
    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    private List<Request> requestList;

    public Screen3Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen3, container, false);

        recyclerView = view.findViewById(R.id.requestList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Utilisation d'un LayoutManager

        requestList = new ArrayList<>();
        requestList.add(new Request("Demande 1", "Description de la demande 1"));
        requestList.add(new Request("Demande 2", "Description de la demande 2"));
        requestList.add(new Request("Demande 3", "Description de la demande 3"));

        requestAdapter = new RequestAdapter(requestList);
        recyclerView.setAdapter(requestAdapter);

        addRequestButton = view.findViewById(R.id.addRequestButton);
        addRequestButton.setOnClickListener(v -> {

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_main, new ScreenRequestFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }
}
