package com.app.mobilityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.app_utils.ConstantMethods;
import com.app.mobilityapp.modals.NotifivationModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotiHolder> {
    private Context context;
    private List<NotifivationModel.NotifivationModelClild> notifivationModelClilds;
    private LayoutInflater layoutInflater;

    public NotificationAdapter(Context context, List<NotifivationModel.NotifivationModelClild> notifivationModelClilds){
        this.context = context;
        this.notifivationModelClilds = notifivationModelClilds;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public NotiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_notification,null);
        return new NotiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotiHolder holder, int position) {
        holder.messageTxt.setText(notifivationModelClilds.get(position).getContent());
        String serverDate = notifivationModelClilds.get(position).getCreatedAt();
        String[] parts = serverDate.split("T");
        String date = parts[0];
        String showDate = ConstantMethods.changeDateFormate(date);
        holder.dateTxt.setText(showDate);
    }

    @Override
    public int getItemCount() {
        return notifivationModelClilds.size();
    }

    public class NotiHolder extends RecyclerView.ViewHolder {
        TextView messageTxt,dateTxt;
        public NotiHolder(@NonNull View itemView) {
            super(itemView);
            messageTxt = itemView.findViewById(R.id.message_txt);
            dateTxt = itemView.findViewById(R.id.date_txt);
        }
    }
}
