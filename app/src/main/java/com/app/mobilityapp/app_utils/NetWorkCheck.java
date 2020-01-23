package com.app.mobilityapp.app_utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.util.Log;

import androidx.annotation.NonNull;

public class NetWorkCheck {
	ConnectivityManager connectivityManager;
	 NetworkInfo wifiInfo;
	 NetworkInfo mobileInfo;


	public static boolean checkConnection(@NonNull Context context) {
		return  ((ConnectivityManager) context.getSystemService
				(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
	}

	public Boolean checkNow(Context con) {

		try {
			connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
			wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

			if (wifiInfo.isConnected() || mobileInfo.isConnected()) {
				return true;
			}
		} catch (Exception e) {
			//Log.e("CheckConnectivity Exception: ", e.getMessage());
		}

		return false;
	}

	public boolean isOnline(Context con) {

		boolean connected = false;
		try {
			connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
			return connected;

		} catch (Exception e) {
			System.out.println("CheckConnectivity Exception: " + e.getMessage());
			Log.v("connectivity", e.toString());
		}

		return connected;
	}

}