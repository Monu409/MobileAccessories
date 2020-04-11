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
import com.app.mobilityapp.activities.RcvdOrdrDetailActivity;
import com.app.mobilityapp.activities.SubOrderActivity;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.modals.MyOrderModel;
import com.app.mobilityapp.modals.OrderRcvdModel;

import java.util.ArrayList;
import java.util.List;

public class OrdrRcvdAdapter extends RecyclerView.Adapter<OrdrRcvdAdapter.ORHolder> {
    private List<OrderRcvdModel.OrderRcvdModelChild> orderRcvdModelChildren;
    private Context context;
    private LayoutInflater layoutInflater;

    public OrdrRcvdAdapter(List<OrderRcvdModel.OrderRcvdModelChild> orderRcvdModelChildren, Context context){
        this.orderRcvdModelChildren = orderRcvdModelChildren;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ORHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_order_received,null);
        return new ORHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ORHolder holder, int position) {
        OrderRcvdModel.OrderId orderIdObj = orderRcvdModelChildren.get(position).getOrderId();
        String orderIdStr = orderIdObj.getOrderId();
        List<OrderRcvdModel.Productdetail> productdetail = orderRcvdModelChildren.get(position).getProductdetails();
        int amountSum = 0;
        for(int i=0;i<productdetail.size();i++){
            int amount = productdetail.get(i).getAmount();
            amountSum = amountSum+amount;
        }

        String date = orderRcvdModelChildren.get(position).getCreatedAt();
        String showDate = ConstantMethods.changeDateFormate(date);
        holder.orderIdTxt.setText(orderIdStr);
        holder.priceTxt.setText("₹ "+amountSum);
        holder.dateTxt.setText(showDate);
        holder.fullView.setOnClickListener(v->{
            Intent intent = new Intent(context, RcvdOrdrDetailActivity.class);
            intent.putExtra("product_details",(ArrayList)productdetail);
            intent.putExtra("preview","seller_view");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderRcvdModelChildren.size();
    }

    public class ORHolder extends RecyclerView.ViewHolder {
        public TextView priceTxt,dateTxt,orderIdTxt;
        public RelativeLayout fullView;
        public ORHolder(@NonNull View itemView) {
            super(itemView);
            priceTxt = itemView.findViewById(R.id.price_txt);
            dateTxt = itemView.findViewById(R.id.date_txt);
            orderIdTxt = itemView.findViewById(R.id.order_id_txt);
            fullView = itemView.findViewById(R.id.full_view);
        }
    }
}
