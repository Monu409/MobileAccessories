package com.app.mobilityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.modals.CreditStmntModel;

import java.util.List;

public class CreditStatementAdapter extends RecyclerView.Adapter<CreditStatementAdapter.CreditHolder> {
    private List<CreditStmntModel> creditStmntModels;
    private Context context;
    private LayoutInflater layoutInflater;

    public CreditStatementAdapter(List<CreditStmntModel> creditStmntModels, Context context){
        this.creditStmntModels = creditStmntModels;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public CreditHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_credit_statement,null);
        return new CreditHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditHolder holder, int position) {
        holder.dateTxt.setText(creditStmntModels.get(position).getCreatedAt());
        holder.creditTxt.setText(String.valueOf(creditStmntModels.get(position).getCreditBalence()));
        holder.debitTxt.setText(String.valueOf(creditStmntModels.get(position).getUsedBalence()));
        holder.totalTxt.setText(String.valueOf(creditStmntModels.get(position).getCreditlimit()));
    }

    @Override
    public int getItemCount() {
        return creditStmntModels.size();
    }

    public class CreditHolder extends RecyclerView.ViewHolder {
        TextView dateTxt,creditTxt,debitTxt,totalTxt;
        public CreditHolder(@NonNull View itemView) {
            super(itemView);
            dateTxt = itemView.findViewById(R.id.date_txt);
            creditTxt = itemView.findViewById(R.id.credit_txt);
            debitTxt = itemView.findViewById(R.id.debit_txt);
            totalTxt = itemView.findViewById(R.id.rest_txt);
        }
    }
}
