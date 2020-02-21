package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.EditCartAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.EditCartModel;
import com.google.gson.Gson;

import org.json.JSONObject;


import static com.app.mobilityapp.app_utils.AppApis.ADD_INTO_CART;

public class EditCartActivity extends BaseActivity {
    private RecyclerView cartChildList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String cartId = getIntent().getStringExtra("cart_id");
        cartChildList = findViewById(R.id.edit_cart_list);
        cartChildList.setLayoutManager(new LinearLayoutManager(this));
        getExploreCartData(cartId);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_edit_cart;
    }

    private void getExploreCartData(String cartId){
        ConstantMethods.showProgressbar(this);
        CommonNetwork.getNetworkJsonObj(ADD_INTO_CART + "/" + cartId, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                Gson gson = new Gson();
                EditCartModel editCartModel = gson.fromJson(String.valueOf(response),EditCartModel.class);
                String confirmation = editCartModel.getConfirmation();
                if(confirmation.equals("success")){
                    EditCartAdapter editCartAdapter = new EditCartAdapter(EditCartActivity.this,editCartModel);
                    cartChildList.setAdapter(editCartAdapter);

//                    OrderDetailModel.Data data = orderDetailModel.getData();
//                    String orderIdSeen = data.getOrderId();
//                    int totalAmount = data.getAmount();
//                    int discount = data.getDiscount();
//                    String date = data.getCreatedAt();
//                    String showDate = ConstantMethods.changeDateFormate(date);
//                    orderIdTxt.setText("OredrID: "+orderIdSeen);
//                    dateTxt.setText(showDate);
//                    amountTxt.setText("₹ "+totalAmount);
//                    discountTxt.setText("₹ "+discount);
//
//                    List<OrderDetailModel.Productdetail> productdetails = data.getProductdetails();
//                    OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(productdetails,OrderDetailActivity.this);
//                    itemList.setAdapter(orderDetailAdapter);
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(EditCartActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }
}
