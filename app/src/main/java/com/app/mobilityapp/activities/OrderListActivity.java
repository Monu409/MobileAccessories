package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.OrderListAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.CartNewModel;
import com.app.mobilityapp.modals.MyOrderModel;
import com.app.mobilityapp.modals.OrderListModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.app.mobilityapp.app_utils.AppApis.CREDIT_LIMIT;
import static com.app.mobilityapp.app_utils.AppApis.ORDER_LIST;

public class OrderListActivity extends BaseActivity {
    private RecyclerView myOrderList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myOrderList = findViewById(R.id.my_order_list);
        myOrderList.setLayoutManager(new LinearLayoutManager(this));
        ConstantMethods.setTitleAndBack(this,"My Orders");
        getMyOrders();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_order_list;
    }

    private void getMyOrders(){
        ConstantMethods.showProgressbar(this);
        CommonNetwork.getNetworkJsonObj(ORDER_LIST, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                Gson gson = new Gson();
                MyOrderModel myOrderModel = gson.fromJson(String.valueOf(response), MyOrderModel.class);
                String confirmation = myOrderModel.getConfirmation();
                if(confirmation.equals("success")){
                    List<MyOrderModel.MyOrderChildModel> myOrderChildModels = myOrderModel.getData();
                    OrderListAdapter orderListAdapter = new OrderListAdapter(myOrderChildModels,OrderListActivity.this);
                    myOrderList.setAdapter(orderListAdapter);
                }
//                try {
//                    String confirmation = response.getString("confirmation");
//                    if(confirmation.equals("success")){
//                        List<OrderListModel> orderListModels = new ArrayList<>();
//                        JSONArray jsonArray = response.getJSONArray("data");
//                        for(int i=0;i<jsonArray.length();i++){
//                            int totalQty = 0;
//                            JSONObject childObj = jsonArray.getJSONObject(i);
//                            String orderId = childObj.getString("orderId");
//                            String listPrice = childObj.getString("amount");
////                            JSONObject brandidObj = childObj.getJSONObject("brandid");
////                            JSONObject productIdObj = childObj.getJSONObject("productId");
////                            JSONObject categoryIdObj = productIdObj.getJSONObject("categoryId");
////                            String itemName = brandidObj.getString("name");
////                            String itemImg = brandidObj.getString("imgUrl");
//                            String catNameStr = categoryIdObj.getString("name");
//                            JSONArray modelListArr = childObj.getJSONArray("modallist");
//                            String createdAt = childObj.getString("createdAt");
//                            String[] dateStr = createdAt.split("T");
//                            String nowDateStr = dateStr[0];
//                            String showDate = ConstantMethods.changeDateFormate(nowDateStr);
//                            for(int j=0;j<modelListArr.length();j++){
//                                JSONObject modalListObj = modelListArr.getJSONObject(j);
//                                String qtyStr = modalListObj.getString("quantity");
//                                int qtyInt = Integer.parseInt(qtyStr);
//                                totalQty = totalQty+qtyInt;
//                            }
//                            OrderListModel orderListModel = new OrderListModel();
////                            orderListModel.setBrandName(itemName);
//                            orderListModel.setOrderId("ORDER ID: "+orderId);
//                            orderListModel.setTotalPrice(listPrice);
//                            orderListModel.setDateStr(showDate);
////                            orderListModel.setImgUrl(itemImg);
//                            orderListModel.setCatName(catNameStr);
//                            orderListModel.setQuantity(String.valueOf(totalQty));
//                            orderListModel.setJsonArray(modelListArr.toString());
//                            orderListModels.add(orderListModel);
//                        }
//                        OrderListAdapter orderListAdapter = new OrderListAdapter(orderListModels,OrderListActivity.this);
//                        myOrderList.setAdapter(orderListAdapter);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(OrderListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }
}
