package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.modals.OrderDetailModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class SubOrderActivity extends BaseActivity {
    private CircleImageView productImg;
    private TextView nameTxt,qtyTxt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Order Detail");
        productImg = findViewById(R.id.brand_img);
        nameTxt = findViewById(R.id.cat_name_txt);
        qtyTxt= findViewById(R.id.qty_txt);
        OrderDetailModel.BrandDetail_ brandDetail = (OrderDetailModel.BrandDetail_)getIntent().getSerializableExtra("data");
        List<OrderDetailModel.Modallist> modallistList = brandDetail.getModallist();
        OrderDetailModel.Brand brand = brandDetail.getBrand();
        int sumQty = 0;
        for(int i=0;i<modallistList.size();i++){
            int qty = modallistList.get(i).getQuantity();
            sumQty = sumQty+qty;
        }
        String name = brand.getName();
        String urlImg = brand.getImgUrl();
        Glide
                .with(this)
                .load(urlImg)
                .placeholder(R.drawable.test)
                .centerCrop()
                .into(productImg);

        nameTxt.setText(name);
        qtyTxt.setText("Qty: "+sumQty);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_sub_order;
    }
}
