package com.app.mobilityapp.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.adapter.ProductNamePriceAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.ProBrndModal;
import com.app.mobilityapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.PRODUCT_DETAIL;
import static com.app.mobilityapp.app_utils.AppApis.SEARCH_DATA;

public class ProductNamePriceActivity extends BaseActivity {
    private RecyclerView brandList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brandList = findViewById(R.id.brand_list);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
//        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            ConstantMethods.setTitleAndBack(this,"All Products");
        }
        brandList.setLayoutManager(new GridLayoutManager(this,2));
        /***Space Between Column**/

        brandList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view); // item position
                int spanCount = 2;
                int spacing = 2;//spacing between views in grid

                if (position >= 0) {
                    int column = position % spanCount; // item column

                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = 0;
                    outRect.right = 0;
                    outRect.top = 0;
                    outRect.bottom = 0;
                }
            }
        });


        /**************/

        ConstantMethods.showProgressbar(this);

        Intent intent = getIntent();


        String cat_id=intent.getStringExtra("cat_id");
        String lastView = getIntent().getStringExtra("last_view");
        JSONObject jsonObject = null;
        String url = "";
        if(lastView.equals("sub_cat")){
            url = PRODUCT_DETAIL;
            jsonObject = new JSONObject();
            try {
                // Log.e("catId",cat_id);
                jsonObject.put("subcategory3",cat_id);
                Log.e("request",jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        if(lastView.equals("search")){
            url = SEARCH_DATA;
            String keyWord = getIntent().getStringExtra("search_key");
            jsonObject = new JSONObject();
            try {
                // Log.e("catId",cat_id);
                jsonObject.put("name",keyWord);
                Log.e("request",jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        CommonNetwork.postNetworkJsonObj(url,jsonObject,new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("response",""+response);
                ConstantMethods.dismissProgressBar();
                List<ProBrndModal> proBrndModals = new ArrayList<>();
                try {
                    String confirmation = response.getString("confirmation");
                    if(confirmation.equals("success")){
                        JSONArray jsonArray = response.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            String content = jsonObject.getString("content");
                            String brandId = jsonObject.getString("_id");
                            ProBrndModal proBrndModal = new ProBrndModal();
                            proBrndModal.setName(name);
                            proBrndModal.setContent(String.valueOf(Html.fromHtml(content)));
                            proBrndModal.setId(brandId);
                            proBrndModals.add(proBrndModal);
                        }
                        ProductNamePriceAdapter productBrandAdapter = new ProductNamePriceAdapter(proBrndModals,ProductNamePriceActivity.this);
                        brandList.setAdapter(productBrandAdapter);
                        productBrandAdapter.onListClick(proBrndModal -> {
                            Log.e("position",""+proBrndModal);
                            Intent intent = new Intent(ProductNamePriceActivity.this, ProductActivity.class);
                            Log.e("brand_name",proBrndModal.getName());
                            Log.e("brand_des",proBrndModal.getContent());

                            intent.putExtra("brand_id",proBrndModal.getId());
                            intent.putExtra("brand_name",proBrndModal.getName());
                            intent.putExtra("brand_des",proBrndModal.getContent());
                            intent.putExtra("brand_img",proBrndModal.getImgUrl());
//                            intent.putExtra("cart_model_data",cartModel);
                            ProductNamePriceActivity.this.startActivity(intent);
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("response",""+anError);
                ConstantMethods.dismissProgressBar();
            }
        },this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_product_brand;
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.action_call:
//                onCallBtnClick();
//                break;
//            case R.id.action_profile:
//                startActivity(new Intent(this, UpdateProfileActivity.class));
//                break;
//            case R.id.logout_menu:
//                alertDialogForLogout();
//                break;
//            case R.id.action_cart:
//                startActivity(new Intent(this, CartChangeActivity.class));
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    private void alertDialogForLogout(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Logout");
        builder1.setMessage("Do you want to logout?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConstantMethods.setStringPreference("login_status","logout",ProductNamePriceActivity.this);
                        startActivity(new Intent(ProductNamePriceActivity.this,LoginActivity.class));
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

