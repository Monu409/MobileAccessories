package com.app.mobilityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.modals.AccTransModel;

import java.util.List;

public class AccountTransactionAdapter extends RecyclerView.Adapter<AccountTransactionAdapter.AccTransHolder> {
    private List<AccTransModel> accTransModels;
    private Context context;
    private LayoutInflater layoutInflater;

    public AccountTransactionAdapter(List<AccTransModel> accTransModels, Context context){
        this.accTransModels = accTransModels;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public AccTransHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_account_transaction,null);
        return new AccTransHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccTransHolder holder, int position) {
        holder.payModeTxt.setText(accTransModels.get(position).getPayModeStr());
        holder.amountTxt.setText(accTransModels.get(position).getAmountStr());
        holder.dateTxt.setText(accTransModels.get(position).getDateStr());
        holder.creditTxt.setText(accTransModels.get(position).getCreditAmountStr());
    }

    @Override
    public int getItemCount() {
        return accTransModels.size();
    }

    public class AccTransHolder extends RecyclerView.ViewHolder {
        TextView payModeTxt,amountTxt,creditTxt,dateTxt;
        public AccTransHolder(@NonNull View itemView) {
            super(itemView);
            payModeTxt = itemView.findViewById(R.id.pay_mode_txt);
            amountTxt = itemView.findViewById(R.id.amount_txt);
            dateTxt = itemView.findViewById(R.id.date_txt);
            creditTxt = itemView.findViewById(R.id.credit_txt);
        }
    }
}
