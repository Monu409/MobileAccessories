package com.app.mobilityapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.androidnetworking.error.ANError;
import com.app.mobilityapp.activities.DashboardActivity;
import com.app.mobilityapp.R;
import com.app.mobilityapp.adapter.ProductAdapter;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.ProductsModal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.GET_GLASS_DATA;

public class GlassFragment extends Fragment {
    private RecyclerView productRcylr;
    String catId;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_glass,container,false);
        productRcylr = view.findViewById(R.id.product_rcylr);
        swipeRefreshLayout = view.findViewById(R.id.pull_to_refresh);
        productRcylr.setLayoutManager(new GridLayoutManager(getActivity(),2));
        productRcylr.setBackgroundColor(Color.parseColor("#FFFFFF"));
        getGlassData();
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getGlassData();
            swipeRefreshLayout.setRefreshing(false);
        });
        return view;
    }

    public GlassFragment(String catId){
        this.catId = catId;
    }

    public GlassFragment(){

    }

    private void getGlassData(){
//        ConstantMethods.showProgressbar(getActivity());
        DashboardActivity dashboardActivity = (DashboardActivity)getActivity();
//        String catId = dashboardActivity.glassCatId;
        JSONObject jsonObject = new JSONObject();
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
                            String subcat=childObj.getString("_id");
                            JSONObject imgUrlObj = childObj.getJSONObject("image");
                            String imgUrlStr = imgUrlObj.getString("imageurl");
                            productsModal = new ProductsModal();
                            productsModal.setName(name);
                            productsModal.setContent(desc);
                            productsModal.setId(subcat);
                            productsModal.setImgUrl(imgUrlStr);
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
//                ConstantMethods.dismissProgressBar();
                Log.e("result",""+anError);
            }
        },getActivity());
    }
}
