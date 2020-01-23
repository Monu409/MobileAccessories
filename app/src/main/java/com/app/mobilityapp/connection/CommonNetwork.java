package com.app.mobilityapp.connection;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.app.mobilityapp.app_utils.ConstantMethods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import static com.app.mobilityapp.app_utils.AppApis.BASE_URL;

public class CommonNetwork {
    JSONResult mResultCallback = null;
    static Context mContext;

    public CommonNetwork(JSONResult resultCallback, Context context){
        mResultCallback = resultCallback;
        mContext = context;
    }

    public static void getNetworkJsonObj(String url,JSONResult mResultCallback,Context context){
        String usertoken = ConstantMethods.getStringPreference("user_token",context);
        AndroidNetworking
                .get(url)
                .addHeaders("Authorization", usertoken)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResultCallback.notifySuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mResultCallback.notifyError(anError);
                    }
                });
    }

    public static void postNetworkJsonObj(String url,JSONObject jsonObject,JSONResult mResultCallback,Context context){
        String usertoken = ConstantMethods.getStringPreference("user_token",context);
        AndroidNetworking
                .post(url)
                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", usertoken)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResultCallback.notifySuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mResultCallback.notifyError(anError);
                    }
                });
    }

    public static void postNetworkJsonArr(String url, JSONArray jsonArray, JSONResult mResultCallback,Context context){
        String usertoken = ConstantMethods.getStringPreference("user_token",context);
        AndroidNetworking
                .post(url)
                .addJSONArrayBody(jsonArray)
                .addHeaders("Authorization",usertoken)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResultCallback.notifySuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mResultCallback.notifyError(anError);
                    }
                });
    }

    public static void putNetworkJsonObj(String url,JSONObject jsonObject,JSONResult mResultCallback,Context context){
        String usertoken = ConstantMethods.getStringPreference("user_token",context);
        AndroidNetworking
                .put(url)
                .addJSONObjectBody(jsonObject)
                .addHeaders("Authorization", usertoken)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResultCallback.notifySuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mResultCallback.notifyError(anError);
                    }
                });
    }

    public static void deleteNetworkJsonObj(String url,JSONResult mResultCallback,Context context){
        String usertoken = ConstantMethods.getStringPreference("user_token",context);
        AndroidNetworking
                .delete(url)
                .addHeaders("Authorization",usertoken)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResultCallback.notifySuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mResultCallback.notifyError(anError);
                    }
                });
    }

    public static void putNetworkFileType(String url, File file,String userId, JSONResult mResultCallback){
        AndroidNetworking
                .put(url)
//                .addFileBody(file)
                .addBodyParameter("userPicture","kjkhfjdhjkfijiofjjfor")
                .addBodyParameter("login_id",userId)
                .addBodyParameter("baseurl",BASE_URL+"images/profile/")
//                .addMultipartFile("userPicture",file)
//                .addMultipartParameter("login_id",userId)
//                .addMultipartParameter("baseurl",BASE_URL+"images/profile/")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResultCallback.notifySuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        mResultCallback.notifyError(anError);
                    }
                });
    }
}
