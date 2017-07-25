package com.example.user.myalarm;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by USER on 7/20/2017.
 */

public class PrefsHelper {

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PrefsHelper.class.getSimpleName(), 0);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        getPrefs(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key) {
        return getPrefs(context).getBoolean(key, false);
    }
}
