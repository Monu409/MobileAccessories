package com.app.mobilityapp.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyProductModel{

    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("data")
    @Expose
    private List<MyProductChild> data = null;

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public List<MyProductChild> getData() {
        return data;
    }

    public void setData(List<MyProductChild> data) {
        this.data = data;
    }

    public class Brand {

        @SerializedName("image")
        @Expose
        private Image image;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

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

    }

    public class BrandDetail {

        @SerializedName("brand")
        @Expose
        private Brand brand;
        @SerializedName("model")
        @Expose
        private List<Model> model = null;
        @SerializedName("_id")
        @Expose
        private String id;

        public Brand getBrand() {
            return brand;
        }

        public void setBrand(Brand brand) {
            this.brand = brand;
        }

        public List<Model> getModel() {
            return model;
        }

        public void setModel(List<Model> model) {
            this.model = model;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    public class CategoryId {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }

    public class CreatedBy {

        @SerializedName("displayName")
        @Expose
        private String displayName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("emailVerify")
        @Expose
        private Boolean emailVerify;
        @SerializedName("phoneVerify")
        @Expose
        private Boolean phoneVerify;
        @SerializedName("dob")
        @Expose
        private Object dob;
        @SerializedName("otp")
        @Expose
        private Integer otp;
        @SerializedName("gstno")
        @Expose
        private String gstno;
        @SerializedName("deviceid")
        @Expose
        private String deviceid;
        @SerializedName("userPicture")
        @Expose
        private String userPicture;
        @SerializedName("panPicture")
        @Expose
        private Object panPicture;
        @SerializedName("adharPicture")
        @Expose
        private Object adharPicture;
        @SerializedName("sellerOrgName")
        @Expose
        private String sellerOrgName;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("isDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("userType")
        @Expose
        private Integer userType;
        @SerializedName("sellerType")
        @Expose
        private Boolean sellerType;
        @SerializedName("creditLimit")
        @Expose
        private Integer creditLimit;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("requestSeller")
        @Expose
        private Boolean requestSeller;
        @SerializedName("addressId")
        @Expose
        private String addressId;
        @SerializedName("userRollId")
        @Expose
        private String userRollId;
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

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Boolean getEmailVerify() {
            return emailVerify;
        }

        public void setEmailVerify(Boolean emailVerify) {
            this.emailVerify = emailVerify;
        }

        public Boolean getPhoneVerify() {
            return phoneVerify;
        }

        public void setPhoneVerify(Boolean phoneVerify) {
            this.phoneVerify = phoneVerify;
        }

        public Object getDob() {
            return dob;
        }

        public void setDob(Object dob) {
            this.dob = dob;
        }

        public Integer getOtp() {
            return otp;
        }

        public void setOtp(Integer otp) {
            this.otp = otp;
        }

        public String getGstno() {
            return gstno;
        }

        public void setGstno(String gstno) {
            this.gstno = gstno;
        }

        public String getDeviceid() {
            return deviceid;
        }

        public void setDeviceid(String deviceid) {
            this.deviceid = deviceid;
        }

        public String getUserPicture() {
            return userPicture;
        }

        public void setUserPicture(String userPicture) {
            this.userPicture = userPicture;
        }

        public Object getPanPicture() {
            return panPicture;
        }

        public void setPanPicture(Object panPicture) {
            this.panPicture = panPicture;
        }

        public Object getAdharPicture() {
            return adharPicture;
        }

        public void setAdharPicture(Object adharPicture) {
            this.adharPicture = adharPicture;
        }

        public String getSellerOrgName() {
            return sellerOrgName;
        }

        public void setSellerOrgName(String sellerOrgName) {
            this.sellerOrgName = sellerOrgName;
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

        public Integer getUserType() {
            return userType;
        }

        public void setUserType(Integer userType) {
            this.userType = userType;
        }

        public Boolean getSellerType() {
            return sellerType;
        }

        public void setSellerType(Boolean sellerType) {
            this.sellerType = sellerType;
        }

        public Integer getCreditLimit() {
            return creditLimit;
        }

        public void setCreditLimit(Integer creditLimit) {
            this.creditLimit = creditLimit;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public Boolean getRequestSeller() {
            return requestSeller;
        }

        public void setRequestSeller(Boolean requestSeller) {
            this.requestSeller = requestSeller;
        }

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }

        public String getUserRollId() {
            return userRollId;
        }

        public void setUserRollId(String userRollId) {
            this.userRollId = userRollId;
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

    public class MyProductChild implements Serializable{

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("colour")
        @Expose
        private List<Object> colour;
        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("isDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("categoryId")
        @Expose
        private CategoryId categoryId;
        @SerializedName("subCategoryId")
        @Expose
        private SubCategoryId subCategoryId;
        @SerializedName("subcategory2")
        @Expose
        private SubCategoryId subcategory2;
        @SerializedName("subcategory3")
        @Expose
        private Object subcategory3;
        @SerializedName("createdBy")
        @Expose
        private CreatedBy createdBy;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("brandDetails")
        @Expose
        private List<BrandDetail> brandDetails ;
        @SerializedName("price")
        @Expose
        private List<Price> price ;
        @SerializedName("image")
        @Expose
        private List<Image> image ;
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

        public List<Object> getColour() {
            return colour;
        }

        public void setColour(List<Object> colour) {
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

        public SubCategoryId getSubcategory2() {
            return subcategory2;
        }

        public void setSubcategory2(SubCategoryId subcategory2) {
            this.subcategory2 = subcategory2;
        }

        public Object getSubcategory3() {
            return subcategory3;
        }

        public void setSubcategory3(Object subcategory3) {
            this.subcategory3 = subcategory3;
        }

        public CreatedBy getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(CreatedBy createdBy) {
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

        public List<Price> getPrice() {
            return price;
        }

        public void setPrice(List<Price> price) {
            this.price = price;
        }

        public List<Image> getImage() {
            return image;
        }

        public void setImage(List<Image> image) {
            this.image = image;
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

    public class Model {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("brandId")
        @Expose
        private String brandId;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

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

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
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

    public class SubCategoryId {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("moq")
        @Expose
        private String moq;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoq() {
            return moq;
        }

        public void setMoq(String moq) {
            this.moq = moq;
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

    }

}





