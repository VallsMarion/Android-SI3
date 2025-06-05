package edu.polytech.concertcare;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import edu.polytech.concertcare.models.Concert;
import edu.polytech.concertcare.views.ConcertItemFragment;
import edu.polytech.concertcare.viewmodels.ConcertViewModel;
import edu.polytech.concertcare.interfaces.Menuable;
import edu.polytech.concertcare.interfaces.Notifiable;
import edu.polytech.concertcare.utils.HttpAsyncGet;
import edu.polytech.concertcare.utils.PostExecuteActivity;
import edu.polytech.concertcare.views.FoundItemFragment;
import edu.polytech.concertcare.views.HomeFragment;
import edu.polytech.concertcare.views.MenuFragment;
import edu.polytech.concertcare.views.Screen3Fragment;
import edu.polytech.concertcare.views.Screen4Fragment;
import edu.polytech.concertcare.views.StaffMapFragment;


public class ControlActivity extends AppCompatActivity implements Menuable, Notifiable, PostExecuteActivity<Concert> {
    private int seekBarValue = 30;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        String url = "https://raw.githubusercontent.com/JanaSaad0/ConcertCareData/main/concert.json";
        new HttpAsyncGet<>(url, Concert.class, this, null );

        int menuNumber = 1;

        Intent intent = getIntent();
        if(intent!=null){
            menuNumber = intent.getIntExtra(getString(R.string.index),1);
        }

        Bundle args = new Bundle();
        args.putInt(getString(R.string.index), menuNumber);
        MenuFragment menu = new MenuFragment();
        menu.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_menu, menu);
        transaction.commit();
    }



    @Override
    public void onMenuChange(int index) {
        Fragment fragment = null;
        switch (index){
            case 0: fragment = new HomeFragment(); break;
            case 1: {
                fragment = new StaffMapFragment();
                Bundle args = new Bundle();
                args.putInt(getString(R.string.seekbarvalue), seekBarValue);
                fragment.setArguments(args);
            }  break;
            case 2: fragment = new Screen3Fragment(); break;
            case 3: fragment = new Screen4Fragment(); break;
            default: fragment = new HomeFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_main, fragment);
        //TODO: addToBackStack doesn't work in all cases
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onClick(int numFragment) {

    }

    @Override
    public void onDataChange(int numFragment, Object data) {
        if(numFragment == 23) {
            // Screen3Fragment
            Fragment fragment =new Screen3Fragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_main, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        if (numFragment == 2) { // concert click
            String id = (String) data;

            Fragment fragment = new ConcertItemFragment();
            Bundle args = new Bundle();
            args.putString("concert_id", id); // just send the index

            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_main, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        /*
        ** to show staff point details when you click on a concert
        else if (numFragment == 1000) {
            int concertIndex = (Integer) data;

            Bundle args = new Bundle();
            args.putInt("concert_index", concertIndex);

            Fragment fragment = new StaffMapFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_main, fragment)
                    .addToBackStack(null)
                    .commit();

            onMenuChange(1);
        }*/
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            FoundItemFragment fragment = (FoundItemFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_main);

            if (fragment != null) {
                fragment.receivePhoto(imageBitmap);
            }
        }
    }

    @Override
    public void onPostExecute(List<Concert> itemList) {
        ConcertViewModel vm = new ViewModelProvider(this).get(ConcertViewModel.class);
        vm.setConcerts(itemList);
        onMenuChange(0); // TODO ajouter des constantes pour les index pour mieux comprendre le switch
    }
}
