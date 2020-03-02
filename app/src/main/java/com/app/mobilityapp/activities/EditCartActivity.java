package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.EditCartAdapter;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.EditCartModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;


import static com.app.mobilityapp.app_utils.AppApis.ADD_INTO_CART;
import static com.app.mobilityapp.app_utils.AppApis.DELETE_BRANDS;

public class EditCartActivity extends BaseActivity implements EditCartAdapter.GetSubCartCatId {
    private RecyclerView cartChildList;
    String cartId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstantMethods.setTitleAndBack(this,"Edit Cart");
        cartId = getIntent().getStringExtra("cart_id");
        cartChildList = findViewById(R.id.edit_cart_list);
        cartChildList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_edit_cart;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getExploreCartData(cartId);
    }

    private void getExploreCartData(String cartId){
        ConstantMethods.showProgressbar(this);
        CommonNetwork.getNetworkJsonObj(ADD_INTO_CART + "/" + cartId, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                ConstantMethods.dismissProgressBar();
                Gson gson = new Gson();
                EditCartModel editCartModel = gson.fromJson(String.valueOf(response),EditCartModel.class);
                String confirmation = editCartModel.getConfirmation();
                if(confirmation.equals("success")){
                    EditCartAdapter editCartAdapter = new EditCartAdapter(EditCartActivity.this,editCartModel,cartId,EditCartActivity.this);
                    cartChildList.setAdapter(editCartAdapter);
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Toast.makeText(EditCartActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        },this);
    }

    @Override
    public void getSubCartId(String subCartId, String cartId) {
        alertDialogForLogout(subCartId,cartId);
    }

    private void deleteSubItems(String subCartId, String cartId){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cartId",cartId);
            jsonObject.put("arrayId",subCartId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(DELETE_BRANDS, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("response",""+response);
                String confirmation = null;
                try {
                    confirmation = response.getString("confirmation");
                    if (confirmation.equals("success")){
                        Toast.makeText(EditCartActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                        getExploreCartData(cartId);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("response",""+anError);
            }
        },this);
    }

    private void alertDialogForLogout(String subCartId, String cartId){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        builder1.setTitle("Delete Item");
        builder1.setMessage("Do you want to Delete this item");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                (dialog, id) -> {
                    deleteSubItems(subCartId,cartId);
                });

        builder1.setNegativeButton(
                "No",
                (dialog, id) -> dialog.cancel());

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
