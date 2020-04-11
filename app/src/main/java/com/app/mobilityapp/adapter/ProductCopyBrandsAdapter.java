package com.app.mobilityapp.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.app.mobilityapp.activities.EnterQuantityACopy;
import com.app.mobilityapp.modals.LocalQuantityModel;
import com.app.mobilityapp.modals.ProBrndModal;
import com.app.mobilityapp.modals.ProductCopyModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductCopyBrandsAdapter extends RecyclerView.Adapter<ProductCopyBrandsAdapter.PCBHolder> {
    private Context context;
    private List<ProductCopyModel.BrandDetail> brandDetails;
    private List<ProductCopyModel.Price> priceList;
    private LayoutInflater layoutInflater;
    private List<LocalQuantityModel> quantityModels;

    public ProductCopyBrandsAdapter(Context context, List<ProductCopyModel.BrandDetail> brandDetails,List<ProductCopyModel.Price> priceList,List<LocalQuantityModel> quantityModels){
        this.context = context;
        this.brandDetails = brandDetails;
        this.priceList = priceList;
        this.quantityModels = quantityModels;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public PCBHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_product_brand_list,parent,false);
        return new PCBHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull PCBHolder holder, int position) {
        ProductCopyModel.Brand brand = brandDetails.get(position).getBrand();
        String imgUrl = brand.getImage().getImageurl();
        String brandName = brand.getName();
        Glide
                .with(context)
                .load(imgUrl)
                .placeholder(R.drawable.test)
                .centerCrop()
                .into(holder.brandImg);

        if (TextUtils.isEmpty(brandName) || brandName.equalsIgnoreCase("") || brandName.equalsIgnoreCase("null")) {
            holder.brandTxt.setVisibility(View.GONE);

        } else {
            holder.brandTxt.setVisibility(View.VISIBLE);
            holder.brandTxt.setText(brandName);
        }
        if(quantityModels!=null) {
            String productBrandId = brandDetails.get(position).getId();
            for (int i = 0; i < quantityModels.size(); i++) {
                String cartBrandId = quantityModels.get(i).getBrandid();
                if(cartBrandId!=null) {
                    if (cartBrandId.equals(productBrandId)) {
                        holder.qtyTxt.setText(String.valueOf(quantityModels.get(i).getTotalQty()));
                        brandDetails.get(position).setQty(String.valueOf(quantityModels.get(i).getTotalQty()));
                    }
                }
            }
        }
        List<ProductCopyModel.Model> modelList = brandDetails.get(position).getModel();
        holder.fullView.setOnClickListener(v->{
            Intent intent = new Intent(context, EnterQuantityACopy.class);
            intent.putExtra("price_list",(ArrayList)priceList);
            intent.putExtra("qty_list",(ArrayList)modelList);
            intent.putExtra("brandId",brandDetails.get(position).getId());
            intent.putExtra("brand_id",productId);
            intent.putExtra("position",position);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return brandDetails.size();
    }

    public class PCBHolder extends RecyclerView.ViewHolder {
        ImageView brandImg;
        TextView brandTxt, brandDes,qtyTxt;
        RelativeLayout fullView;

        public PCBHolder(@NonNull View itemView) {
            super(itemView);
            brandImg = itemView.findViewById(R.id.brand_img);
            brandTxt = itemView.findViewById(R.id.name_txt);
            brandDes = itemView.findViewById(R.id.des_txt);
            qtyTxt = itemView.findViewById(R.id.qty_txt);
            fullView = itemView.findViewById(R.id.full_view);
        }
    }
    private String productId;
    public void getProductId(String productId){
       this.productId = productId;
    }
}
