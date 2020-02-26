package com.app.mobilityapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mobilityapp.R;
import com.app.mobilityapp.activities.FullImageActivity;
import com.app.mobilityapp.modals.ConversationChildModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anupamchugh on 09/02/16.
 */
public class MultiViewTypeAdapter extends RecyclerView.Adapter {

    private List<ConversationChildModel> chatModels;
    private Context context;
    int total_types;
    private String mySendId;
    private LayoutInflater layoutInflater;
    private boolean fabStateVolume = false;

    public static class ChatHolder extends RecyclerView.ViewHolder {
        TextView sentTxt, incomngTxt;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            sentTxt = itemView.findViewById(R.id.outgng_txt);
            incomngTxt = itemView.findViewById(R.id.incomng_txt);
        }
    }

    public static class ChatHolderImage extends RecyclerView.ViewHolder {
        ImageView chatImg, incomingImg;

        public ChatHolderImage(@NonNull View itemView) {
            super(itemView);
            chatImg = itemView.findViewById(R.id.img_chat);
            incomingImg = itemView.findViewById(R.id.img_chat_in);
        }
    }


    public MultiViewTypeAdapter(List<ConversationChildModel> chatModels, Context context, String mySendId) {
        this.chatModels = chatModels;
        this.context = context;
        this.mySendId = mySendId;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_image_adapter, parent, false);
                return new ChatHolderImage(view);
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat, parent, false);
                return new ChatHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        int returnValue;
        ConversationChildModel conversationChildModel = chatModels.get(position);
        String imgUrl = conversationChildModel.getImageurl();
        if (TextUtils.isEmpty(imgUrl)) {
            imgUrl = "";
        }
        if (!imgUrl.isEmpty()) {
            returnValue = 1;
        } else {
            returnValue = 2;
        }
        return returnValue;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {
        ConversationChildModel conversationChildModel = chatModels.get(listPosition);
        if (mySendId.equals(chatModels.get(listPosition).getSenderBy())) {       //here is the outgoing messages
            String imgUrl = conversationChildModel.getImageurl();
            if (TextUtils.isEmpty(imgUrl)) {
                imgUrl = "";
            }
            if (!imgUrl.isEmpty()) {
                ChatHolderImage chatHolderImage = (ChatHolderImage) holder;
                Glide
                        .with(context)
                        .load(chatModels.get(listPosition).getImageurl())
                        .centerCrop()
                        .into(chatHolderImage.chatImg);
                ((ChatHolderImage) holder).incomingImg.setVisibility(View.GONE);
                chatHolderImage.chatImg.setOnClickListener(v -> {
                    Intent intent = new Intent(context, FullImageActivity.class);
                    intent.putExtra("image_url", chatModels.get(listPosition).getImageurl());
                    context.startActivity(intent);
                });
            }
            else{
                ChatHolder chatHolder = (ChatHolder)holder;
                chatHolder.sentTxt.setText(chatModels.get(listPosition).getContent());
                chatHolder.incomngTxt.setVisibility(View.GONE);
            }
        } else {
            String imgUrl = conversationChildModel.getImageurl();
            if (TextUtils.isEmpty(imgUrl)) {
                imgUrl = "";
            }
            if (!imgUrl.isEmpty()) {
                ChatHolderImage chatHolderImage = (ChatHolderImage) holder;
                Glide
                        .with(context)
                        .load(chatModels.get(listPosition).getImageurl())
                        .centerCrop()
                        .into(chatHolderImage.incomingImg);
                ((ChatHolderImage) holder).chatImg.setVisibility(View.GONE);
                chatHolderImage.incomingImg.setOnClickListener(v -> {
                    Intent intent = new Intent(context, FullImageActivity.class);
                    intent.putExtra("image_url", chatModels.get(listPosition).getImageurl());
                    context.startActivity(intent);
                });
            }
            else{
                ChatHolder chatHolder = (ChatHolder)holder;
                chatHolder.incomngTxt.setText(chatModels.get(listPosition).getContent());
                chatHolder.sentTxt.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }
}