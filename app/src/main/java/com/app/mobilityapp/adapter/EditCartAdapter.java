package com.app.mobilityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.EditEnterQuantityActivity;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.modals.EditCartModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class EditCartAdapter extends RecyclerView.Adapter<EditCartAdapter.ECHolder>{
    private Context context;
    private EditCartModel editCartModel;
    private LayoutInflater layoutInflater;
    String cartId;
    private GetSubCartCatId getSubCartCatId;

    public EditCartAdapter(Context context, EditCartModel editCartModel,String cartId,GetSubCartCatId getSubCartCatId){
        this.context = context;
        this.editCartModel = editCartModel;
        this.cartId = cartId;
        this.getSubCartCatId = getSubCartCatId;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ECHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_edit_cart,null);
        return new ECHolder(view);
    }

    int qtySum1 = 0;
    @Override
    public void onBindViewHolder(@NonNull ECHolder holder, int position) {
        int qtySum = 0;
        List<EditCartModel.BrandDetail> brandDetail = editCartModel.getData().getBrandDetails();
        String catName = brandDetail.get(position).getBrand().getName();
        String imageUrl = brandDetail.get(position).getBrand().getImage().getImageurl();
        List<EditCartModel.Modallist> modallists = brandDetail.get(position).getModallist();

        for(int i=0;i<modallists.size();i++){
            int qty = modallists.get(i).getQuantity();
            qtySum = qtySum+qty;
        }
//        qtySum1 = qtySum;

        holder.catName.setText(catName);
        holder.quantity.setText("Qty: "+qtySum);
        String qtyTxt = holder.quantity.getText().toString();
        String[] parts = qtyTxt.split(" ");
        String qtyStr = parts[1];
        int qtyInt = Integer.parseInt(qtyStr);
        Glide
                .with(context)
                .load(imageUrl)
                .placeholder(R.drawable.test)
                .centerCrop()
                .into(holder.circleImageView);

        String subCartId = brandDetail.get(position).getId();

        holder.deleteImg.setOnClickListener(v->getSubCartCatId.getSubCartId(subCartId,cartId));

        holder.editCart.setOnClickListener(v->{
            int fullQty = getFullQty();
            int otherQty = fullQty-qtyInt;
            EditCartModel.Brand brand = brandDetail.get(position).getBrand();
            List<EditCartModel.Price> prices = editCartModel.getData().getProductid().getPrice();
            Intent intent = new Intent(context, EditEnterQuantityActivity.class);
            intent.putExtra("modal_lists",(ArrayList)modallists);
            intent.putExtra("brandDetail_id",brandDetail.get(position).getId());
            intent.putExtra("brand_id",brand.getId());
            intent.putExtra("price_list",(ArrayList)prices);
            intent.putExtra("cart_id",cartId);
            intent.putExtra("other_qty",otherQty);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        List<EditCartModel.BrandDetail> brandDetail = editCartModel.getData().getBrandDetails();
        return brandDetail.size();
    }

    public class ECHolder extends RecyclerView.ViewHolder{
        TextView catName,productName,quantity;
        ImageView editCart,deleteImg;
        CircleImageView circleImageView;
        RelativeLayout fullView;
        public ECHolder(@NonNull View itemView) {
            super(itemView);
            catName = itemView.findViewById(R.id.cat_name_txt);
            productName = itemView.findViewById(R.id.name_txt);
            quantity = itemView.findViewById(R.id.qty_txt);
            editCart = itemView.findViewById(R.id.edit_qty);
            circleImageView = itemView.findViewById(R.id.brand_img);
            fullView = itemView.findViewById(R.id.full_view);
            deleteImg = itemView.findViewById(R.id.delete_itm);
        }
    }

    public interface GetSubCartCatId{
        void getSubCartId(String subCartId,String cartId);
    }

    private int getFullQty(){
        List<EditCartModel.BrandDetail> brandDetail = editCartModel.getData().getBrandDetails();
        int fullQty = 0;
        for(int i=0;i<brandDetail.size();i++) {
            List<EditCartModel.Modallist> modallists = brandDetail.get(i).getModallist();
            int qtySum = 0;
            for (int j = 0; j < modallists.size(); j++) {
                int qty = modallists.get(j).getQuantity();
                qtySum = qtySum + qty;
            }
            fullQty = fullQty+qtySum;
        }
        return fullQty;
    }
}
