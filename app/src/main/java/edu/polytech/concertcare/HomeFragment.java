package edu.polytech.concertcare;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.widget.ListView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.polytech.concertcare.concerts.Clickable;
import edu.polytech.concertcare.concerts.Concert;
import edu.polytech.concertcare.concerts.ConcertAdapter;
import edu.polytech.concertcare.concerts.ConcertList;
import edu.polytech.concertcare.concerts.HttpAsyncGet;
import edu.polytech.concertcare.concerts.PostExecuteActivity;

public class HomeFragment extends Fragment implements Clickable {
    private List<Concert> concertList;
    private ConcertAdapter concertAdapter;
    private ListView listView;
    private Notifiable notifiable;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        concertList = ConcertList.getConcerts();
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
        concertAdapter = new ConcertAdapter(concertList, notifiable);
        listView.setAdapter(concertAdapter);

        return view;
    }


    @Override
    public void onClicItem(int itemIndex) {
        Toast.makeText(getContext(), "Clicked: " + concertList.get(itemIndex).title, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}