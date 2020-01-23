package com.app.mobilityapp.modals;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConversationModel {

    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("data")
    @Expose
    private List<ConversationChildModel> data = null;

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public List<ConversationChildModel> getData() {
        return data;
    }

    public void setData(List<ConversationChildModel> data) {
        this.data = data;
    }

}

