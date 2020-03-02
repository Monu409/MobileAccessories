package com.app.mobilityapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;

import org.json.JSONException;
import org.json.JSONObject;

import static com.app.mobilityapp.app_utils.AppApis.USER_LOGIN;

public class OtpActivity extends BaseActivity {
    private Button sendOtp;
    private EditText otpEdt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Enter OTP");
        String phoneNo = getIntent().getStringExtra("mobile_num");
        otpEdt = findViewById(R.id.input_otp);
        sendOtp = findViewById(R.id.otp_btn);
        sendOtp.setOnClickListener(v->{
            String otpStr = otpEdt.getText().toString();
            if(otpStr.isEmpty()){
                Toast.makeText(this, "Please enter otp", Toast.LENGTH_SHORT).show();
            }
            else {
                JSONObject jsonObject = getSendData(phoneNo,otpStr);
                sendOTP(jsonObject);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_otp;
    }

    private JSONObject getSendData(String phoneNo,String otpStr){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone",phoneNo);
            jsonObject.put("otp",otpStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void sendOTP(JSONObject jsonObject){
        CommonNetwork.postNetworkJsonObj(USER_LOGIN, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                try {
                    String mResponse = response.getString("confirmation");
                    if(mResponse.equals("success")){
                        Intent intent = new Intent(OtpActivity.this,DashboardActivity.class);
                        startActivity(intent);
                        Toast.makeText(OtpActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                        JSONObject dataObj = response.getJSONObject("data");
                        String _id = dataObj.getString("_id");
                        String token = response.getString("token");
                        String creditLimit = dataObj.getString("creditLimit");
                        String userType = dataObj.getString("userType");
                        ConstantMethods.setStringPreference("user_id",_id,OtpActivity.this);
                        ConstantMethods.setStringPreference("login_status","login",OtpActivity.this);
                        ConstantMethods.setStringPreference("credit_limit",creditLimit,OtpActivity.this);
                        ConstantMethods.setStringPreference("user_token",token,OtpActivity.this);
                        ConstantMethods.setStringPreference("user_type",userType,OtpActivity.this);
                    }
                    else if(mResponse.equals("invalid")){
                        String message = response.getString("message");
                        Toast.makeText(OtpActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Toast.makeText(OtpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }

}
