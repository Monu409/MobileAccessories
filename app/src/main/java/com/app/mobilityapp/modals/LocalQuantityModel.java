package com.app.mobilityapp.modals;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalQuantityModel {
    @SerializedName("productid")
    @Expose
    private String productid;
    @SerializedName("brandid")
    @Expose
    private String brandid;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total_qty")
    @Expose
    private Integer totalQty;
    @SerializedName("modallist")
    @Expose
    private List<Modallist> modallist = null;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Integer totalQty) {
        this.totalQty = totalQty;
    }

    public List<Modallist> getModallist() {
        return modallist;
    }

    public void setModallist(List<Modallist> modallist) {
        this.modallist = modallist;
    }

    public class Modallist {

        @SerializedName("modalid")
        @Expose
        private String modalid;
        @SerializedName("quantity")
        @Expose
        private String quantity;

        public String getModalid() {
            return modalid;
        }

        public void setModalid(String modalid) {
            this.modalid = modalid;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

    }
}
