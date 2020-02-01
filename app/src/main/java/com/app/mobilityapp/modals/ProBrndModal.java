package com.app.mobilityapp.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

public class ProBrndModal implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("imgUrl")
    @Expose
    private String imgUrl;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("__v")
    @Expose
    private Integer v;

    private String priceRange;
    private String moqStr;
    private JSONArray imgArr;

    ArrayList<ProModlModel> model;

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    @SerializedName("indexId")
    @Expose
    private Integer indexId;

    private String uniqueId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public ArrayList<ProModlModel> getModel() {
        return model;
    }

    public void setModel(ArrayList<ProModlModel> model) {
        this.model = model;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getMoqStr() {
        return moqStr;
    }

    public void setMoqStr(String moqStr) {
        this.moqStr = moqStr;
    }

    public JSONArray getImgArr() {
        return imgArr;
    }

    public void setImgArr(JSONArray imgArr) {
        this.imgArr = imgArr;
    }
}