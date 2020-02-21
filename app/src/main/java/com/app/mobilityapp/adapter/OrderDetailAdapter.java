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
import com.app.mobilityapp.modals.OrderDetailModel;
import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ODHolder> {
    private List<OrderDetailModel.Productdetail> productdetails;
    private Context context;
    private LayoutInflater layoutInflater;

    public OrderDetailAdapter(List<OrderDetailModel.Productdetail> productdetails, Context context){
        this.productdetails = productdetails;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ODHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_order_detail,null);
        return new ODHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ODHolder holder, int position) {
        OrderDetailModel.ProductId productId = productdetails.get(position).getProductId();
        int price = productdetails.get(position).getPrice();
        OrderDetailModel.CategoryId categoryId = productdetails.get(position).getCategoryId();
        List<OrderDetailModel.BrandDetail_> brandDetail1 = productdetails.get(position).getBrandDetails();
        String name = productId.getName();
        String subName = categoryId.getName();
        holder.nameTxt.setText(name);
        holder.subNameTxt.setText(subName);
        holder.priceTxt.setText("₹ "+price);
        holder.fullView.setOnClickListener(v->{
            Intent intent = new Intent(context, SubOrderActivity.class);
            intent.putExtra("data",brandDetail1.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return productdetails.size();
    }

    public class ODHolder extends RecyclerView.ViewHolder {
        public CircleImageView catImg;
        public TextView nameTxt,subNameTxt,priceTxt;
        RelativeLayout fullView;
        public ODHolder(@NonNull View itemView) {
            super(itemView);
            catImg = itemView.findViewById(R.id.brand_img);
            nameTxt = itemView.findViewById(R.id.cat_name_txt);
            subNameTxt = itemView.findViewById(R.id.name_txt);
            priceTxt = itemView.findViewById(R.id.price_txt);
            fullView = itemView.findViewById(R.id.full_view);
        }
    }
}
