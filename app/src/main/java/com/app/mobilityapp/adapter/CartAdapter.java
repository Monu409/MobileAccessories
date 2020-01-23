package com.app.mobilityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.modals.CartModel;
import com.app.mobilityapp.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private List<CartModel> cartModels;
    private Context context;
    private LayoutInflater layoutInflater;

    public CartAdapter(List<CartModel> cartModels, Context context){
        this.cartModels = cartModels;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_mycart,null);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        holder.nameTxt.setText(cartModels.get(position).getProBrndModal().getName());
        holder.desTxt.setText(cartModels.get(position).getProductsModal().getName());
        holder.plusImg.setOnClickListener(v->{
            String qtyStr = holder.qtyTxt.getText().toString();
            int qtyInt = Integer.parseInt(qtyStr);
            qtyInt = qtyInt+1;
            holder.qtyTxt.setText(String.valueOf(qtyInt));
        });
        holder.minusImg.setOnClickListener(v->{
            String qtyStr = holder.qtyTxt.getText().toString();
            int qtyInt = Integer.parseInt(qtyStr);
            if(qtyInt>1){
                qtyInt = qtyInt-1;
                holder.qtyTxt.setText(String.valueOf(qtyInt));
            }
            else {
                cartModels.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartModels.size());
                //setInterestValues(catKey);
                ConstantMethods.saveArrayListShared(cartModels,context,"saved_cart_modal");
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        public TextView nameTxt,desTxt,qtyTxt;
        public ImageView plusImg,minusImg;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name_txt);
            desTxt = itemView.findViewById(R.id.des_txt);
            qtyTxt = itemView.findViewById(R.id.qnty_txt);
            plusImg = itemView.findViewById(R.id.plus_btn);
            minusImg = itemView.findViewById(R.id.minus_btn);
        }
    }
}
