package com.app.mobilityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.ProductBrandActivity;
import com.app.mobilityapp.activities.ProductNamePriceActivity;
import com.app.mobilityapp.activities.SubSubCatActivity;
import com.app.mobilityapp.app_utils.CircleImageView;
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

import static com.app.mobilityapp.app_utils.AppApis.GET_GLASS_CAT_DATA;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private Context context;
    private List<ProductsModal> productsModals;
    private LayoutInflater layoutInflater;
    CartModel cartModel;

    public ProductAdapter(Context context, List<ProductsModal> productsModals) {
        this.context = context;
        this.productsModals = productsModals;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_cat, null);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.nameTxt.setText(productsModals.get(position).getName());
        //holder.desTxt.setText(productsModals.get(position).getContent());
        Glide
                .with(context)
                .load(productsModals.get(position).getImgUrl())
                .placeholder(R.drawable.test)
                .centerCrop()
                .into(holder.catImg);
        holder.fullView.setOnClickListener(v -> {
            cartModel = new CartModel();
            cartModel.setProductsModal(productsModals.get(position));
            getGlassCatData(cartModel.getProductsModal().getId());
            Log.e("position",productsModals.get(position).toString());
        });
    }

    @Override
    public int getItemCount() {
        return productsModals.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTxt, desTxt;
        public LinearLayout fullView;
        public CircleImageView catImg;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name_txt);
            catImg = itemView.findViewById(R.id.cat_img);
            //desTxt = itemView.findViewById(R.id.des_txt);
            fullView = itemView.findViewById(R.id.full_view);
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            Toast.makeText(context, String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
        }
    }

    private void getGlassCatData(String id) {
//        ConstantMethods.showProgressbar(getActivity());
        //DashboardActivity dashboardActivity = new DashboardActivity();
        String catId = id;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("subCategoryId", catId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.postNetworkJsonObj(GET_GLASS_CAT_DATA, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                List<ProductsModal> productsModals = new ArrayList<>();
                ProductsModal productsModal = null;
//                ConstantMethods.dismissProgressBar();
                Log.e("result", "" + response);
                try {
                    String confirmation = response.getString("confirmation");
                    if (confirmation.equals("success")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        if (jsonArray.length() > 0) {
                            Intent intent = new Intent(context, SubSubCatActivity.class);
                            intent.putExtra("cart_model_data", cartModel);
                            intent.putExtra("Id", cartModel.getProductsModal().getId());
                            context.startActivity(intent);
                        } else {   // Toast.makeText(context,"Sorry No Product Found",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(context, ProductNamePriceActivity.class);
                            intent.putExtra("last_view","sub_cat");
                            intent.putExtra("layer","one");
                            intent.putExtra("cat_id",cartModel.getProductsModal().getId());
                            intent.putExtra("cat_name",cartModel.getProductsModal().getName());
                            context.startActivity(intent);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void notifyError(@NonNull ANError anError) {
//                ConstantMethods.dismissProgressBar();
                Log.e("result", "" + anError);
            }
        },context);
    }

}
