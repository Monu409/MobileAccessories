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
import com.app.mobilityapp.modals.OrderDetailModel;
import com.app.mobilityapp.modals.OrderRcvdModel;

import java.util.List;

public class OrderDetailBrandAdapter extends RecyclerView.Adapter<OrderDetailBrandAdapter.ODBHolder> {
    private List<OrderDetailModel.Modallist> modallists;
    private Context context;
    private LayoutInflater layoutInflater;
    List<OrderRcvdModel.Modallist> sModallists;
    private String s = "";

    public OrderDetailBrandAdapter(List<OrderDetailModel.Modallist> modallists, Context context){
        this.modallists = modallists;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public OrderDetailBrandAdapter(List<OrderRcvdModel.Modallist> sModallists, Context context,String s){
        this.sModallists = sModallists;
        this.context = context;
        this.s = s;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ODBHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_order_detail_brands,null);
        return new ODBHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ODBHolder holder, int position) {
        if(s.equals("")) {
            int qtyInt = modallists.get(position).getQuantity();
            String name = modallists.get(position).getModalid().getName();
            holder.modelName.setText(name);
            holder.qty.setText(String.valueOf(qtyInt));
            if (qtyInt == 0) {
                holder.fullView.setVisibility(View.GONE);
            }
        }
        else {
            int qtyInt = sModallists.get(position).getQuantity();
            String name = sModallists.get(position).getModalid().getName();
            holder.modelName.setText(name);
            holder.qty.setText(String.valueOf(qtyInt));
            if (qtyInt == 0) {
                holder.fullView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if(s.equals("")) {
            return modallists.size();
        }
        else {
            return sModallists.size();
        }
    }

    public class ODBHolder extends RecyclerView.ViewHolder {
        public TextView modelName,qty;
        RelativeLayout fullView;
        public ODBHolder(@NonNull View itemView) {
            super(itemView);
            modelName = itemView.findViewById(R.id.brand_name);
            qty = itemView.findViewById(R.id.brand_qty);
            fullView = itemView.findViewById(R.id.full_view);
        }
    }
}
