package com.app.mobilityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.modals.OrderListModel;
import com.bumptech.glide.Glide;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderHolder> {
    private List<OrderListModel> orderListModels;
    private Context context;
    private LayoutInflater layoutInflater;

    public OrderListAdapter(List<OrderListModel> orderListModels, Context context){
        this.orderListModels = orderListModels;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_order_list,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        holder.name.setText(orderListModels.get(position).getBrandName());
        holder.catNameTxt.setText(orderListModels.get(position).getCatName());
        holder.dateTxt.setText(orderListModels.get(position).getDateStr());
        holder.orderIdTxt.setText(orderListModels.get(position).getOrderId());
        holder.qty.setText("Total items: "+orderListModels.get(position).getQuantity());
        holder.priceTxt.setText("₹ "+orderListModels.get(position).getTotalPrice());
        Glide
                .with(context)
                .load(orderListModels.get(position).getImgUrl())
                .centerCrop()
                .into(holder.itmImg);
    }

    @Override
    public int getItemCount() {
        return orderListModels.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        public TextView name,qty,priceTxt,catNameTxt,dateTxt,orderIdTxt;
        public CircleImageView itmImg;
        RelativeLayout fullView;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_txt);
            qty = itemView.findViewById(R.id.qty_txt);
            priceTxt = itemView.findViewById(R.id.price_txt);
            itmImg = itemView.findViewById(R.id.brand_img);
            fullView = itemView.findViewById(R.id.full_view);
            catNameTxt = itemView.findViewById(R.id.cat_name_txt);
            dateTxt = itemView.findViewById(R.id.date_txt);
            orderIdTxt = itemView.findViewById(R.id.order_id_txt);
        }
    }
}
