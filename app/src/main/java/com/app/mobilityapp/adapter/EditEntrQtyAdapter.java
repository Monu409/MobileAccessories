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
import com.app.mobilityapp.modals.EditCartModel;
import com.app.mobilityapp.modals.ModelListModel;
import com.app.mobilityapp.modals.ProModlModel;

import java.util.ArrayList;
import java.util.List;

public class EditEntrQtyAdapter extends RecyclerView.Adapter<EditEntrQtyAdapter.ProModlHolder> implements OnItemClick {
    private Activity context;
    private List<EditCartModel.Modallist> modallists;
    private int qty = 0;
    private List<ModelListModel> _retData;

    public EditEntrQtyAdapter(Activity context, List<EditCartModel.Modallist> modallists) {
        this.context = context;
        this.modallists = modallists;
        _retData = new ArrayList<>();
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
        EditCartModel.Modallist modallist = modallists.get(position);
        EditCartModel.Modalid modalid = modallist.getModalid();
        holder.nameTxt.setText(modalid.getName());
        holder.edt_qty.setText(String.valueOf(modallist.getQuantity()));
        ModelListModel modelListModel = new ModelListModel();
        modelListModel.setModalid(modalid.getId());
        modelListModel.setQuantity(String.valueOf(modallist.getQuantity()));
        _retData.add(modelListModel);
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
                    ModelListModel modelListModel = new ModelListModel();
                    modelListModel.setModalid(modalid.getId());
                    modelListModel.setQuantity(s.toString());
                    _retData.remove(position);
                    _retData.add(position,modelListModel);
                } else {
                    qty = 0;
                }
            }
        });

        holder.btn_minus.setOnClickListener(v -> {
            if (!holder.edt_qty.getText().toString().equals("") && !holder.edt_qty.getText().toString().equals("0")) {
                qty = Integer.parseInt(holder.edt_qty.getText().toString());
                qty--;
            } else
                qty = 0;
            holder.edt_qty.setText("" + qty);
        });

        holder.btn_plus.setOnClickListener(v -> {
            if (!holder.edt_qty.getText().toString().equals("")) {
                qty = Integer.parseInt(holder.edt_qty.getText().toString());
                qty++;
            } else {
                qty = 1;
            }
            holder.edt_qty.setText("" + qty);
        });

    }


    @Override
    public int getItemCount() {
        return modallists.size();
    }

    @Override
    public void onClick(String value) {

    }

    public class ProModlHolder extends RecyclerView.ViewHolder {
        TextView nameTxt;
        EditText edt_qty;
        RelativeLayout fullView;
        ImageView btn_plus, btn_minus;

        public ProModlHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name_txt);
            fullView = itemView.findViewById(R.id.full_view);
            edt_qty = itemView.findViewById(R.id.edt_qty);
            btn_plus = itemView.findViewById(R.id.btn_plus);
            btn_minus = itemView.findViewById(R.id.btn_minus);
            setIsRecyclable(false);
        }
    }

    public List<ModelListModel> retrieveData()
    {
        return _retData;
    }

}
