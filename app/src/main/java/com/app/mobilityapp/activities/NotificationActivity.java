package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.NotificationAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.NotifivationModel;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.GLOBEL_NOTIFICATION;

public class NotificationActivity extends BaseActivity {
    RecyclerView notifList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Notification");
        notifList = findViewById(R.id.notif_list);
        notifList.setLayoutManager(new LinearLayoutManager(this));
        getGlobelNotification();
    }

    private void getGlobelNotification(){
        ConstantMethods.showProgressbar(this);
        CommonNetwork.getNetworkJsonObj(GLOBEL_NOTIFICATION, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                Log.e("response",""+response);
                Gson gson = new Gson();
                NotifivationModel notifivationModel = gson.fromJson(String.valueOf(response),NotifivationModel.class);
                String status = notifivationModel.getConfirmation();
                if(status.equals("success")){
                    List<NotifivationModel.NotifivationModelClild> notifivationModelClild = notifivationModel.getData();
                    NotificationAdapter notificationAdapter = new NotificationAdapter(NotificationActivity.this,notifivationModelClild);
                    notifList.setAdapter(notificationAdapter);
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Log.e("error",""+anError);
            }
        },this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_notification;
    }
}
