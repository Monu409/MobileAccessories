package com.app.mobilityapp.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.EnterQuantityACopy;
import com.app.mobilityapp.app_utils.OnItemClick;
import com.app.mobilityapp.modals.ProModlModel;

import java.util.List;

public class ProductModelListAdapter1 extends RecyclerView.Adapter<ProductModelListAdapter1.ProModlHolder> implements OnItemClick {
    private Activity context;
    private List<ProModlModel> proModlModels;
    private int qty = 0;

    public ProductModelListAdapter1(Activity context, List<ProModlModel> proModlModels) {
        this.context = context;
        this.proModlModels = proModlModels;
    }

    @NonNull
    @Override
    public ProModlHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_product_model_list, null);
        return new ProModlHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProModlHolder holder, int position) {
        holder.setIsRecyclable(false);
        ProModlModel proModlModel = proModlModels.get(position);
        holder.nameTxt.setText(proModlModel.getName());
        holder.edt_qty.setText("");

        holder.edt_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!holder.edt_qty.getText().toString().equalsIgnoreCase("")) {
                    qty = Integer.parseInt(holder.edt_qty.getText().toString());
                } else
                    qty = 0;
                if (!proModlModel.getQty().equals("" + qty)) {
                    proModlModel.setQty("" + qty);
                    if (context instanceof EnterQuantityACopy) {
                        ((EnterQuantityACopy) context).update_quantity();
                    }
                }
            }
        });

        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.edt_qty.getText().toString().equals("") && !holder.edt_qty.getText().toString().equals("0")) {
                    qty = Integer.parseInt(holder.edt_qty.getText().toString());
                    qty--;
                } else
                    qty = 0;
                holder.edt_qty.setText("" + qty);
            }
        });

        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.edt_qty.getText().toString().equals("")) {
                    qty = Integer.parseInt(holder.edt_qty.getText().toString());
                    qty++;
                } else {
                    qty = 1;
                }
                holder.edt_qty.setText("" + qty);
            }
        });

    }


    @Override
    public int getItemCount() {
        return proModlModels.size();
    }

    @Override
    public void onClick(String value) {

    }

    public class ProModlHolder extends RecyclerView.ViewHolder {
        TextView nameTxt, desTxt, txt_minus, txt_plus;
        EditText edt_qty;
        RelativeLayout fullView;
        // RelativeLayout fullView;
        ImageView btn_plus, btn_minus;
        LinearLayout lnr_plus, lnr_minus;

        public ProModlHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name_txt);
            /*  desTxt = itemView.findViewById(R.id.des_txt);*/
            fullView = itemView.findViewById(R.id.full_view);
            edt_qty = itemView.findViewById(R.id.edt_qty);
            btn_plus = itemView.findViewById(R.id.btn_plus);
            btn_minus = itemView.findViewById(R.id.btn_minus);
            setIsRecyclable(false);
        }
    }

    private int quantities[];
//    public int[] getQuantities() {
//        return this.quantities;
//    }
//
//    public String[] getModelName() {
//        return this.name;
//    }
//
//    public String[] getBrandIds() {
//        return this.brandIds;
//    }

}

