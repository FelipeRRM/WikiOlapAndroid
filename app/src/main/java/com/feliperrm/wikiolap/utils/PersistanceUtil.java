package com.feliperrm.wikiolap.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by felip on 04/04/2017.
 */

public class PersistanceUtil {

    private static final String FILE = "sharedpreferences";

    public static void saveSharedPreferences(Context context, String key, String dado){
        if(context==null) return;
        SharedPreferences shared = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(key, dado);
        editor.apply();
    }

    public static  void clearSharedPreference(Context context) {
        SharedPreferences shared = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.commit();
    }

    public static void deleteSharedPreference(Context context, String key){
        SharedPreferences shared = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.remove(key);
        editor.commit();
    }

    public static String loadSharedPreference(Context context, String key, String defaultReturn){
        SharedPreferences shared = context.getSharedPreferences(FILE, Context.MODE_PRIVATE);
        return shared.getString(key, defaultReturn);
    }

}
