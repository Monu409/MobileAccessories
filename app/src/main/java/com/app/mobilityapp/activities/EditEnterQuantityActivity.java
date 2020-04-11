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
import com.app.mobilityapp.adapter.EditEntrQtyAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.EditCartModel;
import com.app.mobilityapp.modals.ModelListModel;
import com.app.mobilityapp.modals.ProModlModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.EDIT_CART;

public class EditEnterQuantityActivity extends BaseActivity {
    private RecyclerView qtyList;
    private Button doneBtn,btnOrder;
    String brandDetailId,brandId,cartId;
    private double totalPrice;
    private List<EditCartModel.Modallist> modallists;
    private List<EditCartModel.Price> priceList;
    int otherQty;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Edit Cart");
        qtyList = findViewById(R.id.edit_qty_list);
        qtyList.setLayoutManager(new LinearLayoutManager(this));
        modallists = (List<EditCartModel.Modallist>) getIntent().getSerializableExtra("modal_lists");
        priceList = (List<EditCartModel.Price>) getIntent().getSerializableExtra("price_list");
        brandDetailId = getIntent().getStringExtra("brandDetail_id");
        brandId = getIntent().getStringExtra("brand_id");
        cartId = getIntent().getStringExtra("cart_id");
        EditEntrQtyAdapter editEntrQtyAdapter = new EditEntrQtyAdapter(this,modallists);
        qtyList.setAdapter(editEntrQtyAdapter);
        doneBtn = findViewById(R.id.done_btn);
        btnOrder = findViewById(R.id.btn_order);
        otherQty = getIntent().getIntExtra("other_qty",0);
        int totalQty1 = getQty(modallists);
        btnOrder.setText("Order Qty" + "  " + totalQty1);
        doneBtn.setOnClickListener(v->{
            ConstantMethods.showProgressbar(this);
            int totalQty = getQty(modallists);
            totalPrice = applyPrice((totalQty+otherQty),priceList);

            JSONObject jsonObject = getEditJSon(modallists,modallists);

            CommonNetwork.postNetworkJsonObj(EDIT_CART, jsonObject, new JSONResult() {
                @Override
                public void notifySuccess(@NonNull JSONObject response) {
                    ConstantMethods.dismissProgressBar();
                    Log.e("response",""+response);
                    try {
                        String confirmation = response.getString("confirmation");
                        if(confirmation.equals("success")){
                            Toast.makeText(EditEnterQuantityActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void notifyError(@NonNull ANError anError) {
                    Log.e("response",""+anError);
                    Toast.makeText(EditEnterQuantityActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    ConstantMethods.dismissProgressBar();
                }
            },this);
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_edit_enter_qty;
    }

    private JSONObject getEditJSon(List<EditCartModel.Modallist> modallists,List<EditCartModel.Modallist> modelListModels){
        JSONObject finalJson = new JSONObject();
        Gson gson = new Gson();
        String modelListData = gson.toJson(modelListModels);
        try {
            JSONArray modelListArr = new JSONArray(modelListData);
            finalJson.put("brandDetailsId",brandDetailId);
            finalJson.put("brand",brandId);
            finalJson.put("modallist",modelListArr);
            finalJson.put("price",totalPrice);
            finalJson.put("cartId",cartId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalJson;
    }

    private int getQty(List<EditCartModel.Modallist> modallists){
        int sumQty = 0;
        for(int i=0;i<modallists.size();i++){
            int qty = modallists.get(i).getQuantity();
            sumQty = sumQty+qty;
        }
        return sumQty;
    }

    private double applyPrice(int quantity,List<EditCartModel.Price> priceList){
        double price = 0;
        for(int i=0;i<priceList.size();i++){
            double amount = Double.parseDouble(priceList.get(i).getAmount());
            int fromVal = Integer.parseInt(priceList.get(i).getFrom());
            int toVal = Integer.parseInt(priceList.get(i).getTo());
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

    public void update_quantity() {
        int total_qty = 0;
        for (EditCartModel.Modallist proModlModel : modallists) {
            int qtyInt = proModlModel.getQuantity();
            total_qty = total_qty + qtyInt;
        }
        btnOrder.setText("Order Qty" + "  " + total_qty);
        if (slctd_brand != null)
            slctd_brand.setQuantity("" + total_qty);
    }
}
