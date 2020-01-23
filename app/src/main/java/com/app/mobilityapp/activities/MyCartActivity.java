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
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.adapter.CartAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.modals.CartModel;
import com.app.mobilityapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyCartActivity extends BaseActivity {
    private RecyclerView cartList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Cart");
        cartList = findViewById(R.id.cart_list);
        cartList.setLayoutManager(new LinearLayoutManager(this));
        List<CartModel> cartModels = ConstantMethods.getArrayListShared(this,"saved_cart_modal");
        if(cartModels==null){
            cartModels = new ArrayList<>();
        }
//        List<TestCartModel> cartModels = new ArrayList<>();
//        for(int i=0;i<2;i++){
//            TestCartModel cartModel = new TestCartModel();
//            cartModel.setName("Test1");
//            cartModel.setDes("des1\ndes222");
//            cartModels.add(cartModel);
//        }
        CartAdapter cartAdapter = new CartAdapter(cartModels,this);
        cartList.setAdapter(cartAdapter);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_my_cart;
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
                        ConstantMethods.setStringPreference("login_status","logout",MyCartActivity.this);
                        startActivity(new Intent(MyCartActivity.this,LoginActivity.class));
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
}
