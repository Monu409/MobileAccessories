package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.widget.ImageView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.bumptech.glide.Glide;

public class FullImageActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Media");
        String imgUrl = getIntent().getStringExtra("image_url");
        ImageView chatImg = findViewById(R.id.full_image);
        Glide
                .with(this)
                .load(imgUrl)
                //.centerCrop()
                .into(chatImg);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_full_image;
    }
}
