package com.app.mobilityapp.modals;

import java.io.Serializable;

public class CartModel implements Serializable {
    private ProductsModal productsModal;
    private ProBrndModal proBrndModal;
    private ProModlModel proModlModel;
    private String imageUrl;
    private String price;

    public ProductsModal getProductsModal() {
        return productsModal;
    }

    public void setProductsModal(ProductsModal productsModal) {
        this.productsModal = productsModal;
    }

    public ProBrndModal getProBrndModal() {
        return proBrndModal;
    }

    public void setProBrndModal(ProBrndModal proBrndModal) {
        this.proBrndModal = proBrndModal;
    }

    public ProModlModel getProModlModel() {
        return proModlModel;
    }

    public void setProModlModel(ProModlModel proModlModel) {
        this.proModlModel = proModlModel;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
