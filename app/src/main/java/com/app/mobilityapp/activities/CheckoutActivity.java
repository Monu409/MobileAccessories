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
import com.app.mobilityapp.modals.CartChangeModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
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
    private JSONArray jsonForOrder;
    private JSONArray cartIdArray = new JSONArray();
    private RelativeLayout dataAvailView;
    private LinearLayout noDataView;
    private String ADDRESS_MATCHER = "[!#$%&(){|}~:;<=>?@*+,./^_`\\'\\\" \\t\\r\\n\\f-]+";

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
    int sumAmount = 0;
    private void getCartDetail() {
        CommonNetwork.getNetworkJsonObj(ADD_INTO_CART, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
//                ConstantMethods.dismissProgressBar();
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
                        String brandName = brandObj.getString("name");
                        String catName = categoryId.getString("name");
                        String brandImgUrl = brandObj.getString("imgUrl");
                        String brandPrice = childObj.getString("price");
                        int priceInt = Integer.parseInt(brandPrice);
                        sumAmount = sumAmount+priceInt;
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
                        cartIdArray.put(i,cartId);
                        cartChangeModels.add(cartChangeModel);
                    }
                    CartChangeAdapter cartChangeAdapter = new CartChangeAdapter(cartChangeModels, CheckoutActivity.this);
                    checkoutList.setAdapter(cartChangeAdapter);
                    totalTxt.setText(sumAmount+".0");
//                    jsonForOrder = getOrderJson(cartChangeModels);
                    getCreditLimit(sumAmount,cartChangeModels);
                    if(cartChangeModels.size()!=0){
                        dataAvailView.setVisibility(View.VISIBLE);
                        noDataView.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
//                ConstantMethods.dismissProgressBar();
                Toast.makeText(CheckoutActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }
    double totalDiscount;
    private void getCreditLimit(int totalAmount,List<CartChangeModel> cartChangeModels){

        CommonNetwork.getNetworkJsonObj(CREDIT_LIMIT, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                try {
                    JSONObject creditObj = response.getJSONObject("data");
                    String discountPercent = creditObj.getString("discount");
                    int percentIntDiscount = Integer.parseInt(discountPercent);
                    totalDiscount = (totalAmount*percentIntDiscount)/100;
                    netAmountTxt.setText(String.valueOf(totalAmount-totalDiscount));
                    discounntTxt.setText(String.valueOf(totalDiscount));
                    totalBotmTxt.setText("₹ "+(totalAmount-totalDiscount));
                    jsonForOrder = getOrderJson(cartChangeModels);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(@NonNull ANError anError) {
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

    private JSONArray getOrderJson(List<CartChangeModel> cartChangeModels) {
        JSONObject parentObject = new JSONObject();
        String amountStr = totalTxt.getText().toString();
        String discountStr = discounntTxt.getText().toString();
        String netAmountStr = netAmountTxt.getText().toString();
        JSONArray jsonArray = new JSONArray();
        try {
            for (int i = 0; i < cartChangeModels.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("productId", cartChangeModels.get(i).getProductId());
                jsonObject.put("brandid", cartChangeModels.get(i).getBrandId());
                jsonObject.put("modallist", new JSONArray(cartChangeModels.get(i).getJsonArray()));
                jsonObject.put("amount",Double.parseDouble(cartChangeModels.get(i).getTotalPrice()));
                jsonObject.put("discount",Double.parseDouble(discountStr));
                jsonObject.put("netamount",Double.parseDouble(netAmountStr));
                jsonObject.put("shippingFees",0);
                jsonObject.put("shippingDiscount",0);
                jsonArray.put(i, jsonObject);
            }
            parentObject.put("order_list", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private void orderPlaced(JSONArray jsonArray) {
        ConstantMethods.showProgressbar(this);
        CommonNetwork.postNetworkJsonArr(PLACE_ORDER, jsonArray, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                try {
                    String confirmation = response.getString("confirmation");
//                    String message = response.getString("message");
                    if (confirmation.equals("success")) {
                        Toast.makeText(CheckoutActivity.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                        JSONArray dataArr = response.getJSONArray("data");
                        JSONArray cartIdArr = new JSONArray();
                        for(int i=0;i<dataArr.length();i++){
                            JSONObject cartObj = dataArr.getJSONObject(i);
                            String cartId = cartObj.getString("_id");
                            cartIdArr.put(i,cartId);
                        }
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
