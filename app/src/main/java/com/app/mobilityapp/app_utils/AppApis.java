package com.app.mobilityapp.app_utils;

public interface AppApis {
    //    String BASE_URL = "http://192.168.0.65:3000/api/category";
    String BASE_URL = "http://132.148.158.82:3004/";
//    String BASE_URL = "http://132.148.158.82:3004/api/orders";
    String SEND_OTP = BASE_URL+"auth/sendotp";
    String USER_LOGIN = BASE_URL+"auth/login";
    String GET_CATEGORY = BASE_URL+"custom/category";
    String GET_BRAND = BASE_URL+"custom/brand";
    String GET_COLOR = BASE_URL+"custom/color";
    String USER_SIGNUP = BASE_URL+"auth/usercreate";
    String FORGOT_PASSWORD = BASE_URL+"auth/forgotpassword";
    String PROFILE_UPDATE = BASE_URL+"api/user";
    String GET_PROFILE = BASE_URL+"custom/user";
    String GET_GLASS_DATA = BASE_URL+"custom/subCategory";
    String GET_GLASS_CAT_DATA=BASE_URL+"custom/subCategory2";
    String GET_GLASS_SUBCAT_DATA=BASE_URL+"custom/subCategory3";
    String GET_ALL_BRAND = BASE_URL+"api/brand";
    String GET_MODELS = BASE_URL+"custom/model";
    String UPLOAD_PROFILE_PIC = BASE_URL+"auth/Upload";
    String PRODUCT_DETAIL = BASE_URL+"custom/product";
    String GET_ALL_PROD_BRAND = BASE_URL+"api/product";
    String ADD_INTO_CART = BASE_URL+"api/cart";
    String PLACE_ORDER = BASE_URL+"api/order";
    String DELETE_CART = BASE_URL+"api/cart/";
    String ADDRESS_DATA = BASE_URL+"auth/list/city";
    String ORDER_LIST = BASE_URL+"api/order";
    String CREDIT_LIMIT = BASE_URL+"token/get/userinfo";
    String UPDATE_CART = BASE_URL+"api/cart/";
    String SEARCH_DATA = BASE_URL+"token/master/order/";
    String CREDIT_TRANSACTION = BASE_URL+"api/creditTransaction/";
    String ACCOUNT_TRANSACTION = BASE_URL+"api/payment/";
    String FILL_CART = BASE_URL+"token/getcartinfo/cart";
    String UPLOAD_PRODUCT = BASE_URL+"api/product";
    String SEND_MESSAGE = BASE_URL+"api/conversation";
    String GET_CONVERSATION_LIST = BASE_URL+"token/getmessage/conversation";
    String SEND_MEDIA_TO_CHAT = BASE_URL+"auth/commonupload/chating";
}
