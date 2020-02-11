package com.app.mobilityapp.app_utils;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Address;
import android.location.Geocoder;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.app.mobilityapp.modals.CartModel;
import com.app.mobilityapp.modals.LocalQuantityModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by PC on 7/1/2019.
 */

public class ConstantMethods {
    public static ProgressDialog progressDialog;
    public static void setTitleAndBack(AppCompatActivity activity, String title){
      activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.setTitle(title);
    }

    public static void showProgressbar(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
    public static void dismissProgressBar(){
        progressDialog.dismiss();
    }

    public static void setStringPreference(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringPreference(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd/MM/yyyy", cal).toString();
        return date;
    }
    public static String getDateAsWeb(String time) {
        long millisecond = Long.parseLong(time);
        String dateString = DateFormat.format("MMM dd, yyyy", new Date(millisecond)).toString();
        return dateString;
    }
    public static String getDateForComment(String time) {
        long millisecond = Long.parseLong(time);
        String dateString = DateFormat.format("MM/dd/yyyy hh:mm a", new Date(millisecond)).toString();
        return dateString;
    }


    public static String currentDate(){
        Date d = new Date();
        CharSequence s  = DateFormat.format("dd/MM/yyyy,hh:mm a", d.getTime());
        return String.valueOf(s);
    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("tag", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("tag", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("Tag", "printHashKey()", e);
        }
    }

    public static boolean isValidMail(String email) {
        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 6 && phone.length() <= 13;
        }
        return false;
    }

    public static Map<String, String> getUserType(){
        Map<String, String> map = new HashMap<>();
        map.put("3","Buyer");
        map.put("4","Seller");
        return map;
    }

    public static void twoColoredText(TextView textView, String blackStr, String coloredStr){
        String strClrd = "<b><font color='#FF4175C0'>"+ coloredStr +"</font></b>";
        String strBlk = blackStr +strClrd;
        textView.setText(Html.fromHtml(strBlk));
    }

    public static void setDate(EditText editText, Context context){
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1)
                        + "/" + String.valueOf(year);
                SimpleDateFormat input = new SimpleDateFormat("dd/MM/yy");
                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date oneWayTripDate = input.parse(date);
                    String dateo = output.format(oneWayTripDate);
                    String todayDate = todayDate();
                    boolean checkDate = isValidPastDate(dateo,todayDate);
                    if(checkDate) {
                        editText.setText(dateo);
                    }
                    else{
                        Toast.makeText(context, "Future date is not allowed", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, yy, mm, dd);
        datePicker.show();
    }

    public static String todayDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    private static boolean isValidPastDate(String selectedDateStr, String myDateStr){
        boolean futureDate = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateSlctd = null;
        Date dateMy = null;
        try {
            dateSlctd = sdf.parse(selectedDateStr);
            dateMy = sdf.parse(myDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("date1 : " + sdf.format(dateSlctd));
        System.out.println("date2 : " + sdf.format(dateMy));

        if (dateSlctd.compareTo(dateMy) > 0) {
            futureDate = false;
        } else if (dateSlctd.compareTo(dateMy) < 0) {
            futureDate = true;
        } else if (dateSlctd.compareTo(dateMy) == 0) {
            futureDate = false;
        } else {
            System.out.println("How to get here?");
        }
        return futureDate;
    }

    public static void saveArrayListShared(List<CartModel> list, Context context, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public static List<CartModel> getArrayListShared(Context context,String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<CartModel>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void saveQtyListShared(List<LocalQuantityModel> list, Context context, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public static List<LocalQuantityModel> getQtyArrayListShared(Context context,String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<LocalQuantityModel>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public static void getAlertMessage(Context context,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    dialog.dismiss();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static String changeDateFormate(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static boolean checkAddress(String address) {
        boolean valid = false;
        char[] strChr = address.toCharArray();
        for (int i = 0; i < strChr.length; i++) {
            if (strChr[i] >= 'a' && strChr[i] <= 'z' || (strChr[i] >= 'A' && strChr[i] <= 'Z')) {
                Log.e("value", "" + strChr[i]);
                valid = true;
                break;
            } else {
                valid = false;
            }
        }
        return valid;
    }

    public static boolean isFirstLetterMobileNum(String mobileNum){
        boolean valid = false;
        char[] strChr = mobileNum.toCharArray();
        if(strChr[0]=='5'||strChr[0]=='6'||strChr[0]=='7'||strChr[0]=='8'||strChr[0]=='9'){
            valid = true;
        }
        else {
            valid = false;
        }
        return valid;
    }
}
