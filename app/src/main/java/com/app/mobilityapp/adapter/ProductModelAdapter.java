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
import com.app.mobilityapp.activities.ProductActivity;
import com.app.mobilityapp.modals.ProModlModel;

import java.util.List;

public class ProductModelAdapter extends RecyclerView.Adapter<ProductModelAdapter.ProModlHolder> {
    private Context context;
    private List<ProModlModel> proModlModels;
    private LayoutInflater layoutInflater;
    private OnItemListClick onItemListClick;

    public ProductModelAdapter(Context context, List<ProModlModel> proModlModels){
        this.context = context;
        this.proModlModels = proModlModels;
        layoutInflater = LayoutInflater.from(context);
    }

    public void onClickProModlModal(OnItemListClick onItemListClick){
        this.onItemListClick = onItemListClick;
    }
    @NonNull
    @Override
    public ProModlHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_product_model,null);
        return new ProModlHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProModlHolder holder, int position) {
        String modelName = proModlModels.get(position).getName();
        holder.nameTxt.setText(modelName);
        holder.desTxt.setText(proModlModels.get(position).getContent());
        holder.fullView.setOnClickListener(v->{
            onItemListClick.getProdModal(proModlModels.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return proModlModels.size();
    }

    public class  ProModlHolder extends RecyclerView.ViewHolder {
        TextView nameTxt,desTxt;
        LinearLayout fullView;
        public ProModlHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name_txt);
            desTxt = itemView.findViewById(R.id.des_txt);
            fullView = itemView.findViewById(R.id.full_view);
        }
    }

    public interface OnItemListClick{
        void getProdModal(ProModlModel proModlModel);

        void getPosition(ProModlModel proModlModel);
    }
}
