package com.app.mobilityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.SubOrderActivity;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.modals.OrderRcvdModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class OrdrRcvdDtlAdapter extends RecyclerView.Adapter<OrdrRcvdDtlAdapter.ORHolder> {
    private List<OrderRcvdModel.Productdetail> productdetails;
    private Context context;
    private LayoutInflater layoutInflater;

    public OrdrRcvdDtlAdapter(List<OrderRcvdModel.Productdetail> productdetails, Context context){
        this.productdetails = productdetails;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ORHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_order_detail,null);
        return new ORHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ORHolder holder, int position) {
        holder.catName.setVisibility(View.GONE);
        OrderRcvdModel.ProductId productId = productdetails.get(position).getProductId();
        holder.name.setText(productId.getName());
        holder.priceTxt.setText("₹ "+productdetails.get(position).getAmount());
        List<OrderRcvdModel.Image> imageList = productdetails.get(position).getProductId().getImage();
        String imgUrl = imageList.get(0).getImageurl();
        Glide
                .with(context)
                .load(imgUrl)
                .placeholder(R.drawable.test)
                .error(R.drawable.test)
                .centerCrop()
                .into(holder.image);
        holder.fullView.setOnClickListener(v->{
            List<OrderRcvdModel.BrandDetail> brandDetails = productdetails.get(position).getBrandDetails();
            Intent intent = new Intent(context, SubOrderActivity.class);
            intent.putExtra("child_model",(ArrayList)brandDetails);
            intent.putExtra("preview","seller_view");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productdetails.size();
    }

    class ORHolder extends RecyclerView.ViewHolder {
        TextView name,catName,priceTxt;
        CircleImageView image;
        RelativeLayout fullView;
        public ORHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cat_name_txt);
            catName = itemView.findViewById(R.id.name_txt);
            priceTxt = itemView.findViewById(R.id.price_txt);
            image = itemView.findViewById(R.id.brand_img);
            fullView = itemView.findViewById(R.id.full_view);
        }
    }
}
