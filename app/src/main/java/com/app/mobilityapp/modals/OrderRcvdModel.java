package com.app.mobilityapp.modals;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderRcvdModel implements Serializable {

    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("data")
    @Expose
    private List<OrderRcvdModelChild> data = null;

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public List<OrderRcvdModelChild> getData() {
        return data;
    }

    public void setData(List<OrderRcvdModelChild> data) {
        this.data = data;
    }

    public class BrandDetail implements Serializable {

        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("modallist")
        @Expose
        private List<Modallist> modallist = null;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public List<Modallist> getModallist() {
            return modallist;
        }

        public void setModallist(List<Modallist> modallist) {
            this.modallist = modallist;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }


    public class CategoryId implements Serializable {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }


    public class OrderRcvdModelChild implements Serializable {

        @SerializedName("orderId")
        @Expose
        private OrderId orderId;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("netamount")
        @Expose
        private Integer netamount;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("shippingFees")
        @Expose
        private Object shippingFees;
        @SerializedName("shippingDiscount")
        @Expose
        private Object shippingDiscount;
        @SerializedName("paidStatus")
        @Expose
        private Integer paidStatus;
        @SerializedName("productId")
        @Expose
        private ProductId productId;
        @SerializedName("orderFor")
        @Expose
        private String orderFor;
        @SerializedName("categoryId")
        @Expose
        private CategoryId categoryId;
        @SerializedName("subCategoryId")
        @Expose
        private SubCategoryId subCategoryId;
        @SerializedName("subcategory2")
        @Expose
        private Object subcategory2;
        @SerializedName("subcategory3")
        @Expose
        private Object subcategory3;
        @SerializedName("isDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("brandDetails")
        @Expose
        private List<BrandDetail> brandDetails = null;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("__v")
        @Expose
        private Integer v;

        public OrderId getOrderId() {
            return orderId;
        }

        public void setOrderId(OrderId orderId) {
            this.orderId = orderId;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getNetamount() {
            return netamount;
        }

        public void setNetamount(Integer netamount) {
            this.netamount = netamount;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public Object getShippingFees() {
            return shippingFees;
        }

        public void setShippingFees(Object shippingFees) {
            this.shippingFees = shippingFees;
        }

        public Object getShippingDiscount() {
            return shippingDiscount;
        }

        public void setShippingDiscount(Object shippingDiscount) {
            this.shippingDiscount = shippingDiscount;
        }

        public Integer getPaidStatus() {
            return paidStatus;
        }

        public void setPaidStatus(Integer paidStatus) {
            this.paidStatus = paidStatus;
        }

        public ProductId getProductId() {
            return productId;
        }

        public void setProductId(ProductId productId) {
            this.productId = productId;
        }

        public String getOrderFor() {
            return orderFor;
        }

        public void setOrderFor(String orderFor) {
            this.orderFor = orderFor;
        }

        public CategoryId getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(CategoryId categoryId) {
            this.categoryId = categoryId;
        }

        public SubCategoryId getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(SubCategoryId subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public Object getSubcategory2() {
            return subcategory2;
        }

        public void setSubcategory2(Object subcategory2) {
            this.subcategory2 = subcategory2;
        }

        public Object getSubcategory3() {
            return subcategory3;
        }

        public void setSubcategory3(Object subcategory3) {
            this.subcategory3 = subcategory3;
        }

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<BrandDetail> getBrandDetails() {
            return brandDetails;
        }

        public void setBrandDetails(List<BrandDetail> brandDetails) {
            this.brandDetails = brandDetails;
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

    }

    public class Image implements Serializable {

        @SerializedName("imagename")
        @Expose
        private String imagename;
        @SerializedName("imageurl")
        @Expose
        private String imageurl;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getImagename() {
            return imagename;
        }

        public void setImagename(String imagename) {
            this.imagename = imagename;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }


    public class Modallist implements Serializable {

        @SerializedName("modalid")
        @Expose
        private String modalid;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getModalid() {
            return modalid;
        }

        public void setModalid(String modalid) {
            this.modalid = modalid;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }


    public class OrderId implements Serializable {

        @SerializedName("orderId")
        @Expose
        private String orderId;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }


    public class ProductId implements Serializable {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("image")
        @Expose
        private List<Image> image = null;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Image> getImage() {
            return image;
        }

        public void setImage(List<Image> image) {
            this.image = image;
        }

    }


    public class SubCategoryId implements Serializable {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

}


