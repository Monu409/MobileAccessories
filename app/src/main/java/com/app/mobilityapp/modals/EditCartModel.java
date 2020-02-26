package com.app.mobilityapp.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


//public class EditCartModel implements Serializable {

//    @SerializedName("confirmation")
//    @Expose
//    private String confirmation;
//    @SerializedName("data")
//    @Expose
//    private ChildEditCartModel data;
//
//    public String getConfirmation() {
//        return confirmation;
//    }
//
//    public void setConfirmation(String confirmation) {
//        this.confirmation = confirmation;
//    }
//
//    public ChildEditCartModel getData() {
//        return data;
//    }
//
//    public void setData(ChildEditCartModel data) {
//        this.data = data;
//    }
//
//    public class Brand implements Serializable {
//
//        @SerializedName("image")
//        @Expose
//        private Image___ image;
//        @SerializedName("name")
//        @Expose
//        private String name;
//        @SerializedName("content")
//        @Expose
//        private String content;
//        @SerializedName("imgUrl")
//        @Expose
//        private String imgUrl;
//        @SerializedName("status")
//        @Expose
//        private Boolean status;
//        @SerializedName("isDeleted")
//        @Expose
//        private Boolean isDeleted;
//        @SerializedName("createdBy")
//        @Expose
//        private Object createdBy;
//        @SerializedName("_id")
//        @Expose
//        private String id;
//        @SerializedName("createdAt")
//        @Expose
//        private String createdAt;
//        @SerializedName("updatedAt")
//        @Expose
//        private String updatedAt;
//        @SerializedName("__v")
//        @Expose
//        private Integer v;
//
//        public Image___ getImage() {
//            return image;
//        }
//
//        public void setImage(Image___ image) {
//            this.image = image;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public void setContent(String content) {
//            this.content = content;
//        }
//
//        public String getImgUrl() {
//            return imgUrl;
//        }
//
//        public void setImgUrl(String imgUrl) {
//            this.imgUrl = imgUrl;
//        }
//
//        public Boolean getStatus() {
//            return status;
//        }
//
//        public void setStatus(Boolean status) {
//            this.status = status;
//        }
//
//        public Boolean getIsDeleted() {
//            return isDeleted;
//        }
//
//        public void setIsDeleted(Boolean isDeleted) {
//            this.isDeleted = isDeleted;
//        }
//
//        public Object getCreatedBy() {
//            return createdBy;
//        }
//
//        public void setCreatedBy(Object createdBy) {
//            this.createdBy = createdBy;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getCreatedAt() {
//            return createdAt;
//        }
//
//        public void setCreatedAt(String createdAt) {
//            this.createdAt = createdAt;
//        }
//
//        public String getUpdatedAt() {
//            return updatedAt;
//        }
//
//        public void setUpdatedAt(String updatedAt) {
//            this.updatedAt = updatedAt;
//        }
//
//        public Integer getV() {
//            return v;
//        }
//
//        public void setV(Integer v) {
//            this.v = v;
//        }
//
//    }
//
//    public class BrandDetail implements Serializable {
//
//        @SerializedName("brand")
//        @Expose
//        private Brand brand;
//        @SerializedName("modallist")
//        @Expose
//        private List<Modallist> modallist = null;
//        @SerializedName("_id")
//        @Expose
//        private String id;
//
//        public Brand getBrand() {
//            return brand;
//        }
//
//        public void setBrand(Brand brand) {
//            this.brand = brand;
//        }
//
//        public List<Modallist> getModallist() {
//            return modallist;
//        }
//
//        public void setModallist(List<Modallist> modallist) {
//            this.modallist = modallist;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//    }
//
//    public class CategoryId implements Serializable {
//
//        @SerializedName("image")
//        @Expose
//        private Image_ image;
//        @SerializedName("name")
//        @Expose
//        private String name;
//        @SerializedName("content")
//        @Expose
//        private String content;
//        @SerializedName("moq")
//        @Expose
//        private String moq;
//        @SerializedName("status")
//        @Expose
//        private Boolean status;
//        @SerializedName("isDeleted")
//        @Expose
//        private Boolean isDeleted;
//        @SerializedName("createdBy")
//        @Expose
//        private String createdBy;
//        @SerializedName("_id")
//        @Expose
//        private String id;
//        @SerializedName("createdAt")
//        @Expose
//        private String createdAt;
//        @SerializedName("updatedAt")
//        @Expose
//        private String updatedAt;
//        @SerializedName("__v")
//        @Expose
//        private Integer v;
//
//        public Image_ getImage() {
//            return image;
//        }
//
//        public void setImage(Image_ image) {
//            this.image = image;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public void setContent(String content) {
//            this.content = content;
//        }
//
//        public String getMoq() {
//            return moq;
//        }
//
//        public void setMoq(String moq) {
//            this.moq = moq;
//        }
//
//        public Boolean getStatus() {
//            return status;
//        }
//
//        public void setStatus(Boolean status) {
//            this.status = status;
//        }
//
//        public Boolean getIsDeleted() {
//            return isDeleted;
//        }
//
//        public void setIsDeleted(Boolean isDeleted) {
//            this.isDeleted = isDeleted;
//        }
//
//        public String getCreatedBy() {
//            return createdBy;
//        }
//
//        public void setCreatedBy(String createdBy) {
//            this.createdBy = createdBy;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getCreatedAt() {
//            return createdAt;
//        }
//
//        public void setCreatedAt(String createdAt) {
//            this.createdAt = createdAt;
//        }
//
//        public String getUpdatedAt() {
//            return updatedAt;
//        }
//
//        public void setUpdatedAt(String updatedAt) {
//            this.updatedAt = updatedAt;
//        }
//
//        public Integer getV() {
//            return v;
//        }
//
//        public void setV(Integer v) {
//            this.v = v;
//        }
//
//    }
//
//
//    public class ChildEditCartModel implements Serializable {
//        @SerializedName("productid")
//        @Expose
//        private Productid productid;
//        @SerializedName("price")
//        @Expose
//        private Integer price;
//        @SerializedName("categoryId")
//        @Expose
//        private CategoryId categoryId;
//        @SerializedName("subCategoryId")
//        @Expose
//        private SubCategoryId subCategoryId;
//        @SerializedName("subcategory2")
//        @Expose
//        private Object subcategory2;
//        @SerializedName("subcategory3")
//        @Expose
//        private Object subcategory3;
//        @SerializedName("isDeleted")
//        @Expose
//        private Boolean isDeleted;
//        @SerializedName("createdBy")
//        @Expose
//        private String createdBy;
//        @SerializedName("_id")
//        @Expose
//        private String id;
//        @SerializedName("brandDetails")
//        @Expose
//        private List<BrandDetail> brandDetails = null;
//        @SerializedName("createdAt")
//        @Expose
//        private String createdAt;
//        @SerializedName("updatedAt")
//        @Expose
//        private String updatedAt;
//        @SerializedName("__v")
//        @Expose
//        private Integer v;
//
//        public Productid getProductid() {
//            return productid;
//        }
//
//        public void setProductid(Productid productid) {
//            this.productid = productid;
//        }
//
//        public Integer getPrice() {
//            return price;
//        }
//
//        public void setPrice(Integer price) {
//            this.price = price;
//        }
//
//        public CategoryId getCategoryId() {
//            return categoryId;
//        }
//
//        public void setCategoryId(CategoryId categoryId) {
//            this.categoryId = categoryId;
//        }
//
//        public SubCategoryId getSubCategoryId() {
//            return subCategoryId;
//        }
//
//        public void setSubCategoryId(SubCategoryId subCategoryId) {
//            this.subCategoryId = subCategoryId;
//        }
//
//        public Object getSubcategory2() {
//            return subcategory2;
//        }
//
//        public void setSubcategory2(Object subcategory2) {
//            this.subcategory2 = subcategory2;
//        }
//
//        public Object getSubcategory3() {
//            return subcategory3;
//        }
//
//        public void setSubcategory3(Object subcategory3) {
//            this.subcategory3 = subcategory3;
//        }
//
//        public Boolean getIsDeleted() {
//            return isDeleted;
//        }
//
//        public void setIsDeleted(Boolean isDeleted) {
//            this.isDeleted = isDeleted;
//        }
//
//        public String getCreatedBy() {
//            return createdBy;
//        }
//
//        public void setCreatedBy(String createdBy) {
//            this.createdBy = createdBy;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public List<BrandDetail> getBrandDetails() {
//            return brandDetails;
//        }
//
//        public void setBrandDetails(List<BrandDetail> brandDetails) {
//            this.brandDetails = brandDetails;
//        }
//
//        public String getCreatedAt() {
//            return createdAt;
//        }
//
//        public void setCreatedAt(String createdAt) {
//            this.createdAt = createdAt;
//        }
//
//        public String getUpdatedAt() {
//            return updatedAt;
//        }
//
//        public void setUpdatedAt(String updatedAt) {
//            this.updatedAt = updatedAt;
//        }
//
//        public Integer getV() {
//            return v;
//        }
//
//        public void setV(Integer v) {
//            this.v = v;
//        }
//
//    }
//
//    public class Image implements Serializable {
//
//        @SerializedName("imgName")
//        @Expose
//        private Object imgName;
//        @SerializedName("imgUrl")
//        @Expose
//        private Object imgUrl;
//        @SerializedName("imgColor")
//        @Expose
//        private String imgColor;
//        @SerializedName("imagename")
//        @Expose
//        private String imagename;
//        @SerializedName("imageurl")
//        @Expose
//        private String imageurl;
//        @SerializedName("_id")
//        @Expose
//        private String id;
//
//        public Object getImgName() {
//            return imgName;
//        }
//
//        public void setImgName(Object imgName) {
//            this.imgName = imgName;
//        }
//
//        public Object getImgUrl() {
//            return imgUrl;
//        }
//
//        public void setImgUrl(Object imgUrl) {
//            this.imgUrl = imgUrl;
//        }
//
//        public String getImgColor() {
//            return imgColor;
//        }
//
//        public void setImgColor(String imgColor) {
//            this.imgColor = imgColor;
//        }
//
//        public String getImagename() {
//            return imagename;
//        }
//
//        public void setImagename(String imagename) {
//            this.imagename = imagename;
//        }
//
//        public String getImageurl() {
//            return imageurl;
//        }
//
//        public void setImageurl(String imageurl) {
//            this.imageurl = imageurl;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//    }
//
//
//    public class Image_ implements Serializable {
//
//        @SerializedName("imagename")
//        @Expose
//        private String imagename;
//        @SerializedName("imageurl")
//        @Expose
//        private String imageurl;
//
//        public String getImagename() {
//            return imagename;
//        }
//
//        public void setImagename(String imagename) {
//            this.imagename = imagename;
//        }
//
//        public String getImageurl() {
//            return imageurl;
//        }
//
//        public void setImageurl(String imageurl) {
//            this.imageurl = imageurl;
//        }
//
//    }
//
//    public class Image__ implements Serializable {
//
//        @SerializedName("imagename")
//        @Expose
//        private String imagename;
//        @SerializedName("imageurl")
//        @Expose
//        private String imageurl;
//
//        public String getImagename() {
//            return imagename;
//        }
//
//        public void setImagename(String imagename) {
//            this.imagename = imagename;
//        }
//
//        public String getImageurl() {
//            return imageurl;
//        }
//
//        public void setImageurl(String imageurl) {
//            this.imageurl = imageurl;
//        }
//
//    }
//
//    public class Image___ implements Serializable {
//
//        @SerializedName("imagename")
//        @Expose
//        private String imagename;
//        @SerializedName("imageurl")
//        @Expose
//        private String imageurl;
//
//        public String getImagename() {
//            return imagename;
//        }
//
//        public void setImagename(String imagename) {
//            this.imagename = imagename;
//        }
//
//        public String getImageurl() {
//            return imageurl;
//        }
//
//        public void setImageurl(String imageurl) {
//            this.imageurl = imageurl;
//        }
//
//    }
//
//
//    public class Image____ implements Serializable {
//
//        @SerializedName("imagename")
//        @Expose
//        private Object imagename;
//        @SerializedName("imageurl")
//        @Expose
//        private Object imageurl;
//
//        public Object getImagename() {
//            return imagename;
//        }
//
//        public void setImagename(Object imagename) {
//            this.imagename = imagename;
//        }
//
//        public Object getImageurl() {
//            return imageurl;
//        }
//
//        public void setImageurl(Object imageurl) {
//            this.imageurl = imageurl;
//        }
//
//    }
//
//    public class Modalid implements Serializable {
//
//        @SerializedName("image")
//        @Expose
//        private Image____ image;
//        @SerializedName("name")
//        @Expose
//        private String name;
//        @SerializedName("content")
//        @Expose
//        private String content;
//        @SerializedName("status")
//        @Expose
//        private Boolean status;
//        @SerializedName("isDeleted")
//        @Expose
//        private Boolean isDeleted;
//        @SerializedName("brandId")
//        @Expose
//        private String brandId;
//        @SerializedName("createdBy")
//        @Expose
//        private Object createdBy;
//        @SerializedName("_id")
//        @Expose
//        private String id;
//        @SerializedName("createdAt")
//        @Expose
//        private String createdAt;
//        @SerializedName("updatedAt")
//        @Expose
//        private String updatedAt;
//        @SerializedName("__v")
//        @Expose
//        private Integer v;
//
//        public Image____ getImage() {
//            return image;
//        }
//
//        public void setImage(Image____ image) {
//            this.image = image;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public void setContent(String content) {
//            this.content = content;
//        }
//
//        public Boolean getStatus() {
//            return status;
//        }
//
//        public void setStatus(Boolean status) {
//            this.status = status;
//        }
//
//        public Boolean getIsDeleted() {
//            return isDeleted;
//        }
//
//        public void setIsDeleted(Boolean isDeleted) {
//            this.isDeleted = isDeleted;
//        }
//
//        public String getBrandId() {
//            return brandId;
//        }
//
//        public void setBrandId(String brandId) {
//            this.brandId = brandId;
//        }
//
//        public Object getCreatedBy() {
//            return createdBy;
//        }
//
//        public void setCreatedBy(Object createdBy) {
//            this.createdBy = createdBy;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getCreatedAt() {
//            return createdAt;
//        }
//
//        public void setCreatedAt(String createdAt) {
//            this.createdAt = createdAt;
//        }
//
//        public String getUpdatedAt() {
//            return updatedAt;
//        }
//
//        public void setUpdatedAt(String updatedAt) {
//            this.updatedAt = updatedAt;
//        }
//
//        public Integer getV() {
//            return v;
//        }
//
//        public void setV(Integer v) {
//            this.v = v;
//        }
//
//    }
//
//
//    public class Modallist implements Serializable {
//
//        @SerializedName("modalid")
//        @Expose
//        private Modalid modalid;
//        @SerializedName("quantity")
//        @Expose
//        private Integer quantity;
//        @SerializedName("_id")
//        @Expose
//        private String id;
//
//        public Modalid getModalid() {
//            return modalid;
//        }
//
//        public void setModalid(Modalid modalid) {
//            this.modalid = modalid;
//        }
//
//        public Integer getQuantity() {
//            return quantity;
//        }
//
//        public void setQuantity(Integer quantity) {
//            this.quantity = quantity;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//    }
//
//    public class Productid implements Serializable {
//
//        @SerializedName("name")
//        @Expose
//        private String name;
//        @SerializedName("content")
//        @Expose
//        private String content;
//        @SerializedName("_id")
//        @Expose
//        private String id;
//        @SerializedName("image")
//        @Expose
//        private List<Image> image = null;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public void setContent(String content) {
//            this.content = content;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public List<Image> getImage() {
//            return image;
//        }
//
//        public void setImage(List<Image> image) {
//            this.image = image;
//        }
//
//    }
//
//    public class SubCategoryId implements Serializable {
//
//        @SerializedName("image")
//        @Expose
//        private Image__ image;
//        @SerializedName("name")
//        @Expose
//        private String name;
//        @SerializedName("content")
//        @Expose
//        private String content;
//        @SerializedName("moq")
//        @Expose
//        private String moq;
//        @SerializedName("status")
//        @Expose
//        private Boolean status;
//        @SerializedName("isDeleted")
//        @Expose
//        private Boolean isDeleted;
//        @SerializedName("categoryId")
//        @Expose
//        private String categoryId;
//        @SerializedName("createdBy")
//        @Expose
//        private String createdBy;
//        @SerializedName("_id")
//        @Expose
//        private String id;
//        @SerializedName("createdAt")
//        @Expose
//        private String createdAt;
//        @SerializedName("updatedAt")
//        @Expose
//        private String updatedAt;
//        @SerializedName("__v")
//        @Expose
//        private Integer v;
//
//        public Image__ getImage() {
//            return image;
//        }
//
//        public void setImage(Image__ image) {
//            this.image = image;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public void setContent(String content) {
//            this.content = content;
//        }
//
//        public String getMoq() {
//            return moq;
//        }
//
//        public void setMoq(String moq) {
//            this.moq = moq;
//        }
//
//        public Boolean getStatus() {
//            return status;
//        }
//
//        public void setStatus(Boolean status) {
//            this.status = status;
//        }
//
//        public Boolean getIsDeleted() {
//            return isDeleted;
//        }
//
//        public void setIsDeleted(Boolean isDeleted) {
//            this.isDeleted = isDeleted;
//        }
//
//        public String getCategoryId() {
//            return categoryId;
//        }
//
//        public void setCategoryId(String categoryId) {
//            this.categoryId = categoryId;
//        }
//
//        public String getCreatedBy() {
//            return createdBy;
//        }
//
//        public void setCreatedBy(String createdBy) {
//            this.createdBy = createdBy;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getCreatedAt() {
//            return createdAt;
//        }
//
//        public void setCreatedAt(String createdAt) {
//            this.createdAt = createdAt;
//        }
//
//        public String getUpdatedAt() {
//            return updatedAt;
//        }
//
//        public void setUpdatedAt(String updatedAt) {
//            this.updatedAt = updatedAt;
//        }
//
//        public Integer getV() {
//            return v;
//        }
//
//        public void setV(Integer v) {
//            this.v = v;
//        }
//
//    }
//
//    public class Price implements Serializable {
//
//        @SerializedName("from")
//        @Expose
//        private String from;
//        @SerializedName("to")
//        @Expose
//        private String to;
//        @SerializedName("amount")
//        @Expose
//        private String amount;
//        @SerializedName("_id")
//        @Expose
//        private String id;
//
//        public String getFrom() {
//            return from;
//        }
//
//        public void setFrom(String from) {
//            this.from = from;
//        }
//
//        public String getTo() {
//            return to;
//        }
//
//        public void setTo(String to) {
//            this.to = to;
//        }
//
//        public String getAmount() {
//            return amount;
//        }
//
//        public void setAmount(String amount) {
//            this.amount = amount;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//    }
//













    public class EditCartModel implements Serializable{

        @SerializedName("confirmation")
        @Expose
        private String confirmation;
        @SerializedName("data")
        @Expose
        private ChildEditCartModel data;

        public String getConfirmation() {
            return confirmation;
        }

        public void setConfirmation(String confirmation) {
            this.confirmation = confirmation;
        }

        public ChildEditCartModel getData() {
            return data;
        }

        public void setData(ChildEditCartModel data) {
            this.data = data;
        }
        public class Brand implements Serializable {

            @SerializedName("image")
            @Expose
            private Image___ image;
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
            @SerializedName("__v")
            @Expose
            private Integer v;

            public Image___ getImage() {
                return image;
            }

            public void setImage(Image___ image) {
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

        }


        public class BrandDetail implements Serializable {

            @SerializedName("brand")
            @Expose
            private Brand brand;
            @SerializedName("modallist")
            @Expose
            private List<Modallist> modallist = null;
            @SerializedName("_id")
            @Expose
            private String id;

            public Brand getBrand() {
                return brand;
            }

            public void setBrand(Brand brand) {
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


        public class ChildEditCartModel implements Serializable {

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
            private Object subcategory2;
            @SerializedName("subcategory3")
            @Expose
            private Object subcategory3;
            @SerializedName("isDeleted")
            @Expose
            private Boolean isDeleted;
            @SerializedName("createdBy")
            @Expose
            private String createdBy;
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

            @SerializedName("imgName")
            @Expose
            private Object imgName;
            @SerializedName("imgUrl")
            @Expose
            private Object imgUrl;
            @SerializedName("imgColor")
            @Expose
            private String imgColor;
            @SerializedName("imagename")
            @Expose
            private String imagename;
            @SerializedName("imageurl")
            @Expose
            private String imageurl;
            @SerializedName("_id")
            @Expose
            private String id;

            public Object getImgName() {
                return imgName;
            }

            public void setImgName(Object imgName) {
                this.imgName = imgName;
            }

            public Object getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(Object imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getImgColor() {
                return imgColor;
            }

            public void setImgColor(String imgColor) {
                this.imgColor = imgColor;
            }

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


        public class Image_ implements Serializable {

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


        public class Image__ implements Serializable {

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

        public class Image___ implements Serializable {

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


        public class Image____ implements Serializable {

            @SerializedName("imagename")
            @Expose
            private Object imagename;
            @SerializedName("imageurl")
            @Expose
            private Object imageurl;

            public Object getImagename() {
                return imagename;
            }

            public void setImagename(Object imagename) {
                this.imagename = imagename;
            }

            public Object getImageurl() {
                return imageurl;
            }

            public void setImageurl(Object imageurl) {
                this.imageurl = imageurl;
            }

        }


        public class Modalid implements Serializable {

            @SerializedName("image")
            @Expose
            private Image____ image;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("content")
            @Expose
            private String content;
            @SerializedName("status")
            @Expose
            private Boolean status;
            @SerializedName("isDeleted")
            @Expose
            private Boolean isDeleted;
            @SerializedName("brandId")
            @Expose
            private String brandId;
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
            @SerializedName("__v")
            @Expose
            private Integer v;

            public Image____ getImage() {
                return image;
            }

            public void setImage(Image____ image) {
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

            public String getBrandId() {
                return brandId;
            }

            public void setBrandId(String brandId) {
                this.brandId = brandId;
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

        }


        public class Modallist implements Serializable {

            @SerializedName("modalid")
            @Expose
            private Modalid modalid;
            @SerializedName("quantity")
            @Expose
            private Integer quantity;
            @SerializedName("_id")
            @Expose
            private String id;

            public Modalid getModalid() {
                return modalid;
            }

            public void setModalid(Modalid modalid) {
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


        public class Price implements Serializable {

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

        public class Productid implements Serializable {

            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("content")
            @Expose
            private String content;
            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("image")
            @Expose
            private List<Image> image = null;
            @SerializedName("price")
            @Expose
            private List<Price> price = null;

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

            public List<Price> getPrice() {
                return price;
            }

            public void setPrice(List<Price> price) {
                this.price = price;
            }

        }


        public class SubCategoryId implements Serializable {

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



