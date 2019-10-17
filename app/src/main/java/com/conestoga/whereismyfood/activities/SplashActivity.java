package com.conestoga.whereismyfood.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.conestoga.whereismyfood.R;

/**
 * {@link SplashActivity} : this activity is launch activity, when app opens it will stay for {@link #SPLASH_TIME_OUT} ms.
 *
 * @author : Harsh Patel
 * @version : 0.0.1
 * @Date : 10/17/2019
 */
public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 3000;
    private Runnable mRunnable;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        displayScreen();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, SPLASH_TIME_OUT);
    }

    /**
     * This method is responsible for showing splash screen for a specific time.
     *
     * @author : Harsh Patel
     * @version : 0.0.1
     * @Date : 10/17/2019
     */
    private void displayScreen() {
        mRunnable = new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                // close this activity
                finish();
            }
        };
        mHandler = new Handler();
    }

}
