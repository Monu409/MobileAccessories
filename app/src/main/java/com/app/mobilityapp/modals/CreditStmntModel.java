package com.app.mobilityapp.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditStmntModel {

    @SerializedName("orderId")
    @Expose
    private String orderId;
    @SerializedName("creditBalence")
    @Expose
    private String creditBalence;
    @SerializedName("totallimit")
    @Expose
    private Integer totallimit;
    @SerializedName("usedBalence")
    @Expose
    private String usedBalence;
    @SerializedName("creditlimit")
    @Expose
    private String creditlimit;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getTotallimit() {
        return totallimit;
    }

    public void setTotallimit(Integer totallimit) {
        this.totallimit = totallimit;
    }

    public String getUsedBalence() {
        return usedBalence;
    }

    public void setUsedBalence(String usedBalence) {
        this.usedBalence = usedBalence;
    }

    public String getCreditlimit() {
        return creditlimit;
    }

    public void setCreditlimit(String creditlimit) {
        this.creditlimit = creditlimit;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public void setV(Integer v) {
        this.v = v;
    }

    public String getCreditBalence() {
        return creditBalence;
    }

    public void setCreditBalence(String creditBalence) {
        this.creditBalence = creditBalence;
    }
}