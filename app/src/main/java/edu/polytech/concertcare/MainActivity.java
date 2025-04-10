package edu.polytech.concertcare;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(getApplicationContext(), ControlActivity.class);

        ImageView image = findViewById(R.id.imageView);
        image.setBackgroundResource(R.drawable.rotation_animation);
        AnimationDrawable animation = (AnimationDrawable)image.getBackground();
        animation.start();

        findViewById(R.id.goDefault).setOnClickListener(clic -> {
            startActivity(intent);
        });

        findViewById(R.id.option1).setOnClickListener(clic -> {
            int menuNumber = 6;
            intent.putExtra(getString(R.string.index),menuNumber);
            startActivity(intent);
        });
    }
}