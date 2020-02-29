package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.OrdrRcvdAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.OrderRcvdModel;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.List;
import static com.app.mobilityapp.app_utils.AppApis.SELLER_ORDER_RECEIVED;

public class OrderReceivedActivity extends BaseActivity {
    private RecyclerView ordrrcvdList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"My Order Received");
        ordrrcvdList = findViewById(R.id.ordr_rcvd_list);
        ordrrcvdList.setLayoutManager(new LinearLayoutManager(this));
        getorderRcvd();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_order_received;
    }
    private void getorderRcvd(){
        ConstantMethods.showProgressbar(this);
        JSONObject jsonObject = new JSONObject();
        CommonNetwork.postNetworkJsonObj(SELLER_ORDER_RECEIVED, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("test",""+response);
                ConstantMethods.dismissProgressBar();
                Gson gson = new Gson();
                OrderRcvdModel orderRcvdModel = gson.fromJson(String.valueOf(response),OrderRcvdModel.class);
                String confirmation = orderRcvdModel.getConfirmation();
                if(confirmation.equals("success")){
                    List<OrderRcvdModel.OrderRcvdModelChild> orderRcvdModelChild = orderRcvdModel.getData();
                    OrdrRcvdAdapter ordrRcvdAdapter = new OrdrRcvdAdapter(orderRcvdModelChild,OrderReceivedActivity.this);
                    ordrrcvdList.setAdapter(ordrRcvdAdapter);
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(OrderReceivedActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }
}
