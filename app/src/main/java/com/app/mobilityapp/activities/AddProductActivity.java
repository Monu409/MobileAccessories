package com.app.mobilityapp.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.BaseActivity;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.ProductsModal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import static com.app.mobilityapp.app_utils.AppApis.GET_BRAND;

public class AddProductActivity extends BaseActivity {
    private Spinner catSpinner,subCatSpnr1,subCatSpnr2,subCatSpnr3,subCatSpnr4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catSpinner = findViewById(R.id.cat_spinner);
        subCatSpnr1 = findViewById(R.id.sub_cat_spinner1);
        subCatSpnr2 = findViewById(R.id.sub_cat_spinner2);
        subCatSpnr3 = findViewById(R.id.sub_cat_spinner3);
        subCatSpnr4 = findViewById(R.id.sub_cat_spinner4);
        getBrand();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_product;
    }

    private void getBrand(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("isDeleted","false");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(GET_BRAND, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                try {
                    String confirmation = response.getString("confirmation");
                    if(confirmation.equals("success")){
                        JSONArray jsonArray = response.getJSONArray("data");
                        List<ProductsModal> productModels = new ArrayList<>();
                        for(int i=0;i<jsonArray.length();i++){
                            ProductsModal productModel = new ProductsModal();
                            JSONObject childObj = jsonArray.getJSONObject(i);
                            String name = childObj.getString("name");
                            productModel.setName(name);
                            productModels.add(productModel);
                        }
                        ArrayAdapter aa = new ArrayAdapter(AddProductActivity.this,android.R.layout.simple_spinner_item,productModels);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        catSpinner.setAdapter(aa);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {

            }
        },this);
    }
    private void getCategory(){

    }
    private void getColor(){

    }
}
