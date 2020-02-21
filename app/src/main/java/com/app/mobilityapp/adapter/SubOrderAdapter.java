package com.app.mobilityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.modals.EditCartModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class SubOrderAdapter extends RecyclerView.Adapter<SubOrderAdapter.ECHolder>{
    private Context context;
    private EditCartModel editCartModel;
    private LayoutInflater layoutInflater;

    public SubOrderAdapter(Context context, EditCartModel editCartModel){
        this.context = context;
        this.editCartModel = editCartModel;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ECHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_sub_order,null);
        return new ECHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ECHolder holder, int position) {
        List<EditCartModel.BrandDetail> brandDetail = editCartModel.getData().getBrandDetails();
        String catName = brandDetail.get(position).getBrand().getName();
        String imageUrl = brandDetail.get(position).getBrand().getImgUrl();
        List<EditCartModel.Modallist> modallists = brandDetail.get(position).getModallist();
        int qtySum = 0;
        for(int i=0;i<modallists.size();i++){
            int qty = modallists.get(i).getQuantity();
            qtySum = qtySum+qty;
        }
        holder.catName.setText(catName);
        holder.quantity.setText("Qty: "+qtySum);
        Glide
                .with(context)
                .load(imageUrl)
                .placeholder(R.drawable.test)
                .centerCrop()
                .into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        List<EditCartModel.BrandDetail> brandDetail = editCartModel.getData().getBrandDetails();
        return brandDetail.size();
    }

    public class ECHolder extends RecyclerView.ViewHolder{
        TextView catName,productName,quantity;
        ImageView editCart;
        CircleImageView circleImageView;
        public ECHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.cat_name_txt);
            productName = itemView.findViewById(R.id.name_txt);
            quantity = itemView.findViewById(R.id.qty_txt);
            editCart = itemView.findViewById(R.id.edit_qty);
            circleImageView = itemView.findViewById(R.id.brand_img);
        }
    }
}