package com.app.mobilityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.ProductBrandActivity;
import com.app.mobilityapp.activities.ProductNamePriceActivity;
import com.app.mobilityapp.activities.SubCatActivityLast;
import com.app.mobilityapp.activities.SubSubCatActivity;
import com.app.mobilityapp.modals.CartModel;
import com.app.mobilityapp.modals.ProductsModal;

import java.util.List;

public class SubCatLastAdapter extends RecyclerView.Adapter<SubCatLastAdapter.ProductHolder> {
    private Context context;
    private List<ProductsModal> productsModals;
    private LayoutInflater layoutInflater;

    public SubCatLastAdapter (Context context, List<ProductsModal> productsModals){
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
       /* holder.fullView.setOnClickListener(v->{
            CartModel cartModel = new CartModel();
            cartModel.setProductsModal(productsModals.get(position));
            Intent intent = new Intent(context, SubCatActivityLast.class);
            intent.putExtra("cart_model_data",cartModel);
            intent.putExtra("prodid",cartModel.getProductsModal().getId());
            Log.e("subcatid",cartModel.getProductsModal().getId());
          //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        });*/
        holder.fullView.setOnClickListener(v->{

            CartModel cartModel = new CartModel();
            cartModel.setProductsModal(productsModals.get(position));
          /*  Intent intent = new Intent(context, SubCatActivityLast.class);
            intent.putExtra("cart_model_data",cartModel);
            intent.putExtra("prodid",cartModel.getProductsModal().getId());*/
            Log.e("subcatid",cartModel.getProductsModal().getId());




            Intent intent = new Intent(context, ProductNamePriceActivity.class);
            intent.putExtra("last_view","sub_cat");
            intent.putExtra("cat_id",cartModel.getProductsModal().getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productsModals.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        public TextView nameTxt,desTxt;
        public LinearLayout fullView;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name_txt);
            desTxt = itemView.findViewById(R.id.des_txt);
            fullView = itemView.findViewById(R.id.full_view);
        }
    }
}

