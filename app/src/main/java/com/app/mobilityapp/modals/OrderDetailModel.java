package com.app.mobilityapp.modals;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class OrderDetailModel {

    @SerializedName("confirmation")
    @Expose
    private String confirmation;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Brand {

        @SerializedName("image")
        @Expose
        private Image_ image;
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


    public class CategoryId {

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
        private Object userPicture;
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

        public Object getUserPicture() {
            return userPicture;
        }

        public void setUserPicture(Object userPicture) {
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


    public class CreatedBy_ {

        @SerializedName("displayName")
        @Expose
        private String displayName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("_id")
        @Expose
        private String id;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }


    public class Data {

        @SerializedName("orderId")
        @Expose
        private String orderId;
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
        @SerializedName("orderStatus")
        @Expose
        private String orderStatus;
        @SerializedName("paidStatus")
        @Expose
        private Integer paidStatus;
        @SerializedName("isDeleted")
        @Expose
        private Boolean isDeleted;
        @SerializedName("createdBy")
        @Expose
        private CreatedBy createdBy;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("productdetails")
        @Expose
        private List<Productdetail> productdetails = null;
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

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public Integer getPaidStatus() {
            return paidStatus;
        }

        public void setPaidStatus(Integer paidStatus) {
            this.paidStatus = paidStatus;
        }

        public Boolean getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(Boolean isDeleted) {
            this.isDeleted = isDeleted;
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

        public List<Productdetail> getProductdetails() {
            return productdetails;
        }

        public void setProductdetails(List<Productdetail> productdetails) {
            this.productdetails = productdetails;
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


    public class Image_ {

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


    public class Image__ {

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


    public class Modalid {

        @SerializedName("image")
        @Expose
        private Image__ image;
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

    public class Modallist {

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


    public class ProductId {

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
        private Object subcategory2;
        @SerializedName("subcategory3")
        @Expose
        private Object subcategory3;
        @SerializedName("createdBy")
        @Expose
        private CreatedBy_ createdBy;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("brandDetails")
        @Expose
        private List<BrandDetail> brandDetails = null;
        @SerializedName("price")
        @Expose
        private List<Price> price = null;
        @SerializedName("image")
        @Expose
        private List<Image> image = null;
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

        public CreatedBy_ getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(CreatedBy_ createdBy) {
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


    public class Productdetail {

        @SerializedName("productId")
        @Expose
        private ProductId productId;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("sellerSeenStatus")
        @Expose
        private Integer sellerSeenStatus;
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
        @SerializedName("brandDetails")
        @Expose
        private List<BrandDetail_> brandDetails = null;
        @SerializedName("_id")
        @Expose
        private String id;

        public ProductId getProductId() {
            return productId;
        }

        public void setProductId(ProductId productId) {
            this.productId = productId;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getSellerSeenStatus() {
            return sellerSeenStatus;
        }

        public void setSellerSeenStatus(Integer sellerSeenStatus) {
            this.sellerSeenStatus = sellerSeenStatus;
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

        public List<BrandDetail_> getBrandDetails() {
            return brandDetails;
        }

        public void setBrandDetails(List<BrandDetail_> brandDetails) {
            this.brandDetails = brandDetails;
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

