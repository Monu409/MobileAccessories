package com.app.mobilityapp.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MPHolder> {
    @NonNull
    @Override
    public MPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MPHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MPHolder extends RecyclerView.ViewHolder {
        public MPHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
