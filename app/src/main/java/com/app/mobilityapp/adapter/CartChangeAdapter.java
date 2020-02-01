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
import com.app.mobilityapp.modals.ProductPriceModel;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.DELETE_CART;

public class CartChangeAdapter extends RecyclerView.Adapter<CartChangeAdapter.CartCHolder> {
    private List<CartChangeModel> cartChangeModels;
    private Context context;
    private LayoutInflater layoutInflater;

    public CartChangeAdapter(List<CartChangeModel> cartChangeModels, Context context) {
        this.cartChangeModels = cartChangeModels;
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
        String qty = cartChangeModels.get(position).getQuantity();
        holder.name.setText(cartChangeModels.get(position).getBrandName());
        holder.qty.setText(qty);
        holder.catName.setText(cartChangeModels.get(position).getCatName());
        ArrayList<ProductPriceModel> productPriceModels = (ArrayList<ProductPriceModel>) cartChangeModels.get(position).getProductPriceModels();
        holder.priceTxt.setText("₹ " + cartChangeModels.get(position).getTotalPrice());
        Glide
                .with(context)
                .load(cartChangeModels.get(position).getImgUrl())
                .centerCrop()
                .into(holder.itmImg);

        holder.editImg.setOnClickListener(v -> {
            Intent intent = new Intent(context, EnterQuantityActivity.class);
            intent.putExtra("view_name", "cart_unslctd");
            intent.putExtra("qty_arr", cartChangeModels.get(position));
            intent.putExtra("price_list", productPriceModels);
            context.startActivity(intent);
        });
        if(qty.equals("0")) {
            String cartIDWantToDelete = cartChangeModels.get(position).getCartId();
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            try {
                jsonArray.put(0, cartIDWantToDelete);
                jsonObject.put("_id", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            deleteCart(DELETE_CART+jsonObject);
        }
    }

    @Override
    public int getItemCount() {
        return cartChangeModels.size();
    }

    public class CartCHolder extends RecyclerView.ViewHolder {
        public TextView name, qty, priceTxt, catName;
        public CircleImageView itmImg;
        public ImageView editImg;

        public CartCHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_txt);
            catName = itemView.findViewById(R.id.cat_name_txt);
            qty = itemView.findViewById(R.id.qty_txt);
            priceTxt = itemView.findViewById(R.id.price_txt);
            itmImg = itemView.findViewById(R.id.brand_img);
            editImg = itemView.findViewById(R.id.edit_qty);
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
