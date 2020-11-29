package com.example.eco_firendly_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import com.example.eco_firendly_store.auth.SigninActivity;

import java.lang.ref.WeakReference;

public class SplashActivity extends AppCompatActivity {
    private SplashDelayHandler splashDelayHandler;
    private Boolean isRunning;
    private long splashRemainingTime;
    private long currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        isRunning=true;
        currentTime = System.currentTimeMillis();
        splashDelayHandler = new SplashDelayHandler(new WeakReference<SplashActivity>(this));
        splashDelayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message handlerMessage = splashDelayHandler.obtainMessage();
                splashDelayHandler.handleMessage(handlerMessage);
            }
        }, 2000);



    }

    public void moveToNextActivity() {
        startActivity(new Intent(this, SigninActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        splashRemainingTime = System.currentTimeMillis() - currentTime;
        outState.putLong("remaining_delay", splashRemainingTime);
    }

    static class SplashDelayHandler extends Handler {
        private final WeakReference<SplashActivity> splashActivityWeakReference;

        SplashDelayHandler(WeakReference<SplashActivity> splashActivityWeakReference) {
            super();
            this.splashActivityWeakReference = splashActivityWeakReference;
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            SplashActivity splashActivity = splashActivityWeakReference.get();
            if (splashActivity != null && splashActivity.isRunning)
                splashActivity.moveToNextActivity();
        }
    }
}