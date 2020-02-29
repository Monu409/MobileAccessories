package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.MyProductAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.CartNewModel;
import com.app.mobilityapp.modals.MyOrderModel;
import com.app.mobilityapp.modals.MyProductModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.SELLER_PRODUCTS;

public class MyProductActivity extends BaseActivity {
    private RecyclerView mProductList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductList = findViewById(R.id.my_product_list);
        mProductList.setLayoutManager(new LinearLayoutManager(this));
        ConstantMethods.setTitleAndBack(this,"My Products");
        getMyProductDetails();
        ConstantMethods.showProgressbar(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_product;
    }

    private void getMyProductDetails(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("sort","1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(SELLER_PRODUCTS, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("response",""+response);
                ConstantMethods.dismissProgressBar();
                Gson gson = new Gson();
                MyProductModel myProductModel = gson.fromJson(String.valueOf(response),MyProductModel.class);
                String confirmation = myProductModel.getConfirmation();
                if(confirmation.equals("success")) {
                    List<MyProductModel.MyProductChild> myOrderChildModel = myProductModel.getData();
                    MyProductAdapter myProductAdapter = new MyProductAdapter(myOrderChildModel,MyProductActivity.this);
                    mProductList.setAdapter(myProductAdapter);
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("response",""+anError);
                ConstantMethods.dismissProgressBar();
                Toast.makeText(MyProductActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }
}
