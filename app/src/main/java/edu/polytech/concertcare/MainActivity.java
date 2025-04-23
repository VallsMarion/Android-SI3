package edu.polytech.concertcare;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.FirebaseApp;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 101;
    private View[] loadingBars;
    private Random random = new Random();
    private boolean isRandomMode = true;

    private final static String TAG = "concertcare";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(getApplicationContext());

        loadingBars = new View[]{
                findViewById(R.id.loadingBar1),
                findViewById(R.id.loadingBar2),
                findViewById(R.id.loadingBar3),
                findViewById(R.id.loadingBar4),
                findViewById(R.id.loadingBar5)
                        };

        startLoadingAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, ControlActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);

        //check permissions to receive notifications
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                || Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    REQUEST_NOTIFICATION_PERMISSION);
        }

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener( task -> {
            if(!task.isSuccessful()){
                Log.d(TAG,"no token received ");
            }
            else {
                Log.d(TAG,"token = "+task.getResult());
            }
        });

    }
        private void startLoadingAnimation() {
            for (View bar : loadingBars) {
                // Animation pour la première barre : mouvement lent et large
                animateLoadingBar(bar,
                        50f,   // hauteur de départ
                        getRandomIntBetween(80, 150),  // hauteur max
                        getRandomIntBetween(300,800),   // durée plus longue
                        choice(List.of(new LinearInterpolator(),
                                new AccelerateDecelerateInterpolator(),
                                new BounceInterpolator()))

                );
            }
        }

    private void animateLoadingBar(View bar, float startHeight, float endHeight, int duration, Interpolator interpolator) {
        ValueAnimator animator = ValueAnimator.ofFloat(startHeight, endHeight);
        animator.setDuration(duration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(interpolator);

        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            ViewGroup.LayoutParams params = bar.getLayoutParams();
            params.height = (int) value;
            bar.setLayoutParams(params);
        });

        animator.start();
    }

    public static <T> T choice(List<T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static int getRandomIntBetween(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }



}
