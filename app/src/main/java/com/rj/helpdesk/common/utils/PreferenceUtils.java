package com.rj.helpdesk.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
    private static final String PREFS_NAME = "helpdesk_prefs";
    private static final String KEY_API_URL = "api_url";

    public static void saveApiUrl(Context context, String apiUrl) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_API_URL, apiUrl);
        editor.apply();
    }
    public static String getApiUrl(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_API_URL, "");
    }
    public static String getTrailingSlashIfNeeded(Context context) {
        String url = getApiUrl(context);
        if (url != null && url.endsWith("/")) {
            return "";
        }
        return "/";
    }
}
