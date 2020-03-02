package com.app.mobilityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.EditCartActivity;
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
    private GetCartID getCartID;

    public CartChangeAdapter(List<CartNewModel.CartChildModel> cartChildModels, Context context,GetCartID getCartID) {
        this.cartChildModels = cartChildModels;
        this.context = context;
        this.getCartID = getCartID;
        layoutInflater = LayoutInflater.from(context);
    }
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
        CartNewModel.Image_ image = cartChildModels.get(position).getCategoryId().getImage();
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
        String cartId = cartChildModels.get(position).getId();
        holder.fullView.setOnClickListener(v->{
            Intent intent = new Intent(context, EditCartActivity.class);
            intent.putExtra("cart_id",cartId);
            context.startActivity(intent);
        });
        holder.removeTxt.setOnClickListener(v->{
            getCartID.getId(cartId);
        });

    }

    @Override
    public int getItemCount() {
        return cartChildModels.size();
    }

    public class CartCHolder extends RecyclerView.ViewHolder {
        public TextView name, qty, priceTxt, catName,removeTxt;
        public CircleImageView itmImg;
        public ImageView editImg;
        RelativeLayout fullView;

        public CartCHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_txt);
            catName = itemView.findViewById(R.id.cat_name_txt);
            priceTxt = itemView.findViewById(R.id.price_txt);
            itmImg = itemView.findViewById(R.id.brand_img);
            fullView = itemView.findViewById(R.id.full_view);
            removeTxt = itemView.findViewById(R.id.remove_txt);
        }
    }

    public interface GetCartID{
        void getId(String cartId);
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
