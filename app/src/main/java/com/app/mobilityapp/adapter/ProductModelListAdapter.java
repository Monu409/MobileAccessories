package com.app.mobilityapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.OnEditTextChanged;
import com.app.mobilityapp.app_utils.OnItemClick;
import com.app.mobilityapp.modals.ProBrndModal;
import com.app.mobilityapp.modals.ProModlModel;
import com.app.mobilityapp.modals.ProductsModal;
import com.app.mobilityapp.modals.SingleItem;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductModelListAdapter extends RecyclerView.Adapter<ProductModelListAdapter.ProModlHolder> implements OnItemClick {
    private Context context;
    private List<ProModlModel> proModlModels;
    private LayoutInflater layoutInflater;
    private ProductModelAdapter.OnItemListClick onItemListClick;
    private OnListclick onListclick;
    private int row_index = -1, focusedItem = 0;
    private int quantities[];
    private int qty = 0, sum_qty = 0, sub_qty = 0;
    String name[];
    String brandIds[];
    ArrayList<Integer> total_qty = new ArrayList<Integer>();
    int limit = 5;

    private View.OnClickListener onAddNum;
    private View.OnClickListener onSubNum;

    private List<ProductsModal> mDataSet;
    private OnEditTextChanged onEditTextChanged;
    ArrayList<String> total_amt = new ArrayList<>();
    int sum = 0;
    private ArrayList<String> list;
    private String[] mDataset;
    private Map<String, String> nameQtyMap = new HashMap<>();
    List<Integer> qtyList = new ArrayList<>();

    public ProductModelListAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    public ProductModelListAdapter(List<ProductsModal> myData, OnEditTextChanged onEditTextChanged) {
        mDataSet = myData;
        this.onEditTextChanged = onEditTextChanged;
    }

    public ProductModelListAdapter(Context context, List<ProModlModel> proModlModels) {
        this.context = context;
        this.proModlModels = proModlModels;
        layoutInflater = LayoutInflater.from(context);
        this.quantities = new int[getItemCount()];
        this.name = new String[getItemCount()];
        this.brandIds = new String[getItemCount()];
        for(int i=0;i<proModlModels.size();i++){
            brandIds[i] = proModlModels.get(i).getId();
        }

    }

    public void setOnAddNum(View.OnClickListener onAddNum) {
        this.onAddNum = onAddNum;
    }

    public void setOnSubNum(View.OnClickListener onSubNum) {
        this.onSubNum = onSubNum;
    }

    public void onClickProModlModal(OnListclick onListclick) {
        this.onListclick = onListclick;
    }

    public void onClickProModlModal(ProductModelAdapter.OnItemListClick onItemListClick) {
        this.onItemListClick = onItemListClick;
    }

    @NonNull
    @Override
    public ProModlHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_product_model_list, null);
        //View view = layoutInflater.inflate(R.layout.adapter_model_list,null);

        return new ProModlHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProModlHolder holder, int position) {
        String modelName = proModlModels.get(position).getName();
        holder.nameTxt.setText(modelName);
        holder.edt_qty.setText(proModlModels.get(position).getQty());


        holder.edt_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!holder.edt_qty.getText().toString().equalsIgnoreCase("")) {
                    quantities[holder.getAdapterPosition()] =
                            Integer.parseInt(holder.edt_qty.getText().toString());
                    name[holder.getAdapterPosition()] = holder.nameTxt.getText().toString();
                    brandIds[holder.getAdapterPosition()] = proModlModels.get(position).getId();
                    Log.e("size", proModlModels.size() + "");
                    for (int i = 0; i < quantities.length; i++) {
                        Log.e("quantity", quantities[i] + "");
                    }
                    Intent intent = new Intent("custom-message");
                    intent.putExtra("quantity", quantities);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    int qtyInt = Integer.parseInt(holder.edt_qty.getText().toString());
                    qtyList.add(qtyInt);
                    nameQtyMap.put(holder.nameTxt.getText().toString(), holder.edt_qty.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.edt_qty.getText().toString().equals("") || !holder.edt_qty.getText().toString().equals("0")) {
                    //  qty = Integer.parseInt(holder.edt_qty.getText().toString());
                    qty = Integer.parseInt(proModlModels.get(position).getAmount());
                    if (qty != 1) {
                        qty--;
                        sum_qty = qty;
                        total_qty.add(qty);
                        holder.edt_qty.setText(String.valueOf(qty));
                        proModlModels.get(position).setAmount(String.valueOf(qty));
                    }
                }
            }
        });
        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.edt_qty.getText().toString().equals("")) {
                    qty = Integer.parseInt(holder.edt_qty.getText().toString());
                    qty++;
                    sum_qty = qty;
                    total_qty.add(qty);
                    holder.edt_qty.setText(String.valueOf(qty));

                    proModlModels.get(position).setAmount(String.valueOf(qty));

                    Log.e("Entered Qty", proModlModels.get(position).getAmount());
                    getTotal(position);
                    nameStr.add(holder.nameTxt.getText().toString());
                    qtyStr.add(holder.edt_qty.getText().toString());
                    calc(nameStr, qtyStr);
                    Log.e("res",""+calc(nameStr, qtyStr));
//                    for(int i=0;i<proModlModels.size();i++) {
//                        nameQtyMap.put(proModlModels.get(i).getName(), "0");
//                    }
                }
            }
        });

    }

    List<String> nameStr = new ArrayList<>();
    List<String> qtyStr = new ArrayList<>();

    public Map<String,Integer> calc(List<String> pets, List<String> itmName) {
        Map<String, Integer> hm = new HashMap<>();
        for (int i = 0; i < pets.size(); i++) {
            // If the map already has the pet use the current value, otherwise 0.
            int price = hm.containsKey(itmName) ? hm.get(name) : 0;
            //price += Integer.parseInt(pets.get(i));
            hm.put(itmName.get(i), price);
        }
        System.out.println("");
//        for (String key : hm.keySet()) {
//            System.out.printf("%s: %.2f%n", key, hm.get(key));
//        }
        return hm;
    }

    private void getTotal(int pos) {
        for (int i = 0; i < quantities.length; i++) {
            sum += quantities[i];
        }
        Log.e("Total Qty", sum + "");

    }

    private void showDialog(Context context, CharSequence text) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_model_dialog);
        TextView txt_model = dialog.findViewById(R.id.txt_model);
        Button btn_submit = dialog.findViewById(R.id.btn_submit);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        txt_model.setText(text);
        dialog.show();
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
        }
    }

    public interface OnItemListClick {
        void getProdModal(ProModlModel proModlModel);

    }

    public interface OnListclick {
        void getPosition(ProModlModel proModlModel);
    }

    public int[] getQuantities() {
        return this.quantities;
    }

    public String[] getModelName() {
        return this.name;
    }

    public String[] getBrandIds() {
        return this.brandIds;
    }

}

