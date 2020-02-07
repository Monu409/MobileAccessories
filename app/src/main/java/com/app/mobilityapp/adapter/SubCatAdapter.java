package com.app.mobilityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.ProductBrandActivity;
import com.app.mobilityapp.activities.ProductNamePriceActivity;
import com.app.mobilityapp.activities.SubCatActivityLast;
import com.app.mobilityapp.activities.SubSubCatActivity;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.CartModel;
import com.app.mobilityapp.modals.ProductsModal;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.GET_GLASS_SUBCAT_DATA;

public class SubCatAdapter extends RecyclerView.Adapter<SubCatAdapter.ProductHolder> {
    private Context context;
    private List<ProductsModal> productsModals;
    private LayoutInflater layoutInflater;
    CartModel cartModel;
    public SubCatAdapter (Context context, List<ProductsModal> productsModals){
        this.context = context;
        this.productsModals = productsModals;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_product,null);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.nameTxt.setText(productsModals.get(position).getName());
        holder.desTxt.setText(productsModals.get(position).getContent());
        holder.moqTxt.setText("MOQ: "+productsModals.get(position).getMoq());
        Glide
                .with(context)
                .load(productsModals.get(position).getImgUrl())
                .centerCrop()
                .into(holder.subCatImg);
        holder.fullView.setOnClickListener(v->{
            cartModel = new CartModel();
            cartModel.setProductsModal(productsModals.get(position));
            getGlassSubData(cartModel.getProductsModal().getId());

                 });
    }

    @Override
    public int getItemCount() {
        return productsModals.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        public TextView nameTxt,desTxt,moqTxt;
        public RelativeLayout fullView;
        public ImageView subCatImg;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name_txt);
            desTxt = itemView.findViewById(R.id.des_txt);
            fullView = itemView.findViewById(R.id.full_view);
            moqTxt = itemView.findViewById(R.id.moq_txt);
            subCatImg = itemView.findViewById(R.id.sub_cat_img);
        }
    }
    private void getGlassSubData(String id){
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
                        if(jsonArray.length()>0) {
                            Intent intent = new Intent(context, SubCatActivityLast.class);
                            intent.putExtra("cart_model_data", cartModel);
                            intent.putExtra("prodid", cartModel.getProductsModal().getId());
                            Log.e("subcatid", cartModel.getProductsModal().getId());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            context.startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(context, ProductNamePriceActivity.class);
                            intent.putExtra("last_view","sub_cat");
                            intent.putExtra("cat_name",cartModel.getProductsModal().getName());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("result",""+anError);
            }
        },context);
    }

}

