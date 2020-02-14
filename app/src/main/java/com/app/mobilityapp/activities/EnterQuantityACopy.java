package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.ProductModelListAdapter1;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.modals.LocalQuantityModel;
import com.app.mobilityapp.modals.ProModlModel;
import com.app.mobilityapp.modals.ProductPriceModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EnterQuantityACopy extends BaseActivity {
    private RecyclerView qtyList;
    private Button btnOrder;
    private Button submit;
    private String productIdforJson, brandId;
    private ProductModelListAdapter1 productModelAdapter;
    private ArrayList<ProModlModel> proModlModels;
    private String idForUrl;
    private List<ProductPriceModel> productPriceModels;
    int mQty = 0;
    int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qtyList = findViewById(R.id.product_recyler);
        btnOrder = findViewById(R.id.btn_order);
        submit = findViewById(R.id.btn_submit);
        qtyList.setLayoutManager(new LinearLayoutManager(this));
        productPriceModels = (ArrayList<ProductPriceModel>) getIntent().getSerializableExtra("price_list");
        position = getIntent().getIntExtra("position",0);
        ConstantMethods.setTitleAndBack(this, "Enter Quantity");
        proModlModels = (ArrayList<ProModlModel>) getIntent().getSerializableExtra("qty_list");
        List<LocalQuantityModel.Modallist>  modelList = null;
        List<LocalQuantityModel> localQuantityModels = ConstantMethods.getQtyArrayListShared(this,"local_qty_models");

        try {
            modelList = localQuantityModels.get(position).getModallist();
            btnOrder.setText("Order Qty" + "  " + localQuantityModels.get(position).getTotalQty());
        }
        catch (Exception e){
            e.printStackTrace();
            btnOrder.setText("Order Qty" + "  " + mQty);
        }
        if(localQuantityModels==null||modelList==null){
            localQuantityModels = new ArrayList<>();
        }
        else {
            for (int i = 0; i < proModlModels.size(); i++) {
                proModlModels.get(i).setQty(modelList.get(i).getQuantity());
            }
        }
        productModelAdapter = new ProductModelListAdapter1(this, proModlModels);
        qtyList.setAdapter(productModelAdapter);
        productIdforJson = getIntent().getStringExtra("brand_id");
        brandId = getIntent().getStringExtra("brandId");


        submit.setOnClickListener(v -> {
            if(btnOrder.getText().toString().trim().equals("Order Qty  0")){
                Toast.makeText(this, "Select minimum 1 quantity", Toast.LENGTH_SHORT).show();
            }
            else {
                int totalQty = 0;
                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                try {
                    for (int i = 0; i < proModlModels.size(); i++) {
                        ProModlModel proModlModel = proModlModels.get(i);
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("modalid", proModlModels.get(i).getId());
                        jsonObject1.put("quantity", Integer.parseInt(proModlModels.get(i).getQty()));
                        totalQty = totalQty + Integer.parseInt(proModlModel.getQty());
                        jsonArray.put(i, jsonObject1);
                    }

                    jsonObject.put("productid", productIdforJson);
                    jsonObject.put("brandid", brandId);
                    int applyPrice = applyPrice(totalQty);
                    jsonObject.put("price", String.valueOf(applyPrice));
                    jsonObject.put("total_qty", totalQty);
                    jsonObject.put("modallist", jsonArray);
                    Gson gson = new Gson();
                    LocalQuantityModel conversationModel = gson.fromJson(String.valueOf(jsonObject),LocalQuantityModel.class);
                    List<LocalQuantityModel> localQuantityModels1 = ConstantMethods.getQtyArrayListShared(this,"local_qty_models");
                    if(localQuantityModels1==null){
                        localQuantityModels1 = new ArrayList<>();
                    }
                    localQuantityModels1.add(position,conversationModel);
                    ConstantMethods.saveQtyListShared(localQuantityModels1,this,"local_qty_models");
//                    String jsonArrayPrif = jsonObject.toString();
//                    ConstantMethods.setStringPreference("product_json",jsonArrayPrif,this);
                    onBackPressed();
//                    if (previousViewName.equals("product")) {
//                        addDataIntoCart(jsonObject, ADD_INTO_CART);
//                    } else if (previousViewName.equals("cart_unslctd")) {
//                        updateDataIntoCart(jsonObject, UPDATE_CART + idForUrl);
//                    }

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
}
