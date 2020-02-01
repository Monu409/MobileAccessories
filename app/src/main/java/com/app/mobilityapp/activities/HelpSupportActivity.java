package com.app.mobilityapp.activities;

import android.os.Bundle;

import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;

public class HelpSupportActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Help & Support");
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_help_support;
    }
}
