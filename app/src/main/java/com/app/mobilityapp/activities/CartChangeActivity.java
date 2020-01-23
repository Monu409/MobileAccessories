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
import com.app.mobilityapp.modals.ProductPriceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.ADD_INTO_CART;
import static com.app.mobilityapp.app_utils.AppApis.DELETE_CART;
import static com.app.mobilityapp.app_utils.AppApis.PLACE_ORDER;

public class CartChangeActivity extends BaseActivity {
    private RecyclerView cartList;
    private JSONArray jsonForOrder;
    private TextView checkoutTxt,priceTxt,continueShopTxtBotm;
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
        priceTxt = findViewById(R.id.total_txt);
        priceTxt = findViewById(R.id.total_txt);
        emptyCartView = findViewById(R.id.empty_cart_view);
        fullCartView = findViewById(R.id.cart_full_view);
        continueShopTxt = findViewById(R.id.continue_shop_txt);
        continueShopTxtBotm = findViewById(R.id.continue_shop_txt_botm);
        cartList.setLayoutManager(new LinearLayoutManager(this));
        checkoutTxt.setOnClickListener(v->{
//            orderPlaced(jsonForOrder);
            startActivity(new Intent(this,CheckoutActivity.class));
        });
        continueShopTxt.setOnClickListener(v->startActivity(new Intent(this,DashboardActivity.class)));
        continueShopTxtBotm.setOnClickListener(v->startActivity(new Intent(this,DashboardActivity.class)));
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
                List<CartChangeModel> cartChangeModels = new ArrayList<>();
                int totalCartPrice = 0;
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        int totalBrndQty = 0;
                        JSONObject childObj = jsonArray.getJSONObject(i);
                        JSONObject brandObj = childObj.getJSONObject("brandid");
                        JSONObject productObj = childObj.getJSONObject("productid");
                        JSONObject categoryId = childObj.getJSONObject("categoryId");

                        JSONArray modalArr = childObj.getJSONArray("modallist");
                        for (int j = 0; j < modalArr.length(); j++) {
                            JSONObject modalObj = modalArr.getJSONObject(j);
                            String qty = modalObj.getString("quantity");
                            int qtyInt = Integer.parseInt(qty);
                            totalBrndQty = totalBrndQty + qtyInt;
                        }
                        JSONArray priceArr = productObj.getJSONArray("price");
                        List<ProductPriceModel> productPriceModels = new ArrayList<>();
                        for (int k = 0; k < priceArr.length(); k++) {
                            try {
                                JSONObject jsonObjectprice = priceArr.getJSONObject(k);
                                String from = jsonObjectprice.getString("from");
                                String to = jsonObjectprice.getString("to");
                                String amt = jsonObjectprice.getString("amount");
                                ProductPriceModel productPriceModel = new ProductPriceModel(from, to, amt);
                                productPriceModel.setFrom(from);
                                productPriceModel.setTo(to);
                                productPriceModel.setAmount(amt);
                                productPriceModels.add(productPriceModel);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        String brandName = brandObj.getString("name");
                        String catName = categoryId.getString("name");
                        String brandImgUrl = brandObj.getString("imgUrl");
                        String brandPrice = childObj.getString("price");
                        int brandPriceInt = Integer.parseInt(brandPrice);
                        totalCartPrice = totalCartPrice+brandPriceInt;
                        String brandId = brandObj.getString("_id");
                        String productId = productObj.getString("_id");
                        String cartId = childObj.getString("_id");
                        String qty = String.valueOf(totalBrndQty);
                        CartChangeModel cartChangeModel = new CartChangeModel();
                        cartChangeModel.setBrandName(brandName);
                        cartChangeModel.setCatName(catName);
                        cartChangeModel.setImgUrl(brandImgUrl);
                        cartChangeModel.setQuantity(qty);
                        cartChangeModel.setTotalPrice(brandPrice);
                        cartChangeModel.setBrandId(brandId);
                        cartChangeModel.setProductId(productId);
                        cartChangeModel.setJsonArray(modalArr.toString());
                        cartChangeModel.setCartId(cartId);
                        cartChangeModel.setProductPriceModels(productPriceModels);
                        cartIdArray.put(i,cartId);
                        cartChangeModels.add(cartChangeModel);
                    }
                    CartChangeAdapter cartChangeAdapter = new CartChangeAdapter(cartChangeModels, CartChangeActivity.this);
                    cartList.setAdapter(cartChangeAdapter);
                    priceTxt.setText("₹ "+totalCartPrice);
                    String cartSizeStr = String.valueOf(cartChangeModels.size());
                    ConstantMethods.setStringPreference("cart_size",cartSizeStr,CartChangeActivity.this);
                    if(cartChangeModels.size()!=0){
                        fullCartView.setVisibility(View.VISIBLE);
                        emptyCartView.setVisibility(View.GONE);
                    }
                    else{
                        fullCartView.setVisibility(View.GONE);
                        emptyCartView.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(CartChangeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }
}
