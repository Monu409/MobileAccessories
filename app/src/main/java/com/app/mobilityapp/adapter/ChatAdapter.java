package com.app.mobilityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.modals.ConversationChildModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {
    private List<ConversationChildModel> chatModels;
    private Context context;
    private String mySendId;
    private LayoutInflater layoutInflater;

    public ChatAdapter(List<ConversationChildModel> chatModels, Context context,String mySendId){
        this.chatModels = chatModels;
        this.context = context;
        this.mySendId = mySendId;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_chat,null);
        return new ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        if(mySendId.equals(chatModels.get(position).getSenderBy())){
            holder.sentTxt.setText(chatModels.get(position).getContent());
            holder.incomngTxt.setVisibility(View.GONE);
        }
        else {
            holder.incomngTxt.setText(chatModels.get(position).getContent());
            holder.sentTxt.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder {
        TextView sentTxt,incomngTxt;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            sentTxt = itemView.findViewById(R.id.outgng_txt);
            incomngTxt = itemView.findViewById(R.id.incomng_txt);
        }
    }
}
