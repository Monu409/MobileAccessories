package com.app.mobilityapp.activities;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.ShowMyProdBrndAdapter;
import com.app.mobilityapp.adapter.ShowMyProdPriceAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.modals.MyProductModel;

import java.util.ArrayList;
import java.util.List;

public class ShowMyProductActivity extends BaseActivity {
    private RecyclerView priceListRcyl,prodBrndList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"My Product Detail");
        priceListRcyl = findViewById(R.id.rec_price);
        prodBrndList = findViewById(R.id.prod_brand);
        priceListRcyl.setLayoutManager(new GridLayoutManager(this,3));
        prodBrndList.setLayoutManager(new LinearLayoutManager(this));
        List<MyProductModel.Price> priceList = (ArrayList)getIntent().getStringArrayListExtra("price_list");
        ShowMyProdPriceAdapter showMyProdPriceAdapter = new ShowMyProdPriceAdapter(priceList,this);
        priceListRcyl.setAdapter(showMyProdPriceAdapter);

        List<MyProductModel.BrandDetail> brandDetails = (ArrayList)getIntent().getStringArrayListExtra("model_list");
        ShowMyProdBrndAdapter showMyProdBrndAdapter = new ShowMyProdBrndAdapter(brandDetails,this);
        prodBrndList.setAdapter(showMyProdBrndAdapter);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_show_my_product;
    }
}
