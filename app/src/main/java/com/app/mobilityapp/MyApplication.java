package com.app.mobilityapp;

import android.app.Application;
import android.os.StrictMode;

import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {

    private static MyApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        FirebaseApp.initializeApp(this);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}