package com.app.mobilityapp.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;
import static com.app.mobilityapp.app_utils.AppApis.SEND_OTP;

public class LoginActivity extends BaseActivity {
    private Button loginBtn;
    private TextView signupTxt,forgotPass;
    private EditText phoneEdt,passEdt;
    private String deviceId;
    String newToken = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Login");
        setTitleColor(Color.parseColor("#E5D10B"));
        signupTxt = findViewById(R.id.sign_up_txt);
        forgotPass = findViewById(R.id.forget_pass_txt);
        phoneEdt = findViewById(R.id.input_phone);
        passEdt = findViewById(R.id.input_pass);
        deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        loginBtn = findViewById(R.id.login_btn);
        Intent intent = getIntent();
        String phoneStr = intent.getStringExtra("phone");
        phoneEdt.setText(phoneStr);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);

            }
        });
        loginBtn.setOnClickListener(t -> {
            String phoneNum = phoneEdt.getText().toString();
//            String myToken = ConstantMethods.getStringPreference("my_token",this);
            if(phoneNum.isEmpty()){
                Toast.makeText(this, "Enter your mobile number", Toast.LENGTH_SHORT).show();
            }
            else{
                ConstantMethods.showProgressbar(this);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("phone",phoneNum);
                    jsonObject.put("deviceid",newToken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loginUser(jsonObject);
            }
        });

        forgotPass.setOnClickListener(v->{
            startActivity(new Intent(this,ForgotPasswordActivity.class));
        });
        ConstantMethods.twoColoredText(signupTxt,"Don't have any account ","Sign up");
        signupTxt.setOnClickListener(v->{
            startActivity(new Intent(this,SignUpActivity.class));
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    private void loginUser(JSONObject jsonObject){
        AndroidNetworking
                .post(SEND_OTP)
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("res",response.toString());
                        ConstantMethods.dismissProgressBar();
                        try {
                            String mResponse = response.getString("confirmation");
                            if(mResponse.equals("success")){
                                String phoneNum = phoneEdt.getText().toString();
                                Intent intent = new Intent(LoginActivity.this,OtpActivity.class);
                                intent.putExtra("mobile_num",phoneNum);
                                startActivity(intent);
                            }
                            else if(mResponse.equals("invalid")){
                                String message = response.getString("message");
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                            else if(mResponse.equals("block")){
//                                String message = response.getString("message");
                                messageDialog("Your profile has been inactive\nPlease contact your system admin");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ConstantMethods.dismissProgressBar();
                        Log.e("anError",anError.toString());
                        try {
                            JSONObject jsonObject1 = new JSONObject(anError.getErrorBody());
                            String error = jsonObject1.getString("message");
//                            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                            String phoneNum = phoneEdt.getText().toString();
                            notRegisterPopup(phoneNum);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    private void notRegisterPopup(String mobileNum){
        AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        alert.setTitle("Number does not exist");
        alert.setMessage("This mobile number is not registered with us.\nDo you want to continue");
        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
            Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
            intent.putExtra("mobile_number",mobileNum);
            startActivity(intent);
        });

        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            // what ever you want to do with No option.
        });

        alert.show();
    }
    private void messageDialog(String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        alert.setTitle("Profile Alert");
        alert.setMessage(message);
        alert.setPositiveButton("Cancel", (dialog, whichButton) -> {

        });

        alert.setNegativeButton("Call", (dialog, whichButton) -> {
            onCallBtnClick();
        });
        alert.show();
    }

    private void onCallBtnClick(){
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        }else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                phoneCall();
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    private void phoneCall(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:8882396778"));
            startActivity(callIntent);
        }else{
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
}
