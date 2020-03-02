package com.app.mobilityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.modals.MyProductModel;

import java.util.List;

public class ShowMyProdPriceAdapter extends RecyclerView.Adapter<ShowMyProdPriceAdapter.SMPHolder> {
    private List<MyProductModel.Price> priceList;
    private Context context;
    private LayoutInflater layoutInflater;

    public ShowMyProdPriceAdapter(List<MyProductModel.Price> priceList, Context context){
        this.priceList = priceList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public SMPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_my_prod_price,null);
        return new SMPHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SMPHolder holder, int position) {
        String fromPrice = priceList.get(position).getFrom();
        String toPrice = priceList.get(position).getTo();
        String totalPrice = priceList.get(position).getAmount();
        holder.fromToPrice.setText(fromPrice+" to "+toPrice);
        holder.priceTxt.setText("₹ "+totalPrice);
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public class SMPHolder extends RecyclerView.ViewHolder {
        TextView fromToPrice,priceTxt;
        public SMPHolder(@NonNull View itemView) {
            super(itemView);
            fromToPrice = itemView.findViewById(R.id.from_price_to_to);
            priceTxt = itemView.findViewById(R.id.txt_amount);
        }
    }
}
