package com.app.mobilityapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.modals.MyProductModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShowMyProdBrndAdapter extends RecyclerView.Adapter<ShowMyProdBrndAdapter.SMBHolder> {
    private List<MyProductModel.BrandDetail> brandDetails;
    private Context context;
    private LayoutInflater layoutInflater;

    public ShowMyProdBrndAdapter(List<MyProductModel.BrandDetail> brandDetails, Context context){
        this.brandDetails = brandDetails;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public SMBHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_show_my_prod_brand,null);
        return new SMBHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SMBHolder holder, int position) {
        MyProductModel.Brand brand = brandDetails.get(position).getBrand();
        MyProductModel.Image image = brandDetails.get(position).getBrand().getImage();
        List<MyProductModel.Model> modelList = brandDetails.get(position).getModel();
        String brndName = brand.getName();
        String imgUrl = image.getImageurl();
        String modlStr = "";
        for(int i=0;i<modelList.size();i++){
            String modlName = modelList.get(i).getName();
            modlStr = modlStr+modlName+",";
        }
        modlStr = modlStr.substring(0, modlStr.length() - 1);

        holder.prodName.setText(brndName);
        Spannable word = new SpannableString("Models: ");
        word.setSpan(new ForegroundColorSpan(Color.RED), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.brandsName.setText(word);
        holder.brandsName.append(modlStr);
//        holder.brandsName.setText("Models: "+modlStr);
        Glide
                .with(context)
                .load(imgUrl)
                .centerCrop()
                .into(holder.prodImg);
    }

    @Override
    public int getItemCount() {
        return brandDetails.size();
    }

    public class SMBHolder extends RecyclerView.ViewHolder {
        public TextView prodName,brandsName;
        public CircleImageView prodImg;
        public SMBHolder(@NonNull View itemView) {
            super(itemView);
            prodName = itemView.findViewById(R.id.name_txt);
            brandsName = itemView.findViewById(R.id.brnd_name_txt);
            prodImg = itemView.findViewById(R.id.brand_img);
        }
    }
}
