package com.app.mobilityapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.modals.ProBrndModal;
import com.app.mobilityapp.modals.ProductPriceModel;

import java.util.List;

public class ProductPriceListAdapter  extends RecyclerView.Adapter<ProductPriceListAdapter.ProdBrndHolder> {
    private List<ProductPriceModel> proBrndModals;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnListClick onListClick;
    private int row_index=-1,focusedItem=0;

    public ProductPriceListAdapter(List<ProductPriceModel> proBrndModals, Context context){
        this.proBrndModals = proBrndModals;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void onListClick(OnListClick onListClick){
        this.onListClick = onListClick;
    }
    @NonNull
    @Override
    public ProdBrndHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_product_price,null);
        return new ProdBrndHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdBrndHolder holder, int position) {
        String to_price = proBrndModals.get(position).getTo();
        String from_price=proBrndModals.get(position).getFrom();
        String amount=proBrndModals.get(position).getAmount();
        holder.from_price.setText(from_price+" to "+to_price);
        holder.txt_amount.setText("₹ "+amount);
    }



    @Override
    public int getItemCount() {
        return proBrndModals.size();
    }

    public class ProdBrndHolder extends RecyclerView.ViewHolder{
      TextView from_price,to_price,txt_amount;
        public ProdBrndHolder(@NonNull View itemView) {
            super(itemView);
          from_price=itemView.findViewById(R.id.from_price_to_to);
//          to_price=itemView.findViewById(R.id.to_price);
          txt_amount=itemView.findViewById(R.id.txt_amount);
        }
    }
    public interface OnListClick{
        void getPosition(ProductPriceModel proBrndModal);
    }
}


