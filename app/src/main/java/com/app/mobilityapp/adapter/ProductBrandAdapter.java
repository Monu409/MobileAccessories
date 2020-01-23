package com.app.mobilityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.ProductModelActivity;
import com.app.mobilityapp.modals.ProBrndModal;
import com.bumptech.glide.Glide;

import java.util.List;

public class ProductBrandAdapter extends RecyclerView.Adapter<ProductBrandAdapter.ProdBrndHolder> {
    private List<ProBrndModal> proBrndModals;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnListClick onListClick;

    public ProductBrandAdapter(List<ProBrndModal> proBrndModals, Context context){
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
        View view = layoutInflater.inflate(R.layout.adapter_product_brand,null);
        return new ProdBrndHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdBrndHolder holder, int position) {
        Glide
                .with(context)
                .load(proBrndModals.get(position).getImgUrl())
                .centerCrop()
                .into(holder.brandImg);
        String brandName = proBrndModals.get(position).getName();
        holder.brandTxt.setText(brandName);
        holder.brandDes.setText(proBrndModals.get(position).getContent());
        String brandId = proBrndModals.get(position).getId();
        holder.fullView.setOnClickListener(v->{
            onListClick.getPosition(proBrndModals.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return proBrndModals.size();
    }

    public class ProdBrndHolder extends RecyclerView.ViewHolder{
        ImageView brandImg;
        TextView brandTxt,brandDes;
        RelativeLayout fullView;
        public ProdBrndHolder(@NonNull View itemView) {
            super(itemView);
            brandImg = itemView.findViewById(R.id.brand_img);
            brandTxt = itemView.findViewById(R.id.name_txt);
            brandDes = itemView.findViewById(R.id.des_txt);
            fullView = itemView.findViewById(R.id.full_view);
        }
    }
    public interface OnListClick{
        void getPosition(ProBrndModal proBrndModal);
    }
}
