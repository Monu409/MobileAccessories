package com.app.mobilityapp.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class NotifivationModel {

    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("data")
    @Expose
    private List<NotifivationModelClild> data = null;

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public List<NotifivationModelClild> getData() {
        return data;
    }

    public void setData(List<NotifivationModelClild> data) {
        this.data = data;
    }

    public class NotifivationModelClild {

        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }

}