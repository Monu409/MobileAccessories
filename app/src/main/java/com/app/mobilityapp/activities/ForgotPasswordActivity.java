package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.app.mobilityapp.app_utils.AppApis.FORGOT_PASSWORD;


public class ForgotPasswordActivity extends BaseActivity {
    private Button submitBtn;
    private EditText emailEdt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        submitBtn = findViewById(R.id.submit_btn);
        emailEdt = findViewById(R.id.input_email);
        ConstantMethods.setTitleAndBack(this,"Forget Password");
        submitBtn.setOnClickListener(v -> {
            String emailStr = emailEdt.getText().toString();
            if (!ConstantMethods.isValidMail(emailStr)) {
                Toast.makeText(this, "Please enter valid email address", Toast.LENGTH_SHORT).show();
            } else {
                ConstantMethods.showProgressbar(this);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", emailStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                forgotPassword(jsonObject);
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_forgot_pass;
    }

    private void forgotPassword(JSONObject jsonObject) {
        AndroidNetworking
                .post(FORGOT_PASSWORD)
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("res", response.toString());
                        ConstantMethods.dismissProgressBar();
                        try {
                            String message = response.getString("message");
                            Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("anError", anError.toString());
                        Toast.makeText(ForgotPasswordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
