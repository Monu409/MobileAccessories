package com.app.mobilityapp.app_utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by coderzlab on 16/7/15.
 */
public class Methods {


    public static boolean saveTOTALPRICE(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("TOTALPRICE",cif).commit();
    }
    public static String getTOTALPRICE(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("TOTALPRICE", null);
    }

    public static boolean saveCOUNT(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("COUNT",cif).commit();
    }
    public static String getCOUNT(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("COUNT", null);
    }



    public static boolean saveNAME(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("NAME",cif).commit();
    }
    public static String getNAME(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("NAME", null);
    }

    public static boolean saveMOBILE(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("MOBILE",cif).commit();
    }
    public static String getMOBILE(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("MOBILE", null);
    }
    public static boolean saveUSERID(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("userid",cif).commit();
    }
    public static String getUSERID(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("userid", null);
    }



    public static boolean saveGAINING(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("gainpoint",cif).commit();
    }
    public static String getGAINING(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("gainpoint", null);
    }



    public static boolean saveEMAIL(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("EMAIL",cif).commit();
    }
    public static String getEMAIL(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("EMAIL", null);
    }
    public static String deleteEMAIL(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        return sharedPreferences.getString("EMAIL", null);
    }


    public static boolean saveORDERID(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("ORDER",cif).commit();
    }
    public static String getORDERID(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("ORDER", null);
    }


    public static boolean saveCITY(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("CITY",cif).commit();
    }
    public static String getCITY(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("CITY", null);
    }


    public static boolean saveADDRESS1(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("ADDRESS1",cif).commit();
    }
    public static String getADDRESS1(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("ADDRESS1", null);
    }


    public static boolean saveADDRESS2(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("ADDRESS2",cif).commit();
    }
    public static String getADDRESS2(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("ADDRESS2", null);
    }

    public static boolean saveHOUSENO(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("HOUSENO",cif).commit();
    }
    public static String getHOUSENO(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("HOUSENO", null);
    }

    public static boolean saveSTATE(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("STATE",cif).commit();
    }
    public static String getSTATE(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("STATE", null);
    }


    public static boolean savePIN(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("PIN",cif).commit();
    }
    public static String getPIN(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("PIN", null);
    }



    public static boolean saveDID(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("ID",cif).commit();
    }
    public static String getDID(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("ID", null);
    }



    public static boolean savePOINTCUSTOMERID(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("ID",cif).commit();
    }
    public static String getPOINTCUSTOMERID(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("ID", null);
    }






    public static boolean saveDNAME(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("DNAME",cif).commit();
    }
    public static String getDNAME(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("DNAME", null);
    }

    public static boolean saveDMOBILE(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("DMOBILE",cif).commit();
    }
    public static String getDMOBILE(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("DMOBILE", null);
    }
    public static boolean saveDEMAIL(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("DEMAIL",cif).commit();
    }
    public static String getDEMAIL(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("DEMAIL", null);
    }
    public static boolean saveDPASSWORD(Context context, String cif){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.edit().putString("DPASSWORD",cif).commit();
    }
    public static String getDPASSWORD(Context context){
        SharedPreferences sharedPreferences=getPreferances(context);
        return sharedPreferences.getString("DPASSWORD", null);
    }





    public  static SharedPreferences getPreferances(Context context){
       return context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);

    }
}
