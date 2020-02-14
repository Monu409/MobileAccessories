package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.OrderDetailAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.OrderDetailModel;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.ORDER_LIST;


public class OrderDetailActivity extends BaseActivity {
    private TextView orderIdTxt,dateTxt,amountTxt,discountTxt;
    private RecyclerView itemList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Order Details");
        String orderId = getIntent().getStringExtra("order_id");
        orderIdTxt = findViewById(R.id.order_id_txt);
        dateTxt = findViewById(R.id.date_txt);
        amountTxt = findViewById(R.id.total_txt);
        discountTxt = findViewById(R.id.discount_txt);
        itemList = findViewById(R.id.item_list);
        itemList.setLayoutManager(new LinearLayoutManager(this));
        getOrderDetail(orderId);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_order_detail;
    }

    private void getOrderDetail(String orderId){
        ConstantMethods.showProgressbar(this);
        CommonNetwork.getNetworkJsonObj(ORDER_LIST + "/" + orderId, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                Gson gson = new Gson();
                OrderDetailModel orderDetailModel = gson.fromJson(String.valueOf(response),OrderDetailModel.class);
                String confirmation = orderDetailModel.getConfirmation();
                if(confirmation.equals("success")){
                    OrderDetailModel.Data data = orderDetailModel.getData();
                    String orderIdSeen = data.getOrderId();
                    int totalAmount = data.getAmount();
                    int discount = data.getDiscount();
                    String date = data.getCreatedAt();
                    String showDate = ConstantMethods.changeDateFormate(date);
                    orderIdTxt.setText("OredrID: "+orderIdSeen);
                    dateTxt.setText(showDate);
                    amountTxt.setText("₹ "+totalAmount);
                    discountTxt.setText("₹ "+discount);

                    List<OrderDetailModel.Productdetail> productdetails = data.getProductdetails();
                    OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(productdetails,OrderDetailActivity.this);
                    itemList.setAdapter(orderDetailAdapter);
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(OrderDetailActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }
}
