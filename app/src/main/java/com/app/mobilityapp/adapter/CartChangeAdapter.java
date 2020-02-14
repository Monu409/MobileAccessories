package com.app.mobilityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.EnterQuantityActivity;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.CartChangeModel;
import com.app.mobilityapp.modals.CartNewModel;
import com.app.mobilityapp.modals.ProductPriceModel;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.DELETE_CART;

public class CartChangeAdapter extends RecyclerView.Adapter<CartChangeAdapter.CartCHolder> {
    private List<CartNewModel.CartChildModel> cartChildModels;
    private Context context;
    private LayoutInflater layoutInflater;

    public CartChangeAdapter(List<CartNewModel.CartChildModel> cartChildModels, Context context) {
        this.cartChildModels = cartChildModels;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CartCHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_change_cart, parent, false);
        return new CartCHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartCHolder holder, int position) {

        CartNewModel.Productid productid = cartChildModels.get(position).getProductid();
        String proName = productid.getName();
        CartNewModel.CategoryId categoryId = cartChildModels.get(position).getCategoryId();
        String catName = categoryId.getName();
        CartNewModel.Image image = cartChildModels.get(position).getCategoryId().getImage();
        String imgUrl = image.getImageurl();
        int price = cartChildModels.get(position).getPrice();
        holder.catName.setText(proName);
        holder.name.setText(catName);
        holder.priceTxt.setText("₹ "+price);
        Glide
                .with(context)
                .load(imgUrl)
                .centerCrop()
                .into(holder.itmImg);
    }

    @Override
    public int getItemCount() {
        return cartChildModels.size();
    }

    public class CartCHolder extends RecyclerView.ViewHolder {
        public TextView name, qty, priceTxt, catName;
        public CircleImageView itmImg;
        public ImageView editImg;

        public CartCHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_txt);
            catName = itemView.findViewById(R.id.cat_name_txt);
//            qty = itemView.findViewById(R.id.qty_txt);
            priceTxt = itemView.findViewById(R.id.price_txt);
            itmImg = itemView.findViewById(R.id.brand_img);
//            editImg = itemView.findViewById(R.id.edit_qty);
        }
    }

    private void deleteCart(String url){
        CommonNetwork.deleteNetworkJsonObj(url, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("res",""+response);
                try {
                    String confirmation = response.getString("confirmation");
//                    if(confirmation.equals("success")){
//
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("res",""+anError);
            }
        },context);
    }
}
