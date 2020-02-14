package com.app.mobilityapp.modals;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;





public class CartNewModel {

    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("data")
    @Expose
    private List<CartChildModel> data = null;

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public List<CartChildModel> getData() {
        return data;
    }

    public void setData(List<CartChildModel> data) {
        this.data = data;
    }
    public class CartChildModel {

        @SerializedName("productid")
        @Expose
        private Productid productid;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("categoryId")
        @Expose
        private CategoryId categoryId;
        @SerializedName("subCategoryId")
        @Expose
        private SubCategoryId subCategoryId;
        @SerializedName("subcategory2")
        @Expose
        private Subcategory2 subcategory2;
        @SerializedName("subcategory3")
        @Expose
        private Object subcategory3;
        @SerializedName("isDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("createdBy")
        @Expose
        private Object createdBy;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("brandDetails")
        @Expose
        private List<BrandDetail_> brandDetails = null;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("__v")
        @Expose
        private Integer v;

        public Productid getProductid() {
            return productid;
        }

        public void setProductid(Productid productid) {
            this.productid = productid;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
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

        public Subcategory2 getSubcategory2() {
            return subcategory2;
        }

        public void setSubcategory2(Subcategory2 subcategory2) {
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

        public List<BrandDetail_> getBrandDetails() {
            return brandDetails;
        }

        public void setBrandDetails(List<BrandDetail_> brandDetails) {
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
    public class BrandDetail {

        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("model")
        @Expose
        private List<String> model = null;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public List<String> getModel() {
            return model;
        }

        public void setModel(List<String> model) {
            this.model = model;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }
    public class BrandDetail_ {

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

    public class CategoryId {

        @SerializedName("image")
        @Expose
        private Image image;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("moq")
        @Expose
        private String moq;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("isDeleted")
        @Expose
        private Boolean isDeleted;
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

        public Image getImage() {
            return image;
        }

        public void setImage(Image image) {
            this.image = image;
        }

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

        public String getMoq() {
            return moq;
        }

        public void setMoq(String moq) {
            this.moq = moq;
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

    }
    public class Image {

        @SerializedName("imagename")
        @Expose
        private String imagename;
        @SerializedName("imageurl")
        @Expose
        private String imageurl;

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

    }

    public class Image_ {

        @SerializedName("imagename")
        @Expose
        private String imagename;
        @SerializedName("imageurl")
        @Expose
        private String imageurl;

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

    }

    public class Image__ {

        @SerializedName("imagename")
        @Expose
        private String imagename;
        @SerializedName("imageurl")
        @Expose
        private String imageurl;

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

    }


    public class Modallist {

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


    public class Price {

        @SerializedName("from")
        @Expose
        private String from;
        @SerializedName("to")
        @Expose
        private String to;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("_id")
        @Expose
        private String id;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    public class Productid {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("colour")
        @Expose
        private List<String> colour = null;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("isDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("categoryId")
        @Expose
        private String categoryId;
        @SerializedName("subCategoryId")
        @Expose
        private String subCategoryId;
        @SerializedName("subcategory2")
        @Expose
        private String subcategory2;
        @SerializedName("subcategory3")
        @Expose
        private Object subcategory3;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("image")
        @Expose
        private List<Object> image = null;
        @SerializedName("price")
        @Expose
        private List<Price> price = null;
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

        public List<String> getColour() {
            return colour;
        }

        public void setColour(List<String> colour) {
            this.colour = colour;
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

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
        }

        public String getSubcategory2() {
            return subcategory2;
        }

        public void setSubcategory2(String subcategory2) {
            this.subcategory2 = subcategory2;
        }

        public Object getSubcategory3() {
            return subcategory3;
        }

        public void setSubcategory3(Object subcategory3) {
            this.subcategory3 = subcategory3;
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

        public List<Object> getImage() {
            return image;
        }

        public void setImage(List<Object> image) {
            this.image = image;
        }

        public List<Price> getPrice() {
            return price;
        }

        public void setPrice(List<Price> price) {
            this.price = price;
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


    public class SubCategoryId {

        @SerializedName("image")
        @Expose
        private Image_ image;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("moq")
        @Expose
        private String moq;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("isDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("categoryId")
        @Expose
        private String categoryId;
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

        public Image_ getImage() {
            return image;
        }

        public void setImage(Image_ image) {
            this.image = image;
        }

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

        public String getMoq() {
            return moq;
        }

        public void setMoq(String moq) {
            this.moq = moq;
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

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
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

    }


    public class Subcategory2 {

        @SerializedName("image")
        @Expose
        private Image__ image;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("moq")
        @Expose
        private String moq;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("isDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("categoryId")
        @Expose
        private String categoryId;
        @SerializedName("subCategoryId")
        @Expose
        private String subCategoryId;
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

        public Image__ getImage() {
            return image;
        }

        public void setImage(Image__ image) {
            this.image = image;
        }

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

        public String getMoq() {
            return moq;
        }

        public void setMoq(String moq) {
            this.moq = moq;
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

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getSubCategoryId() {
            return subCategoryId;
        }

        public void setSubCategoryId(String subCategoryId) {
            this.subCategoryId = subCategoryId;
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

    }

}

