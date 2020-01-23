package com.app.mobilityapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.androidnetworking.error.ANError;
import com.app.mobilityapp.adapter.ProductBrandAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.CartModel;
import com.app.mobilityapp.modals.ProBrndModal;
import com.app.mobilityapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.app.mobilityapp.app_utils.AppApis.GET_ALL_BRAND;

public class ProductBrandActivity extends BaseActivity {
    private RecyclerView brandList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brandList = findViewById(R.id.brand_list);
        ConstantMethods.setTitleAndBack(this,"All Brands");
        brandList.setLayoutManager(new GridLayoutManager(this,2));
        ConstantMethods.showProgressbar(this);
        CommonNetwork.getNetworkJsonObj(GET_ALL_BRAND, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("response",""+response);
                ConstantMethods.dismissProgressBar();
                List<ProBrndModal> proBrndModals = new ArrayList<>();
                try {
                    String confirmation = response.getString("confirmation");
                    if(confirmation.equals("success")){
                        JSONArray jsonArray = response.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            String content = jsonObject.getString("content");
                            String imgUrl = jsonObject.getString("imgUrl");
                            String brandId = jsonObject.getString("_id");
                            ProBrndModal proBrndModal = new ProBrndModal();
                            proBrndModal.setName(name);
                            proBrndModal.setContent(content);
                            proBrndModal.setImgUrl(imgUrl);
                            proBrndModal.setId(brandId);
                            proBrndModals.add(proBrndModal);
                        }
                        ProductBrandAdapter productBrandAdapter = new ProductBrandAdapter(proBrndModals,ProductBrandActivity.this);
                        brandList.setAdapter(productBrandAdapter);
                        productBrandAdapter.onListClick(proBrndModal -> {
                            Log.e("position",""+proBrndModal);
//                            CartModel cartModel = (CartModel) getIntent().getSerializableExtra("cart_model_data");
//                            cartModel.setProBrndModal(proBrndModal);
                            Intent intent = new Intent(ProductBrandActivity.this, ProductModelActivity.class);
                            intent.putExtra("brand_id",proBrndModal.getId());
                            intent.putExtra("brand_name",proBrndModal.getName());
                            intent.putExtra("brand_img",proBrndModal.getImgUrl());
//                            intent.putExtra("cart_model_data",cartModel);
                            ProductBrandActivity.this.startActivity(intent);
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("response",""+anError);
                ConstantMethods.dismissProgressBar();
            }
        },this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_product_brand;
    }
}
