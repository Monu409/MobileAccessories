package com.app.mobilityapp.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.app.mobilityapp.app_utils.AppApis.SEND_OTP;
import static com.app.mobilityapp.app_utils.AppApis.USER_LOGIN;


public class LoginActivity extends BaseActivity {
    private Button loginBtn;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 7;
    private TextView signupTxt,forgotPass;
    private EditText phoneEdt,passEdt;
    private String deviceId;
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
        if(checkAndRequestPermissions()) {
            loginBtn = findViewById(R.id.login_btn);
            loginBtn.setOnClickListener(t -> {
                String phoneNum = phoneEdt.getText().toString();
                String passStr = passEdt.getText().toString();
                if(phoneNum.isEmpty()){
                    Toast.makeText(this, "Enter your mobile number", Toast.LENGTH_SHORT).show();
                }
                else{
                    ConstantMethods.showProgressbar(this);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("phone",phoneNum);
                        jsonObject.put("deviceid",deviceId);
//                        jsonObject.put("password",passStr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                   loginUser(jsonObject);
                }
            });

            forgotPass.setOnClickListener(v->{
                startActivity(new Intent(this,ForgotPasswordActivity.class));
            });
        }
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
//                                Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
//                                JSONObject dataObj = response.getJSONObject("data");
//                                String _id = dataObj.getString("_id");
//                                String token = response.getString("token");
//                                String creditLimit = dataObj.getString("creditLimit");
//                                String userType = dataObj.getString("userType");
//                                ConstantMethods.setStringPreference("user_id",_id,LoginActivity.this);
//                                ConstantMethods.setStringPreference("login_status","login",LoginActivity.this);
//                                ConstantMethods.setStringPreference("credit_limit",creditLimit,LoginActivity.this);
//                                ConstantMethods.setStringPreference("user_token",token,LoginActivity.this);
//                                ConstantMethods.setStringPreference("user_type",userType,LoginActivity.this);
                            }
                            else if(mResponse.equals("invalid")){
                                String message = response.getString("message");
                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ConstantMethods.dismissProgressBar();
                        Log.e("anError",anError.toString());
                        Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int wtite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int call = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (wtite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (read != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (call != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return true;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("in fragment on request", "Permission callback called-------");
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("in fragment on request", "CAMERA & WRITE_EXTERNAL_STORAGE READ_EXTERNAL_STORAGE permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d("in fragment on request", "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera and Storage Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                                    .show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
