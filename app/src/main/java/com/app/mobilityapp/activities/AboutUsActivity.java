package com.app.mobilityapp.activities;

import android.os.Bundle;

import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;

public class AboutUsActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"About Us");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_about_us;
    }
}
