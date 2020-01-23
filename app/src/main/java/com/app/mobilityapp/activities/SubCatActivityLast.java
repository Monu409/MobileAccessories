package com.app.mobilityapp.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.adapter.SubCatLastAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.CartModel;
import com.app.mobilityapp.modals.ProductsModal;
import com.app.mobilityapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.GET_GLASS_SUBCAT_DATA;

public class SubCatActivityLast extends BaseActivity {
    private RecyclerView sscList;
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();
    String id = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sscList = findViewById(R.id.ssc_list);
        sscList.setLayoutManager(new GridLayoutManager(this, 3));
        CartModel cartModel = (CartModel) getIntent().getSerializableExtra("cart_model_data");
        String name = cartModel.getProductsModal().getName();
        Intent i = getIntent();
        id = i.getStringExtra("prodid");
        Log.e("glasssub1", id);
        ConstantMethods.setTitleAndBack(this, name);
       // Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
    /* for(int i=0;i<20;i++){
          list1.add("Flip"+(i+1));
            list2.add("\u20B9 "+(10+i*2));
          list3.add("About this Flip");
       }*/
        getGlassSubData();


      /*  SubSubCatAdapter subSubCatAdapter = new SubSubCatAdapter(list1,list2,list3,this);
        sscList.setAdapter(subSubCatAdapter);*/
    }
    private void getGlassSubData(){
//        ConstantMethods.showProgressbar(getActivity());
        //DashboardActivity dashboardActivity = new DashboardActivity();
        String catId = id;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("subCategoryId",catId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(GET_GLASS_SUBCAT_DATA, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                List<ProductsModal> productsModals = new ArrayList<>();
                ProductsModal productsModal = null;
//                ConstantMethods.dismissProgressBar();
                Log.e("result",""+response);
                try {
                    String confirmation = response.getString("confirmation");
                    if(confirmation.equals("success")){
                        JSONArray jsonArray = response.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject childObj = jsonArray.getJSONObject(i);
                            String name = childObj.getString("name");
                            String desc = childObj.getString("content");
                            String id=childObj.getString("_id");
                            productsModal = new ProductsModal();
                            productsModal.setName(name);
                            productsModal.setContent(desc);
                            productsModal.setId(id);
                            productsModals.add(productsModal);
                        }
                        SubCatLastAdapter productAdapter = new SubCatLastAdapter(getApplicationContext(),productsModals);
                        sscList.setAdapter(productAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(@NonNull ANError anError) {
//                ConstantMethods.dismissProgressBar();
                Log.e("result",""+anError);
            }
        },this);
    }

    @Override
    protected int getLayoutResourceId () {
        return R.layout.activity_sub_sub_cat;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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

    private void alertDialogForLogout(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Logout");
        builder1.setMessage("Do you want to logout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConstantMethods.setStringPreference("login_status","logout",SubCatActivityLast.this);
                        startActivity(new Intent(SubCatActivityLast.this,LoginActivity.class));
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

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }*/
}
