package com.app.mobilityapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.R;


public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        getActionBar().hide();
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                String loginStatus = ConstantMethods.getStringPreference("login_status", SplashActivity.this);
                if (loginStatus.equals("login")) {
                    Intent mainIntent = new Intent(SplashActivity.this,DashboardActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else {
                    Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
