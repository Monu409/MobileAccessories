package com.app.mobilityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.ProductBrandActivity;
import com.app.mobilityapp.activities.ProductNamePriceActivity;

import java.util.List;

public class SubSubCatAdapter extends RecyclerView.Adapter<SubSubCatAdapter.SSCHolder> {
    private List<String> nameList,priceList,desList;
    private Context context;
    private LayoutInflater layoutInflater;

    public SubSubCatAdapter(List<String> nameList,List<String> priceList,List<String> desList, Context context){
        this.nameList = nameList;
        this.priceList = priceList;
        this.desList = desList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public SSCHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = layoutInflater.inflate(R.layout.adapter_sub_sub_cat,null);
        return new SSCHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull SSCHolder holder, int position) {
        holder.nameTxt.setText(nameList.get(position));
        holder.priceTxt.setText(priceList.get(position));
        holder.desTxt.setText(desList.get(position));
        holder.fullView.setOnClickListener(v->{
         /*   Intent intent = new Intent(context, ProductBrandActivity.class);
            context.startActivity(intent);*/
            Intent intent = new Intent(context, ProductNamePriceActivity.class);
            intent.putExtra("last_view","sub_cat");
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class SSCHolder extends RecyclerView.ViewHolder {
        private TextView nameTxt,priceTxt,desTxt;
        private LinearLayout fullView;
        public SSCHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.ss_name);
            priceTxt = itemView.findViewById(R.id.price_txt);
            desTxt = itemView.findViewById(R.id.des_txt);
            fullView = itemView.findViewById(R.id.full_view);
        }
    }
}
