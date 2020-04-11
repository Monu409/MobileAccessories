package com.app.mobilityapp.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.ProductCopyBrandsAdapter;
import com.app.mobilityapp.adapter.ProductPriceCopyAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.LocalQuantityModel;
import com.app.mobilityapp.modals.OrderDetailModel;
import com.app.mobilityapp.modals.ProductCopyModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.ADD_INTO_CART;
import static com.app.mobilityapp.app_utils.AppApis.GET_ALL_PROD_BRAND;

public class ProductACopyCopy extends BaseActivity {
    private RecyclerView priceList,brandNameList;
    private String productId;
    private Button addToCartBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        priceList = findViewById(R.id.rec_price);
        brandNameList = findViewById(R.id.prod_brand);
        priceList.setLayoutManager(new GridLayoutManager(this,3));
        brandNameList.setLayoutManager(new LinearLayoutManager(this));
        addToCartBtn = findViewById(R.id.add_to_cart);
        productId = getIntent().getStringExtra("brand_id");
        getData(productId);

        addToCartBtn.setOnClickListener(v->{
            List<LocalQuantityModel> quantityModels =  ConstantMethods.getQtyArrayListShared(ProductACopyCopy.this,"local_qty_models");
            JSONObject jsonObject = getBrandDetailArr(quantityModels);
            addDataIntoCart(jsonObject,ADD_INTO_CART);
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_product;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData(productId);
    }

    private void getData(String productId){
        String url = GET_ALL_PROD_BRAND + "/" + productId;
        CommonNetwork.getNetworkJsonObj(url, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("response",""+response);
                Gson gson = new Gson();
                ProductCopyModel productCopyModel = gson.fromJson(String.valueOf(response),ProductCopyModel.class);
                List<ProductCopyModel.Price> prices = productCopyModel.getData().getPrice();
                ProductPriceCopyAdapter productPriceCopyAdapter = new ProductPriceCopyAdapter(ProductACopyCopy.this,prices);
                priceList.setAdapter(productPriceCopyAdapter);


                List<LocalQuantityModel> quantityModels =  ConstantMethods.getQtyArrayListShared(ProductACopyCopy.this,"local_qty_models");
                getBrandDetailArr(quantityModels);
                List<ProductCopyModel.BrandDetail> brandDetails = productCopyModel.getData().getBrandDetails();
                ProductCopyBrandsAdapter productCopyBrandsAdapter = new ProductCopyBrandsAdapter(ProductACopyCopy.this,brandDetails,prices,quantityModels);
                brandNameList.setAdapter(productCopyBrandsAdapter);
                productCopyBrandsAdapter.getProductId(productId);
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("response",""+anError);
            }
        },this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        List<LocalQuantityModel> quantityModels =  ConstantMethods.getQtyArrayListShared(ProductACopyCopy.this,"local_qty_models");
        quantityModels.clear();
        ConstantMethods.saveQtyListShared(quantityModels,this,"local_qty_models");
    }

    private JSONObject getBrandDetailArr(List<LocalQuantityModel> quantityModels){
        int priceSum = 0;
        JSONArray jsonArray = new JSONArray();
        JSONObject cartJson = new JSONObject();
        Gson gson = new Gson();
        String strJson = gson.toJson(quantityModels);
        Log.e("json",strJson);
        try {
            JSONArray getJsonArr = new JSONArray(strJson);
            for(int i=0;i<getJsonArr.length();i++){
                JSONObject brandDetails = new JSONObject();
                JSONObject childObj = getJsonArr.getJSONObject(i);
                JSONArray modallist = childObj.getJSONArray("modallist");
                String brandid = childObj.getString("brandid");
                String price = childObj.getString("price");
                int priceInt = Integer.parseInt(price);
                priceSum = priceSum+priceInt;
                brandDetails.put("brand",brandid);
                brandDetails.put("modallist",modallist);
                jsonArray.put(i,brandDetails);
            }
            String productId = getJsonArr.getJSONObject(0).getString("productid");
            cartJson.put("productid",productId);
            cartJson.put("brandDetails",jsonArray);
            cartJson.put("price",priceSum);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cartJson;
    }

    private void addDataIntoCart(JSONObject jsonObject, String apiUrl) {
        ConstantMethods.showProgressbar(this);
        CommonNetwork.postNetworkJsonObj(apiUrl, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    String confirmation = response.getString("confirmation");
//                    if (confirmation.equals("success")) {
//                        Toast.makeText(ProductACopy.this, "Added into cart", Toast.LENGTH_SHORT).show();
//                        onBackPressed();
//                    }
                    showDialog(ProductACopyCopy.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(ProductACopyCopy.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }, this);

    }

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_custom_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setLayout(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT);

        FrameLayout mDialogNo = dialog.findViewById(R.id.frmNo);
        TextView msgTxt = dialog.findViewById(R.id.msg_txt);
        TextView leftTxt = dialog.findViewById(R.id.txt_left);
        TextView rightTxt = dialog.findViewById(R.id.txt_right);
        msgTxt.setText("Successfully added into cart");
        leftTxt.setText("Go To Bag");
        rightTxt.setText("Continue Shopping");
        rightTxt.setOnClickListener(v -> {
            startActivity(new Intent(ProductACopyCopy.this,DashboardActivity.class));
            List<LocalQuantityModel> quantityModels =  ConstantMethods.getQtyArrayListShared(ProductACopyCopy.this,"local_qty_models");
            quantityModels.clear();
            ConstantMethods.saveQtyListShared(quantityModels,this,"local_qty_models");
            dialog.dismiss();
        });

        leftTxt.setOnClickListener(v -> {
            startActivity(new Intent(ProductACopyCopy.this,CartChangeActivity.class));
            List<LocalQuantityModel> quantityModels =  ConstantMethods.getQtyArrayListShared(ProductACopyCopy.this,"local_qty_models");
            quantityModels.clear();
            ConstantMethods.saveQtyListShared(quantityModels,this,"local_qty_models");
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_chat:
                startActivity(new Intent(this,ChatActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
