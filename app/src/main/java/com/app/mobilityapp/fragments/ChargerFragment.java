package com.app.mobilityapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.DashboardActivity;
import com.app.mobilityapp.adapter.ProductAdapter;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.ProductsModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.GET_GLASS_DATA;

public class ChargerFragment extends Fragment {
    private RecyclerView productRcylr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_charger,container,false);
        productRcylr = view.findViewById(R.id.product_rcylr);
        productRcylr.setLayoutManager(new GridLayoutManager(getActivity(),2));
        getGlassData();
        return view;
    }

    private void getGlassData(){
        ConstantMethods.showProgressbar(getActivity());
        JSONObject jsonObject = new JSONObject();
        DashboardActivity dashboardActivity = (DashboardActivity)getActivity();
        String catId = dashboardActivity.chargerCatId;
        try {
            jsonObject.put("categoryId",catId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(GET_GLASS_DATA, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                List<ProductsModal> productsModals = new ArrayList<>();
                ProductsModal productsModal = null;
                ConstantMethods.dismissProgressBar();
                Log.e("result",""+response);
                try {
                    String confirmation = response.getString("confirmation");
                    if(confirmation.equals("success")){
                        JSONArray jsonArray = response.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject childObj = jsonArray.getJSONObject(i);
                            String name = childObj.getString("name");
                            String desc = childObj.getString("content");
                            productsModal = new ProductsModal();
                            productsModal.setName(name);
                            productsModal.setContent(desc);
                            productsModals.add(productsModal);
                        }
                        ProductAdapter productAdapter = new ProductAdapter(getActivity(),productsModals);
                        productRcylr.setAdapter(productAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                ConstantMethods.dismissProgressBar();
                Log.e("result",""+anError);
            }
        },getActivity());
    }
}
