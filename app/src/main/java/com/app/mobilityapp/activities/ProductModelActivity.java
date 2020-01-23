package com.app.mobilityapp.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.adapter.ProductModelAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.ProModlModel;
import com.bumptech.glide.Glide;
import com.app.mobilityapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.GET_MODELS;

public class ProductModelActivity extends BaseActivity {
    private RecyclerView modelList;
    private ImageView brandImg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelList = findViewById(R.id.model_list);
        modelList.setLayoutManager(new GridLayoutManager(this,2));
        String brandId = getIntent().getStringExtra("brand_id");
        String brandName = getIntent().getStringExtra("brand_name");
        String brandUrlStr = getIntent().getStringExtra("brand_img");
        brandImg = findViewById(R.id.brand_img);
        Glide
                .with(this)
                .load(brandUrlStr)
                .centerCrop()
                .into(brandImg);
        ConstantMethods.setTitleAndBack(this,brandName);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("brandId",brandId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ConstantMethods.showProgressbar(this);
        CommonNetwork.postNetworkJsonObj(GET_MODELS, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                List<ProModlModel> proModlModels = new ArrayList<>();
                try {
                    String confirmation = response.getString("confirmation");
                    if(confirmation.equals("success")){
                        JSONArray jsonArray = response.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            String content = jsonObject.getString("content");
                            String brandId = jsonObject.getString("brandId");
                            ProModlModel proModlModel = new ProModlModel();
                            proModlModel.setName(name);
                            proModlModel.setContent(content);
                            proModlModel.setBrandId(brandId);
                            proModlModels.add(proModlModel);
                        }
                        ProductModelAdapter productModelAdapter = new ProductModelAdapter(ProductModelActivity.this,proModlModels);
                        modelList.setAdapter(productModelAdapter);
                        /*productModelAdapter.onClickProModlModal(proModlModel -> {
//                            CartModel cartModel = (CartModel) getIntent().getSerializableExtra("cart_model_data");
//                            cartModel.setProModlModel(proModlModel);
                            String brandId = proModlModel.getBrandId();
                           // Intent intent = new Intent(ProductModelActivity.this, ProductActivity.class);
                        *//*    Intent intent = new Intent(ProductModelActivity.this, MyCartActivity.class);
                            intent.putExtra("brand_id",brandId);
                            intent.putExtra("model_name",proModlModel.getName());
                            intent.putExtra("content",proModlModel.getContent());
//                            intent.putExtra("cart_model_data",cartModel);
                            startActivity(intent);*//*
                        });*/
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(ProductModelActivity.this, anError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        },this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_product_model;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_call:
                onCallBtnClick();
                break;
            case R.id.action_profile:
                startActivity(new Intent(this, UpdateProfileActivity.class));
                break;
            case R.id.logout_menu:
                alertDialogForLogout();
                break;
            case R.id.action_cart:
                startActivity(new Intent(this, CartChangeActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
      private void alertDialogForLogout(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Logout");
        builder1.setMessage("Do you want to logout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConstantMethods.setStringPreference("login_status","logout",ProductModelActivity.this);
                        startActivity(new Intent(ProductModelActivity.this,LoginActivity.class));
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch(requestCode){
            case 9:
                permissionGranted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
        if(permissionGranted){
            phoneCall();
        }else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void phoneCall(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:9999999999"));
            startActivity(callIntent);
        }else{
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
}
