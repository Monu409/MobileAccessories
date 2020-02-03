package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.widget.Button;

import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;

public class ThankuActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Thank You");
        Button okBtn = findViewById(R.id.ok_txt);
        okBtn.setOnClickListener(v->onBackPressed());
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_thanku;
    }
}
