package com.app.mobilityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.modals.ProductCopyModel;

import java.util.List;

public class ProductPriceCopyAdapter extends RecyclerView.Adapter<ProductPriceCopyAdapter.PPCHolder> {
    private Context context;
    private List<ProductCopyModel.Price> priceList;
    private LayoutInflater layoutInflater;

    public ProductPriceCopyAdapter(Context context, List<ProductCopyModel.Price> priceList){
        this.context = context;
        this.priceList = priceList;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public PPCHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_product_price,null);
        return new PPCHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PPCHolder holder, int position) {
        int fromPrice = priceList.get(position).getFrom();
        int toPrice = priceList.get(position).getTo();
        int amount = priceList.get(position).getAmount();

        holder.from_price.setText(""+fromPrice+" to "+toPrice);
        holder.txt_amount.setText(String.valueOf(amount));
    }

    @Override
    public int getItemCount() {
        return priceList.size();
    }

    public class PPCHolder extends RecyclerView.ViewHolder {
        TextView from_price,to_price,txt_amount;
        public PPCHolder(@NonNull View itemView) {
            super(itemView);
            from_price=itemView.findViewById(R.id.from_price_to_to);
//          to_price=itemView.findViewById(R.id.to_price);
            txt_amount=itemView.findViewById(R.id.txt_amount);
        }
    }
}
