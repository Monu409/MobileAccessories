package com.app.mobilityapp.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConversationChildModel {
    @SerializedName("senderBy")
    @Expose
    private String senderBy;
    @SerializedName("senderFor")
    @Expose
    private String senderFor;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("seen")
    @Expose
    private Boolean seen;
    @SerializedName("conversationId")
    @Expose
    private String conversationId;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("deletestt")
    @Expose
    private Boolean deletestt;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("imageurl")
    @Expose
    private String imageurl;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getSenderBy() {
        return senderBy;
    }

    public void setSenderBy(String senderBy) {
        this.senderBy = senderBy;
    }

    public String getSenderFor() {
        return senderFor;
    }

    public void setSenderFor(String senderFor) {
        this.senderFor = senderFor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getDeletestt() {
        return deletestt;
    }

    public void setDeletestt(Boolean deletestt) {
        this.deletestt = deletestt;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getV() {
        return v;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}