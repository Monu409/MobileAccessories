package com.app.mobilityapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.CartChangeAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.CartChangeModel;
import com.app.mobilityapp.modals.CartNewModel;
import com.app.mobilityapp.modals.ConversationModel;
import com.app.mobilityapp.modals.ProductPriceModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.ADD_INTO_CART;
import static com.app.mobilityapp.app_utils.AppApis.DELETE_CART;
import static com.app.mobilityapp.app_utils.AppApis.PLACE_ORDER;

public class CartChangeActivity extends BaseActivity implements CartChangeAdapter.GetCartID {
    private RecyclerView cartList;
    private JSONArray jsonForOrder;
    private TextView checkoutTxt, priceTxt, continueShopTxtBotm;
    private LinearLayout emptyCartView;
    private RelativeLayout fullCartView;
    private JSONArray cartIdArray = new JSONArray();
    private TextView continueShopTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this, "Cart");
        cartList = findViewById(R.id.cart_list);
        checkoutTxt = findViewById(R.id.checkout_txt);
        emptyCartView = findViewById(R.id.empty_cart_view);
        fullCartView = findViewById(R.id.cart_full_view);
        continueShopTxtBotm = findViewById(R.id.continue_shop_txt);

        continueShopTxtBotm.setOnClickListener(v->{
            startActivity(new Intent(this,DashboardActivity.class));
        });

        cartList.setLayoutManager(new LinearLayoutManager(this));
        checkoutTxt.setOnClickListener(v -> {
            startActivity(new Intent(this, CheckoutActivity.class));
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_change_cart;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartDetail();

    }

    private void getCartDetail() {
        ConstantMethods.showProgressbar(this);
        CommonNetwork.getNetworkJsonObj(ADD_INTO_CART, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                Gson gson = new Gson();
                CartNewModel cartNewModel = gson.fromJson(String.valueOf(response), CartNewModel.class);
                String confirmation = cartNewModel.getConfirmation();
                if (confirmation.equals("success")) {
                    List<CartNewModel.CartChildModel> cartChildModels = cartNewModel.getData();
                    CartChangeAdapter cartChangeAdapter = new CartChangeAdapter(cartChildModels, CartChangeActivity.this, CartChangeActivity.this);
                    cartList.setAdapter(cartChangeAdapter);
                    if (cartChildModels.size() != 0) {
                        fullCartView.setVisibility(View.VISIBLE);
                        emptyCartView.setVisibility(View.GONE);
                    } else {
                        fullCartView.setVisibility(View.GONE);
                        emptyCartView.setVisibility(View.VISIBLE);
                    }
                    int priceSum = 0;
                    for (int i = 0; i < cartChildModels.size(); i++) {
                        int price = cartChildModels.get(i).getPrice();
                        priceSum = priceSum + price;
                    }
//                    priceTxt.setText("₹ "+priceSum);
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(CartChangeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }, this);
    }

    @Override
    public void getId(String cartId) {
        alertDialogForLogout(cartId);
    }

    //{"_id":["5e5c92d96f6a170ed18e7411"]}
    private void deleteCart(String catdId) {
        JSONObject cartIdObj = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(catdId);
        try {
            cartIdObj.put("_id", jsonArray);
            CommonNetwork.deleteNetworkJsonObj(DELETE_CART + cartIdObj, new JSONResult() {
                @Override
                public void notifySuccess(@NonNull JSONObject response) {
                    Log.e("response", "" + response);
                    String confirmation = null;
                    try {
                        confirmation = response.getString("confirmation");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (confirmation.equals("success")) {
                        Toast.makeText(CartChangeActivity.this, "Cart item deleted", Toast.LENGTH_SHORT).show();
                        getCartDetail();
                    }
                }

                @Override
                public void notifyError(@NonNull ANError anError) {
                    Log.e("response", "" + anError);
                }
            }, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void alertDialogForLogout(String cartId){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        builder1.setTitle("Delete Cart");
        builder1.setMessage("Do you want to Delete this item");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                    deleteCart(cartId);
                });

        builder1.setNegativeButton(
                "No",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
