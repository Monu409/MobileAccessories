package com.app.mobilityapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.onClickInterface;
import com.app.mobilityapp.modals.CartChangeModel;
import com.app.mobilityapp.modals.LocalQuantityModel;
import com.app.mobilityapp.modals.ProBrndModal;
import com.app.mobilityapp.modals.ProductsModal;
import com.app.mobilityapp.modals.QuantityModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductBrandListAdapter extends RecyclerView.Adapter<ProductBrandListAdapter.ProdBrndHolder> {
    private List<ProBrndModal> proBrndModals;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnListClick onListClick;
    private int row_index = -1, focusedItem = 0;
    private List<CartChangeModel> cartChangeModels;
    private List<LocalQuantityModel> quantityModels;

//    public ProductBrandListAdapter(List<ProBrndModal> proBrndModals, Context context,List<CartChangeModel> cartChangeModels) {
//        this.proBrndModals = proBrndModals;
//        this.context = context;
//        this.cartChangeModels = cartChangeModels;
//        layoutInflater = LayoutInflater.from(context);
//    }
    public ProductBrandListAdapter(List<ProBrndModal> proBrndModals, Context context,List<LocalQuantityModel> quantityModels) {
        this.proBrndModals = proBrndModals;
        this.context = context;
        this.quantityModels = quantityModels;
        layoutInflater = LayoutInflater.from(context);
    }
    public ProductBrandListAdapter(List<ProBrndModal> proBrndModals, Context context) {
        this.proBrndModals = proBrndModals;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void onListClick(OnListClick onListClick) {
        this.onListClick = onListClick;
    }

    @NonNull
    @Override
    public ProdBrndHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = layoutInflater.inflate(R.layout.adapter_product_brand_list, null,false);
        View view = layoutInflater.inflate(R.layout.adapter_product_brand_list,parent,false);
        return new ProdBrndHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdBrndHolder holder, int position) {
        ProBrndModal proBrndModal = proBrndModals.get(position);
        Glide
                .with(context)
                .load(proBrndModal.getImgUrl())
                .placeholder(R.drawable.test)
                .centerCrop()
                .into(holder.brandImg);
        String brandName = proBrndModal.getName();

        if (TextUtils.isEmpty(brandName) || brandName.equalsIgnoreCase("") || brandName.equalsIgnoreCase("null")) {
            holder.brandTxt.setVisibility(View.GONE);

        } else {
            holder.brandTxt.setVisibility(View.VISIBLE);
            holder.brandTxt.setText(brandName);
        }
//        if(cartChangeModels!=null) {
//            String productBrandId = proBrndModal.getId();
//            for (int i = 0; i < cartChangeModels.size(); i++) {
//                String cartBrandId = cartChangeModels.get(i).getBrandId();
//                if (cartBrandId.equals(productBrandId)) {
//                    holder.qtyTxt.setText("Total Items: " + cartChangeModels.get(i).getQuantity());
//                    proBrndModal.setQuantity(cartChangeModels.get(i).getQuantity());
//                }
//            }
//        }

        if(quantityModels!=null) {
            String productBrandId = proBrndModal.getId();
            for (int i = 0; i < quantityModels.size(); i++) {
                String cartBrandId = quantityModels.get(i).getBrandid();
                if (cartBrandId.equals(productBrandId)) {
                    holder.qtyTxt.setText("Total Items: " + quantityModels.get(i).getTotalQty());
                    proBrndModal.setQuantity(String.valueOf(quantityModels.get(i).getTotalQty()));
                }
            }
        }


//        if (proBrndModal.getQuantity() != null)
//            holder.qtyTxt.setText("Total Items: " + proBrndModal.getQuantity());
//        else
//            holder.qtyTxt.setText("Total Items: 0");


   /*     holder.brandDes.setText(proBrndModals.get(position).getContent());
        String brandId = proBrndModals.get(position).getId();*/
        holder.fullView.setOnClickListener(v -> {
            onListClick.getPosition(proBrndModals.get(position));
            row_index = position;

        });


        holder.fullView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onListClick.getPosition(proBrndModals.get(position));
                row_index = position;
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return proBrndModals.size();
    }

    public class ProdBrndHolder extends RecyclerView.ViewHolder {
        ImageView brandImg;
        TextView brandTxt, brandDes,qtyTxt;
        RelativeLayout fullView;

        public ProdBrndHolder(@NonNull View itemView) {
            super(itemView);
            brandImg = itemView.findViewById(R.id.brand_img);
            brandTxt = itemView.findViewById(R.id.name_txt);
            brandDes = itemView.findViewById(R.id.des_txt);
            qtyTxt = itemView.findViewById(R.id.qty_txt);
            fullView = itemView.findViewById(R.id.full_view);
        }
    }

    public interface OnListClick {
        void getPosition(ProBrndModal proBrndModal);
    }
}
