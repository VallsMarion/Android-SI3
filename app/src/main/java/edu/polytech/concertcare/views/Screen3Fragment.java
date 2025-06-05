package edu.polytech.concertcare.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.polytech.concertcare.R;
import edu.polytech.concertcare.adapters.RequestAdapter;
import edu.polytech.concertcare.interfaces.Clickable;
import edu.polytech.concertcare.models.Request;
import edu.polytech.concertcare.viewmodels.RequestViewModel;

public class Screen3Fragment extends Fragment implements Clickable {

    private Button addRequestButton;
    private RecyclerView recyclerView;
    private RequestAdapter requestAdapter;
    private List<Request> requestList;

    public Screen3Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen3, container, false);

        ListView listView = view.findViewById(R.id.requestListView);

        RequestViewModel requestViewModel = new ViewModelProvider(requireActivity()).get(RequestViewModel.class);
        requestViewModel.getRequests().observe(getViewLifecycleOwner(), requestList -> {
            if (requestList != null) {
                RequestAdapter adapter = new RequestAdapter(requireContext(), requestList.getRequests());
                listView.setAdapter(adapter);
            }
        });


        addRequestButton = view.findViewById(R.id.addRequestButton);
        addRequestButton.setOnClickListener(v -> {

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_main, new ScreenRequestFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        TextView noRequestText = view.findViewById(R.id.noRequestText);
        requestViewModel.getRequests().observe(getViewLifecycleOwner(), requestList -> {
            if (requestList != null && !requestList.getRequests().isEmpty()) {
                RequestAdapter adapter = new RequestAdapter(requireContext(), requestList.getRequests());
                listView.setAdapter(adapter);
                noRequestText.setVisibility(View.GONE);
            } else {
                listView.setAdapter(null);
                noRequestText.setVisibility(View.VISIBLE);
            }
        });


        return view;
    }

    @Override
    public void onClicItem(int itemIndex) {
        Toast.makeText(getContext(), "Clicked: " + itemIndex, Toast.LENGTH_SHORT).show();
    }
}
