package com.esime.oflinemovies.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.esime.oflinemovies.Data.Remoto.ApiConstants;

public class SharedPreferenceManager {



    private SharedPreferenceManager(){

    }
    private static SharedPreferences getSharedPreference(){
        return MyApp.getContext().
                getSharedPreferences(ApiConstants.APP_SETTING_FILE, Context.MODE_PRIVATE);
    }

    public static void setSomeStringValue(String dataLabel, String dataValue){
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(dataLabel,dataValue);
        editor.commit();
    }

    public static void setSomeBooleanValue(String dataLabel, Boolean dataValue){
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(dataLabel,dataValue);
        editor.commit();
    }

    public static  String getSomeStringValue (String dataLabel){
        return getSharedPreference().getString(dataLabel,null);
    }

    public static  Boolean getSomeBooleanValue (String dataLabel){
        return getSharedPreference().getBoolean(dataLabel,false);
    }

    public static void ClearPreference(){
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.clear();
        editor.commit();
    }
}



