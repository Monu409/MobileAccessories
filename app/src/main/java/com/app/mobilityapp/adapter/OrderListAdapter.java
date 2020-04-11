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
import com.app.mobilityapp.activities.OrderDetailActivity;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.modals.MyOrderModel;
import com.app.mobilityapp.modals.OrderListModel;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderHolder> {
    private List<MyOrderModel.MyOrderChildModel> myOrderChildModels;
    private Context context;
    private LayoutInflater layoutInflater;

    public OrderListAdapter(List<MyOrderModel.MyOrderChildModel> myOrderChildModels, Context context){
        this.myOrderChildModels = myOrderChildModels;
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
        String orderId = myOrderChildModels.get(position).getOrderId();
        double amount = myOrderChildModels.get(position).getAmount();
        String dateTime = myOrderChildModels.get(position).getCreatedAt();
        String showDate = ConstantMethods.changeDateFormate(dateTime);
        List<MyOrderModel.Productdetail> productdetail = myOrderChildModels.get(position).getProductdetails();
        holder.dateTxt.setText(showDate);
        holder.orderIdTxt.setText("OrderId: "+orderId);
        holder.priceTxt.setText("₹ "+amount);
        holder.fullView.setOnClickListener(v->{
            String orderIdUnSeen = myOrderChildModels.get(position).getId();
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("order_id", orderIdUnSeen);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return myOrderChildModels.size();
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
