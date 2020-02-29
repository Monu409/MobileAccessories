package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.SubOrderDetailAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.modals.OrderDetailModel;
import com.app.mobilityapp.modals.OrderRcvdModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class SubOrderActivity extends BaseActivity {
    private RecyclerView subOrdrList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Order Detail");
        subOrdrList= findViewById(R.id.sub_ordr_list);
        subOrdrList.setLayoutManager(new LinearLayoutManager(this));
        String preView = getIntent().getStringExtra("preview");
        if(preView.equals("seller_view")){
            List<OrderRcvdModel.OrderRcvdModelChild> orderRcvdModelChildren = (List<OrderRcvdModel.OrderRcvdModelChild>)getIntent().getSerializableExtra("child_model");
            SubOrderDetailAdapter subOrderDetailAdapter = new SubOrderDetailAdapter(orderRcvdModelChildren,this,"seller");
            subOrdrList.setAdapter(subOrderDetailAdapter);
        }
        else if(preView.equals("buyer_view")){
            List<OrderDetailModel.BrandDetail_> brandDetail = (List<OrderDetailModel.BrandDetail_>)getIntent().getSerializableExtra("data");
            SubOrderDetailAdapter subOrderDetailAdapter = new SubOrderDetailAdapter(brandDetail,this,"","");
            subOrdrList.setAdapter(subOrderDetailAdapter);
        }

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_sub_order;
    }
}
