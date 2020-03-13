package com.app.mobilityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.Add_ProductActivity;
import com.app.mobilityapp.activities.ShowMyProductActivity;
import com.app.mobilityapp.app_utils.CircleImageView;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.connection.CommonNetwork;
import com.app.mobilityapp.connection.JSONResult;
import com.app.mobilityapp.modals.MyProductModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.app.mobilityapp.app_utils.AppApis.MY_PRODUCT_STATUS;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MPHolder> {
    private List<MyProductModel.MyProductChild> myProductChildren;
    private Context context;
    private LayoutInflater layoutInflater;
    public static final String PAYLOAD_NAME = "PAYLOAD_NAME";

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
private boolean status;
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
        List<MyProductModel.Price> priceList = myProductChildren.get(position).getPrice();
        List<MyProductModel.BrandDetail> brandDetails = myProductChildren.get(position).getBrandDetails();
        holder.fullView.setOnClickListener(v->{
            Intent intent = new Intent(context, ShowMyProductActivity.class);
            intent.putExtra("price_list",(ArrayList)priceList);
            intent.putExtra("model_list",(ArrayList)brandDetails);
            context.startActivity(intent);
        });
        String productId = myProductChildren.get(position).getId();
        String btnTxtStr = holder.actvTxt.getText().toString();
        status = myProductChildren.get(position).getStatus();
        if(!status) {
            holder.actvTxt.setText("Inactive");
            holder.actvTxt.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }
        else{
            holder.actvTxt.setText("Active");
            holder.actvTxt.setBackgroundColor(context.getResources().getColor(R.color.green));
        }
//        if(btnTxtStr.equals("Active")){
//            statusBol = false;
//        }
//        else if(btnTxtStr.equals("Inactive")){
//            statusBol = true;
//        }
        holder.actvTxt.setOnClickListener(v->{
            changeProductStatus(!status,productId,holder,position);
            status = !status;
        });

        holder.editTxt.setOnClickListener(v -> {
            String id = myProductChildren.get(position).getId();
            Intent intent =new Intent(context,Add_ProductActivity.class);
            intent.putExtra("page_type","edit");
            intent.putExtra("data",  new Gson().toJson(myProductChildren.get(position)));
            intent.putExtra("product_id",id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return myProductChildren.size();
    }

    public class MPHolder extends RecyclerView.ViewHolder {
        CircleImageView prodImg;
        TextView prodName,editTxt,actvTxt;
        RelativeLayout fullView;
        public MPHolder(@NonNull View itemView) {
            super(itemView);
            prodImg = itemView.findViewById(R.id.prod_img);
            prodName = itemView.findViewById(R.id.prod_name_txt);
            fullView = itemView.findViewById(R.id.full_view);
            actvTxt = itemView.findViewById(R.id.activ_txt);
            editTxt = itemView.findViewById(R.id.edit_txt);
        }
    }

    private void changeProductStatus(boolean status,String productId,MPHolder mpHolder,int position){
        ConstantMethods.showProgressbar(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status",status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CommonNetwork.putNetworkJsonObj(MY_PRODUCT_STATUS+productId, jsonObject, new JSONResult() {
            @Override
            public void notifySuccess(@NonNull JSONObject response) {
                Log.e("response",""+response);
                ConstantMethods.dismissProgressBar();
                try {
                    String confirmation = response.getString("confirmation");
                    if(confirmation.equals("success")){
                        Toast.makeText(context, "Status changed", Toast.LENGTH_SHORT).show();
                        JSONObject dataObj = response.getJSONObject("data");
                        String status = dataObj.getString("status");
                        if(status.equals("false")) {
                            mpHolder.actvTxt.setText("Inactive");
                            mpHolder.actvTxt.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                        }
                        else{
                            mpHolder.actvTxt.setText("Active");
                            mpHolder.actvTxt.setBackgroundColor(context.getResources().getColor(R.color.green));
                        }
//                        notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
            }

            @Override
            public void notifyError(@NonNull ANError anError) {
                Log.e("response",""+anError);
                ConstantMethods.dismissProgressBar();
            }
        },context);
    }
}
