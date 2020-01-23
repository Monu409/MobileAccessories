package com.app.mobilityapp.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddedBrands {
    @SerializedName("model")
    @Expose
    ArrayList<String> model_ids;
    @SerializedName("brand")
    @Expose
    String brand_id;

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public ArrayList<String> getModel_ids() {
        return model_ids;
    }

    public void addModel_ids(String model_id) {
        if (model_ids == null)
            model_ids = new ArrayList<>();
        if (!model_ids.contains(model_id))
            model_ids.add(model_id);
    }
}
