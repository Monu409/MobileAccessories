package com.app.mobilityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.mobilityapp.R;
import com.app.mobilityapp.modals.CheckoutModel;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutHolder> {
    private List<CheckoutModel> checkoutModels;
    private Context context;
    private LayoutInflater layoutInflater;

    public CheckoutAdapter(List<CheckoutModel> checkoutModels, Context context){
        this.checkoutModels = checkoutModels;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public CheckoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_checkout,null);
        return new CheckoutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return checkoutModels.size();
    }

    class CheckoutHolder extends RecyclerView.ViewHolder {
        TextView nameTxt,multipleTxt,totalTxt;
        public CheckoutHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name_txt);
            multipleTxt = itemView.findViewById(R.id.multiple_txt);
            totalTxt = itemView.findViewById(R.id.total_txt);
        }
    }
}
