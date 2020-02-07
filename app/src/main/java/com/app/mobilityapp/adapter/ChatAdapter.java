package com.app.mobilityapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.modals.ConversationChildModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ConversationChildModel> chatModels;
    private Context context;
    private String mySendId;
    private LayoutInflater layoutInflater;
    private int first = 1;
    private int second = 2;

    public ChatAdapter(List<ConversationChildModel> chatModels, Context context,String mySendId){
        this.chatModels = chatModels;
        this.context = context;
        this.mySendId = mySendId;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                View view = layoutInflater.inflate(R.layout.adapter_chat,parent,false);
                viewHolder = new ChatHolder(view);
                break;
            case 2:
                View view1 = layoutInflater.inflate(R.layout.chat_image_adapter,parent,false);
                viewHolder = new ChatHolderImage(view1);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        String content = chatModels.get(position).getContent();
        switch (content){
            case "":
                ChatHolderImage chatHolderImage = (ChatHolderImage)viewHolder;
                Glide
                        .with(context)
                        .load(chatModels.get(position).getImageurl())
                        .centerCrop()
                        .into(chatHolderImage.chatImg);
                break;
            default:
                ChatHolder chatHolder = (ChatHolder)viewHolder;
                if (mySendId.equals(chatModels.get(position).getSenderBy())) {
                    chatHolder.sentTxt.setText(chatModels.get(position).getContent());
                    chatHolder.incomngTxt.setVisibility(View.GONE);
                } else {
                    chatHolder.incomngTxt.setText(chatModels.get(position).getContent());
                    chatHolder.sentTxt.setVisibility(View.GONE);
                }
                break;


        }
//        String incomingMsg = chatModels.get(position).getContent();
//        if(incomingMsg.isEmpty()){
//            ChatHolderImage chatHolderImage = (ChatHolderImage)viewHolder;
//            Glide
//                    .with(context)
//                    .load(chatModels.get(position).getImageurl())
//                    .centerCrop()
//                    .into(chatHolderImage.chatImg);
//        }
//        else {
//            ChatHolder chatHolder = (ChatHolder)viewHolder;
//            if (mySendId.equals(chatModels.get(position).getSenderBy())) {
//                chatHolder.sentTxt.setText(chatModels.get(position).getContent());
//                chatHolder.incomngTxt.setVisibility(View.GONE);
//            } else {
//                chatHolder.incomngTxt.setText(chatModels.get(position).getContent());
//                chatHolder.sentTxt.setVisibility(View.GONE);
//            }
//        }

    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        String content = chatModels.get(position).getContent();
        switch (content) {
            case "":
                return first;
            default:
                return second;
        }
    }

    public class ChatHolder extends RecyclerView.ViewHolder {
        TextView sentTxt,incomngTxt;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            sentTxt = itemView.findViewById(R.id.outgng_txt);
            incomngTxt = itemView.findViewById(R.id.incomng_txt);
        }
    }

    public class ChatHolderImage extends RecyclerView.ViewHolder {
        ImageView chatImg;
        public ChatHolderImage(@NonNull View itemView) {
            super(itemView);
            chatImg = itemView.findViewById(R.id.img_chat);
        }
    }
}
