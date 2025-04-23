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

public class HomeFragment extends Fragment implements Clickable {
    private List<Concert> concertList = new ArrayList<>();
    private ConcertAdapter concertAdapter;
    private ListView listView;
    private final static int NUM_FRAGMENT = 1;
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

    private void loadConcertsFromFirebase() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://concertcare-16a66-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference dbRef = db.getReference("concerts");

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                concertList.clear();
                for (DataSnapshot concertSnap : snapshot.getChildren()) {
                    Concert concert = concertSnap.getValue(Concert.class);
                    concertList.add(concert);
                }
                concertAdapter.notifyDataSetChanged();
                Log.d("Firebase", "Concerts loaded: " + concertList.size());
                Toast.makeText(getContext(), "Concerts: " + concertList.size(), Toast.LENGTH_SHORT).show();
                Log.d("Firebase", "Snapshot exists: " + snapshot.exists());
                Log.d("Firebase", "Children count: " + snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error loading concerts", error.toException());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.listView);
        concertAdapter = new ConcertAdapter(concertList, getContext(),this);
        listView.setAdapter(concertAdapter);

        loadConcertsFromFirebase();


        return view;
    }


    @Override
    public void onClicItem(int itemIndex) {

    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

}