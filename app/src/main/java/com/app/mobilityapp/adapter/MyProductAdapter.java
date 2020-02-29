package com.app.mobilityapp.adapter;

import android.content.Context;
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

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MPHolder> {
    private List<MyProductModel.MyProductChild> myProductChildren;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyProductAdapter(List<MyProductModel.MyProductChild> myProductChildren, Context context){
        this.myProductChildren = myProductChildren;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public MPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_my_product,null);
        return new MPHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MPHolder holder, int position) {
        holder.prodName.setText(myProductChildren.get(position).getName());
        List<MyProductModel.Image> image = myProductChildren.get(position).getImage();
        if(image.size()!=0){
            String imgUrl = image.get(0).getImageurl();
            Glide
                    .with(context)
                    .load(imgUrl)
                    .centerCrop()
                    .into(holder.prodImg);
        }
    }

    @Override
    public int getItemCount() {
        return myProductChildren.size();
    }

    public class MPHolder extends RecyclerView.ViewHolder {
        CircleImageView prodImg;
        TextView prodName,editTxt,actvTxt,inactvTxt;
        public MPHolder(@NonNull View itemView) {
            super(itemView);
            prodImg = itemView.findViewById(R.id.prod_img);
            prodName = itemView.findViewById(R.id.prod_name_txt);
        }
    }
}
