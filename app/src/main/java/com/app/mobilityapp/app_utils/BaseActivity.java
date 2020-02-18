package com.app.mobilityapp.app_utils;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.app.mobilityapp.R;
import com.app.mobilityapp.modals.ProBrndModal;


public abstract class BaseActivity extends AppCompatActivity {
    public  static ProBrndModal slctd_brand;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_tool));
        setContentView(getLayoutResourceId()); 
    } 
    protected abstract int getLayoutResourceId();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
} 