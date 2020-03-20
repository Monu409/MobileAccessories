package com.app.mobilityapp.activities;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.OrderDetailBrandAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.modals.OrderDetailModel;
import com.app.mobilityapp.modals.OrderRcvdModel;

import java.util.List;

public class OrderDetailModelActivity extends BaseActivity {
    private RecyclerView modelList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Model Detail");
        modelList = findViewById(R.id.model_list);
        modelList.setLayoutManager(new LinearLayoutManager(this));
        String prAc = getIntent().getStringExtra("pr_ac");
        if(prAc.equals("buyer")) {
            List<OrderDetailModel.Modallist> modallists = (List<OrderDetailModel.Modallist>) getIntent().getSerializableExtra("model_list");
            OrderDetailBrandAdapter orderDetailBrandAdapter = new OrderDetailBrandAdapter(modallists, this);
            modelList.setAdapter(orderDetailBrandAdapter);
        }
        else {
            List<OrderRcvdModel.Modallist> modallists = (List<OrderRcvdModel.Modallist>) getIntent().getSerializableExtra("model_list");
            OrderDetailBrandAdapter orderDetailBrandAdapter = new OrderDetailBrandAdapter(modallists, this,"sss");
            modelList.setAdapter(orderDetailBrandAdapter);
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_order_detail_model;
    }
}
