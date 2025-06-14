package edu.polytech.concertcare.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.polytech.concertcare.R;
import edu.polytech.concertcare.interfaces.Clickable;
import edu.polytech.concertcare.adapters.ConcertAdapter;
import edu.polytech.concertcare.models.Concert;
import edu.polytech.concertcare.viewmodels.ConcertList;
import edu.polytech.concertcare.viewmodels.ConcertViewModel;
import edu.polytech.concertcare.interfaces.Notifiable;

public class HomeFragment extends Fragment implements Clickable {
    private ConcertAdapter concertAdapter;
    private RecyclerView listView;
    private List<Concert> listConcerts = new ArrayList<>();
    private Notifiable notifiable;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (requireActivity() instanceof Notifiable) {
            notifiable = (Notifiable) requireActivity();
        } else {
            throw new AssertionError("Classe " + requireActivity().getClass().getName() + " ne met pas en œuvre Notifiable.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        ConcertViewModel viewModel = new ViewModelProvider(requireActivity()).get(ConcertViewModel.class);

        concertAdapter = new ConcertAdapter(listConcerts, notifiable);
        listView.setAdapter(concertAdapter);

        viewModel.getConcerts().observe(getViewLifecycleOwner(), concerts -> {
            if (concerts != null) {
                List<Concert> concertList = ConcertList.getConcerts();
                listConcerts.clear();
                listConcerts.addAll(concertList);
            }
        });

        ImageView image = view.findViewById(R.id.music_homepage);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.musique);
        image.startAnimation(animation);

        return view;
    }


    @Override
    public void onClicItem(int itemIndex) {
        Toast.makeText(getContext(), "Clicked: " + itemIndex, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}