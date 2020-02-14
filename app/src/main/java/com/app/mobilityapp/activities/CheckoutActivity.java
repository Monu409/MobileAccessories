package com.app.mobilityapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.app.mobilityapp.modals.CartNewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.ADD_INTO_CART;
import static com.app.mobilityapp.app_utils.AppApis.CREDIT_LIMIT;
import static com.app.mobilityapp.app_utils.AppApis.DELETE_CART;
import static com.app.mobilityapp.app_utils.AppApis.GET_PROFILE;
import static com.app.mobilityapp.app_utils.AppApis.PLACE_ORDER;
import static com.app.mobilityapp.app_utils.AppApis.PROFILE_UPDATE;

public class CheckoutActivity extends BaseActivity {
    private RecyclerView checkoutList;
    private TextView totalTxt, discounntTxt, netAmountTxt,totalBotmTxt;
    private EditText addressEdt;
    private Button editAdrsBtn,continueBtn;
    private JSONObject jsonForOrder;
    private JSONArray cartIdArray = new JSONArray();
    private RelativeLayout dataAvailView;
    private LinearLayout noDataView;
    private String ADDRESS_MATCHER = "[!#$%&(){|}~:;<=>?@*+,./^_`\\'\\\" \\t\\r\\n\\f-]+";
    private JSONObject cartIdJSON = new JSONObject();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Order Summary");
        checkoutList = findViewById(R.id.checkout_list);
        totalTxt = findViewById(R.id.total_txt);
        totalBotmTxt = findViewById(R.id.totl_txt);
        discounntTxt = findViewById(R.id.discount_txt);
        netAmountTxt = findViewById(R.id.net_amount_txt);
        addressEdt = findViewById(R.id.address_edt);
        editAdrsBtn = findViewById(R.id.edit_btn);
        continueBtn = findViewById(R.id.placed_btn);
        dataAvailView = findViewById(R.id.data_avail_view);
        noDataView = findViewById(R.id.empty_cart_view);
        noDataView.setOnClickListener(v->{
            startActivity(new Intent(this,DashboardActivity.class));
        });
        checkoutList.setLayoutManager(new LinearLayoutManager(this));
        getAddressData();
        addressEdt.setEnabled(false);
        editAdrsBtn.setOnClickListener(v->{
            String btnTxt = editAdrsBtn.getText().toString();
            if(btnTxt.equals("Edit")) {
                addressEdt.setEnabled(true);
                editAdrsBtn.setText("Save");
            }
            else if(btnTxt.equals("Save")){
                saveAddress();
            }
        });
        continueBtn.setOnClickListener(v->{
            String addressStr = addressEdt.getText().toString();
            if(addressStr.equals("No address specified")||addressStr.equals("")||!ConstantMethods.checkAddress(addressStr)){
                ConstantMethods.getAlertMessage(this,"Please enter valid shipping address");
            }
            else {
                orderPlaced(jsonForOrder);
            }
        });
        getCartDetail();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_checkout;
    }

    private void getAddressData() {
        ConstantMethods.showProgressbar(this);
        String userId = ConstantMethods.getStringPreference("user_id", this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(GET_PROFILE, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    JSONObject userInfo = jsonArray.getJSONObject(0);
                    JSONObject addressObj = userInfo.getJSONObject("addressId");
                    String addressStr = addressObj.getString("address");
                    if(addressStr.equals("null")){
                        addressStr = "No address specified";
                    }
                    addressEdt.setText(addressStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
            }
        }, this);
    }
    private void getCartDetail() {
        CommonNetwork.getNetworkJsonObj(ADD_INTO_CART, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Gson gson = new Gson();
                CartNewModel cartNewModel = gson.fromJson(String.valueOf(response),CartNewModel.class);
                String confirmation = cartNewModel.getConfirmation();
                if(confirmation.equals("success")){
                    List<CartNewModel.CartChildModel> cartChildModels = cartNewModel.getData();
                    CartChangeAdapter cartChangeAdapter = new CartChangeAdapter(cartChildModels, CheckoutActivity.this);
                    checkoutList.setAdapter(cartChangeAdapter);
                    if(cartChildModels.size()!=0){
                        dataAvailView.setVisibility(View.VISIBLE);
                        noDataView.setVisibility(View.GONE);
                    }
                    int priceSum = 0;
                    for(int i=0;i<cartChildModels.size();i++){
                        int price = cartChildModels.get(i).getPrice();
                        String cartId = cartChildModels.get(i).getId();
                        cartIdArray.put(cartId);
                        priceSum = priceSum+price;
                    }
                    totalTxt.setText("₹ "+priceSum);
                    getCreditLimit(priceSum/*,cartChangeModels*/);
                    jsonForOrder = getOrderJson(cartChildModels,priceSum);
                }
            }
            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(CheckoutActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }
    double totalDiscount;
    private void getCreditLimit(int totalAmount/*,List<CartChangeModel> cartChangeModels*/){

        CommonNetwork.getNetworkJsonObj(CREDIT_LIMIT, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                try {
                    ConstantMethods.dismissProgressBar();
                    JSONObject creditObj = response.getJSONObject("data");
                    String discountPercent = creditObj.getString("discount");
                    int percentIntDiscount = Integer.parseInt(discountPercent);
                    totalDiscount = (totalAmount*percentIntDiscount)/100;
                    netAmountTxt.setText(String.valueOf(totalAmount-totalDiscount));
                    discounntTxt.setText(String.valueOf(totalDiscount));
                    totalBotmTxt.setText("₹ "+(totalAmount-totalDiscount));
//                    jsonForOrder = getOrderJson(cartChangeModels);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(CheckoutActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }

    private void saveAddress(){
        String addressStr  = addressEdt.getText().toString();
        if(addressStr.equals("No address specified")||addressStr.equals("")||!ConstantMethods.checkAddress(addressStr)){
            Toast.makeText(this, "Enter valid address", Toast.LENGTH_SHORT).show();
        }
        else {
            ConstantMethods.showProgressbar(this);
            String id = ConstantMethods.getStringPreference("user_id", this);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("address", addressStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CommonNetwork.putNetworkJsonObj(PROFILE_UPDATE + "/" + id, jsonObject, new JSONResult() {
                @Override
                public void notifySuccess(@NonNull JSONObject response) {
                    ConstantMethods.dismissProgressBar();
                    try {
                        String confirmation = response.getString("confirmation");
                        if (confirmation.equals("success")) {
                            Toast.makeText(CheckoutActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                            addressEdt.setEnabled(false);
                            editAdrsBtn.setText("Edit");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void notifyError(@NonNull ANError anError) {
                    ConstantMethods.dismissProgressBar();
                    Log.e("response", "" + anError);
                }
            }, this);
        }
    }

    private JSONObject getOrderJson(List<CartNewModel.CartChildModel> cartChildModels,int priceSum) {
        JSONObject parentObject = new JSONObject();
        Gson gson = new Gson();
        try {
            JSONArray prodctDtlArr = new JSONArray();
            for(int i=0;i<cartChildModels.size();i++) {
                CartNewModel.Productid productid = cartChildModels.get(i).getProductid();
                CartNewModel.CategoryId categoryId = cartChildModels.get(i).getCategoryId();
                CartNewModel.SubCategoryId subCategoryId = cartChildModels.get(i).getSubCategoryId();
                CartNewModel.Subcategory2 subcategory2 = cartChildModels.get(i).getSubcategory2();
                Object o = cartChildModels.get(i).getSubcategory3();
                int price = cartChildModels.get(i).getPrice();
                List<CartNewModel.BrandDetail_> brandDetail = cartChildModels.get(i).getBrandDetails();
                String productIdStr = productid.getId();
                String catidStr = categoryId.getId();
                String subcatidStr = subCategoryId.getId();
                String subcat2idStr = null;
                String subcat3idStr = null;
                try{
                    subcat2idStr = subcategory2.getId();
                    subcat3idStr = subcategory2.getId();
                }
                catch (Exception e){
                    e.printStackTrace();
                    subcat2idStr = "";
                    subcat3idStr = "";
                }
                String brndDtlStr = gson.toJson(brandDetail);
                JSONArray jsonArray = new JSONArray(brndDtlStr);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("productId",productIdStr);
                jsonObject.put("categoryId",catidStr);
                jsonObject.put("subCategoryId",subcatidStr);
                jsonObject.put("subcategory2",null);
                jsonObject.put("subcategory3",null);
                jsonObject.put("price",price);
                jsonObject.put("brandDetails",jsonArray);
                prodctDtlArr.put(i,jsonObject);
            }
            parentObject.put("amount",priceSum);
            parentObject.put("netamount",priceSum);
            parentObject.put("discount",0);
            parentObject.put("productdetails",prodctDtlArr);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parentObject;
    }

    private void orderPlaced(JSONObject jsonObject) {
        ConstantMethods.showProgressbar(this);
        CommonNetwork.postNetworkJsonObj(PLACE_ORDER, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    String confirmation = response.getString("confirmation");
//                    String message = response.getString("message");
                    if (confirmation.equals("success")) {
                        Toast.makeText(CheckoutActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                        JSONObject resObj = response.getJSONObject("data");
                        String cartId = resObj.getString("_id");
//                        JSONArray cartIdArr = new JSONArray();
//                        cartIdArr.put(0,cartId);
                        JSONObject cartIdObj = new JSONObject();
                        cartIdObj.put("_id",cartIdArray);
                        deleteCart(DELETE_CART+cartIdObj);
                    }
                    else if(confirmation.equals("failed")){
//                        ConstantMethods.getAlertMessage(CheckoutActivity.this,message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(CheckoutActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);

    }

    private void deleteCart(String url){
        CommonNetwork.deleteNetworkJsonObj(url, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("res",""+response);
                try {
                    String confirmation = response.getString("confirmation");
                    if(confirmation.equals("success")){
                        dataAvailView.setVisibility(View.GONE);
                        noDataView.setVisibility(View.VISIBLE);
                        getCartDetail();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("res",""+anError);
            }
        },this);
    }

}
