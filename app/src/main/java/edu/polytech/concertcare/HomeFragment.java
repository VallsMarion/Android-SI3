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
import edu.polytech.concertcare.concerts.HttpAsyncGet;
import edu.polytech.concertcare.concerts.PostExecuteActivity;

public class HomeFragment extends Fragment implements Clickable, PostExecuteActivity<Concert> {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.listView);
        concertAdapter = new ConcertAdapter(concertList, getContext(),this);
        listView.setAdapter(concertAdapter);
        String url = "https://raw.githubusercontent.com/JanaSaad0/ConcertCareData/main/concert.json";
        //todo: try to change context from MainActivity.this in getApplicationContext()
        new HttpAsyncGet<>(url, Concert.class, this, null );
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

    @Override
    public void onPostExecute(List<Concert> itemList) {
        concertList.clear();
        concertList.addAll(itemList);

        concertAdapter = new ConcertAdapter(concertList, requireContext(), this);
        listView.setAdapter(concertAdapter);
        concertAdapter.notifyDataSetChanged(); // this updates the list on screen
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
        requireActivity().runOnUiThread(runnable);
    }

}