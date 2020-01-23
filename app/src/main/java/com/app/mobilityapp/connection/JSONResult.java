package com.app.mobilityapp.connection;

import androidx.annotation.NonNull;

import com.androidnetworking.error.ANError;

import org.json.JSONObject;

public interface JSONResult {
    void notifySuccess(@NonNull JSONObject response);
    void notifyError(@NonNull ANError anError);
}