package edu.polytech.concertcare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class ControlActivity extends AppCompatActivity implements Menuable, Notifiable {
    private int seekBarValue = 30;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

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
        onMenuChange(0);
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
        switch(numFragment){
            case 2:  seekBarValue = (Integer)data; break;
        }
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

}
