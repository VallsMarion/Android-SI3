package edu.polytech.concertcare;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    private View[] loadingBars;
    private Random random = new Random();
    private boolean isRandomMode = true;

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
