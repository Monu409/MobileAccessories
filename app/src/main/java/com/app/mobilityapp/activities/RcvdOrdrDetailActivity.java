package com.app.mobilityapp.activities;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.OrdrRcvdDtlAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.modals.OrderRcvdModel;

import java.util.List;

public class RcvdOrdrDetailActivity extends BaseActivity {
    private RecyclerView detailList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Order Details");
        detailList = findViewById(R.id.detail_list);
        detailList.setLayoutManager(new LinearLayoutManager(this));
        List<OrderRcvdModel.Productdetail> productdetails = (List<OrderRcvdModel.Productdetail>) getIntent().getSerializableExtra("product_details");
        OrdrRcvdDtlAdapter ordrRcvdDtlAdapter = new OrdrRcvdDtlAdapter(productdetails,this);
        detailList.setAdapter(ordrRcvdDtlAdapter);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_ordr_rcvd_detail;
    }
}
