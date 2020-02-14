package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.ProductModelListAdapter1;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.CartChangeModel;
import com.app.mobilityapp.modals.ProModlModel;
import com.app.mobilityapp.modals.ProductPriceModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.app.mobilityapp.app_utils.AppApis.ADD_INTO_CART;
import static com.app.mobilityapp.app_utils.AppApis.UPDATE_CART;

public class EnterQuantityActivity extends BaseActivity {
    private RecyclerView qtyList;
    private Button btnOrder;
    private Button submit;
    private String productIdforJson, brandId;
    private ProductModelListAdapter1 productModelAdapter;
    private ArrayList<ProModlModel> proModlModels;
    private String idForUrl;
    private List<ProductPriceModel> productPriceModels;
    int mQty = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qtyList = findViewById(R.id.product_recyler);
        btnOrder = findViewById(R.id.btn_order);
        submit = findViewById(R.id.btn_submit);
        qtyList.setLayoutManager(new LinearLayoutManager(this));
        String previousViewName = getIntent().getStringExtra("view_name");
        productPriceModels = (ArrayList<ProductPriceModel>) getIntent().getSerializableExtra("price_list");
        if (previousViewName.equals("cart_unslctd")) {
            ConstantMethods.setTitleAndBack(this, "Edit Quantity");
            CartChangeModel cartChangeModel = (CartChangeModel) getIntent().getSerializableExtra("qty_arr");
            idForUrl = cartChangeModel.getCartId();
            proModlModels = new ArrayList<>();
            Log.e("data", "" + cartChangeModel);
            try {
                JSONArray jsonArray = new JSONArray(cartChangeModel.getJsonArray());
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONObject modalidObj = jsonObject.getJSONObject("modalid");
                        String model_name = modalidObj.getString("name");
                        String desc = modalidObj.getString("content");
                        String id = modalidObj.getString("_id");
                        String qty = jsonObject.getString("quantity");
                        int qtyInt = Integer.parseInt(qty);
                        mQty = mQty + qtyInt;
                        ProModlModel proModlModel = new ProModlModel();
                        proModlModel.setName(model_name);
                        proModlModel.setContent(desc);
                        proModlModel.setId(id);
                        proModlModel.setQty(qty);
                        proModlModels.add(proModlModel);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                btnOrder.setText("Order Qty" + "  " + mQty);
                productModelAdapter = new ProductModelListAdapter1(this, proModlModels);
                qtyList.setAdapter(productModelAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (previousViewName.equals("product")) {
            ConstantMethods.setTitleAndBack(this, "Enter Quantity");
            proModlModels = (ArrayList<ProModlModel>) getIntent().getSerializableExtra("qty_list");
            productModelAdapter = new ProductModelListAdapter1(this, proModlModels);
            qtyList.setAdapter(productModelAdapter);
            productIdforJson = getIntent().getStringExtra("brand_id");
            brandId = getIntent().getStringExtra("brandId");
        }

        submit.setOnClickListener(v -> {
            if(btnOrder.getText().toString().trim().equals("Order Qty  0")){
                Toast.makeText(this, "Select minimum 1 quantity", Toast.LENGTH_SHORT).show();
            }
            else {
                getCartDetail();
                int totalQty = 0;

                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                try {
                    for (int i = 0; i < proModlModels.size(); i++) {
                        ProModlModel proModlModel = proModlModels.get(i);
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("modalid", proModlModels.get(i).getId());
                        jsonObject1.put("quantity", proModlModels.get(i).getQty());
                        totalQty = totalQty + Integer.parseInt(proModlModel.getQty());
                        jsonArray.put(i, jsonObject1);
                    }

                    jsonObject.put("productid", productIdforJson);
                    jsonObject.put("brandid", brandId);
                    int applyPrice = applyPrice(totalQty);
                    jsonObject.put("price", String.valueOf(applyPrice));
                    jsonObject.put("modallist", jsonArray);
                    String jsonArrayPrif = jsonArray.toString();
                    ConstantMethods.setStringPreference("product_json",jsonArrayPrif,this);
                    if (previousViewName.equals("product")) {
                        addDataIntoCart(jsonObject, ADD_INTO_CART);
                    } else if (previousViewName.equals("cart_unslctd")) {
                        updateDataIntoCart(jsonObject, UPDATE_CART + idForUrl);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_enter_quantity;
    }

    private void addDataIntoCart(JSONObject jsonObject, String apiUrl) {
        ConstantMethods.showProgressbar(this);
        CommonNetwork.postNetworkJsonObj(apiUrl, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    String confirmation = response.getString("confirmation");
                    if (confirmation.equals("success")) {
                        Toast.makeText(EnterQuantityActivity.this, "Added into cart", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(EnterQuantityActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }, this);

    }

    private void updateDataIntoCart(JSONObject jsonObject, String apiUrl) {
        ConstantMethods.showProgressbar(this);
        CommonNetwork.putNetworkJsonObj(apiUrl, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    String confirmation = response.getString("confirmation");
                    if (confirmation.equals("success")) {
                        Toast.makeText(EnterQuantityActivity.this, "Cart data updated", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(EnterQuantityActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }, this);

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
                    catrSize = jsonArray.length();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(EnterQuantityActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }, this);
    }

    public static int catrSize;

    int total_qty;

    public void update_quantity() {
        total_qty = 0;
        for (ProModlModel proModlModel : proModlModels) {
            int qtyInt = Integer.parseInt(proModlModel.getQty());
            total_qty = total_qty + qtyInt;
        }
        btnOrder.setText("Order Qty" + "  " + total_qty);
        if(slctd_brand!=null)
        slctd_brand.setQuantity("" + total_qty);
    }

    private int applyPrice(int quantity){
        int price = 0;
        for(int i=0;i<productPriceModels.size();i++){
            int amount = Integer.parseInt(productPriceModels.get(i).getAmount());
            int fromVal = Integer.parseInt(productPriceModels.get(i).getFrom());
            int toVal = Integer.parseInt(productPriceModels.get(i).getTo());
            if(quantity>=fromVal && quantity<=toVal){
                price = amount*quantity;
                break;
            }
            else {
                price = amount*quantity;
            }
        }
        return price;
    }
}
