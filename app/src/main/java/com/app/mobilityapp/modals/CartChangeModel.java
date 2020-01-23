package com.app.mobilityapp.modals;

import org.json.JSONArray;
import java.io.Serializable;
import java.util.List;

public class CartChangeModel implements Serializable {
    private String brandName;
    private String content;
    private String quantity;
    private String imgUrl;
    private String totalPrice;
    private String productId;
    private String brandId;
    private String jsonArray;
    private String cartId;
    private String catName;
    private List<ProductPriceModel> productPriceModels;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(String jsonArray) {
        this.jsonArray = jsonArray;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public List<ProductPriceModel> getProductPriceModels() {
        return productPriceModels;
    }

    public void setProductPriceModels(List<ProductPriceModel> productPriceModels) {
        this.productPriceModels = productPriceModels;
    }
}
