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
import com.app.mobilityapp.activities.OrderDetailModelActivity;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.modals.OrderDetailModel;
import com.app.mobilityapp.modals.OrderRcvdModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SubOrderDetailAdapter extends RecyclerView.Adapter<SubOrderDetailAdapter.SODHolder> {
    private List<OrderDetailModel.BrandDetail_> modallistList;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<OrderRcvdModel.OrderRcvdModelChild> orderRcvdModelChildren;
    private String seller;
    private String seller1;

    public SubOrderDetailAdapter(List<OrderDetailModel.BrandDetail_> modallistList, Context context,String seller,String seller1){
        this.modallistList = modallistList;
        this.context = context;
        this.seller = seller;
        this.seller1 = seller1;
        layoutInflater = LayoutInflater.from(context);
    }

    public SubOrderDetailAdapter(List<OrderRcvdModel.OrderRcvdModelChild> orderRcvdModelChildren, Context context,String seller){
        this.orderRcvdModelChildren = orderRcvdModelChildren;
        this.context = context;
        this.seller = seller;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public SODHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_sub_order_detail,null);
        return new SODHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SODHolder holder, int position) {
        if(seller.equals("")) {
            OrderDetailModel.Brand brand = modallistList.get(position).getBrand();
            String brandName = brand.getName();
            OrderDetailModel.Image_ image = modallistList.get(position).getBrand().getImage();
            String imgStr = image.getImageurl();
            List<OrderDetailModel.Modallist> modallist = modallistList.get(position).getModallist();
            int qtySum = 0;
            for (int i = 0; i < modallist.size(); i++) {
                int qty = modallist.get(i).getQuantity();
                qtySum = qtySum + qty;
            }
            holder.name.setText(brandName);
            holder.qty.setText("Qty: " + qtySum);
            Glide
                    .with(context)
                    .load(imgStr)
                    .placeholder(R.drawable.test)
                    .centerCrop()
                    .into(holder.img);
            holder.fullView.setOnClickListener(v->{
                Intent intent = new Intent(context, OrderDetailModelActivity.class);
                intent.putExtra("model_list",(ArrayList)modallist);
                intent.putExtra("pr_ac","buyer");
                context.startActivity(intent);
            });
        }
        else{
            OrderRcvdModel.ProductId productId = orderRcvdModelChildren.get(position).getProductId();
            String name = productId.getName();
            String imgUrl = "";
            List<OrderRcvdModel.Image> image = orderRcvdModelChildren.get(position).getProductId().getImage();
            if(image.size()!=0){
                imgUrl = image.get(0).getImageurl();
            }
            List<OrderRcvdModel.Modallist> modallist = orderRcvdModelChildren.get(position).getBrandDetails().get(0).getModallist();
            int sumQty = 0;
            for(int i=0;i<modallist.size();i++){
                int qty = modallist.get(i).getQuantity();
                sumQty = sumQty+qty;
            }
            holder.name.setText(name);
            holder.qty.setText("Qty: " + sumQty);
            Glide
                    .with(context)
                    .load(imgUrl)
                    .placeholder(R.drawable.test)
                    .error(R.drawable.test)
                    .centerCrop()
                    .into(holder.img);
            holder.fullView.setOnClickListener(v->{
                Intent intent = new Intent(context, OrderDetailModelActivity.class);
                intent.putExtra("model_list",(ArrayList)modallist);
                intent.putExtra("pr_ac","seller");
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        if(seller.equals("")){
            return modallistList.size();
        }
        else {
            return orderRcvdModelChildren.size();
        }
    }

    public class SODHolder extends RecyclerView.ViewHolder {
        TextView name,qty;
        CircleImageView img;
        RelativeLayout fullView;
        public SODHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cat_name_txt);
            qty = itemView.findViewById(R.id.qty_txt);
            img = itemView.findViewById(R.id.brand_img);
            fullView = itemView.findViewById(R.id.full_view);
        }
    }
}
