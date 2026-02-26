package com.rj.helpdesk.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Patterns; // Importante para validar URLs

public class PreferenceUtils {
    private static final String PREFS_NAME = "helpdesk_prefs";
    private static final String KEY_API_URL = "api_url";

    public static boolean isValidUrl(String url) {
        return url != null &&
                Patterns.WEB_URL.matcher(url).matches() &&
                (url.startsWith("http://") || url.startsWith("https://"));
    }

    public static void saveApiUrl(Context context, String apiUrl) {
        if (isValidUrl(apiUrl)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_API_URL, apiUrl);
            editor.apply();
        }
    }

    public static String getApiUrl(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_API_URL, "");
    }

    public static String getTrailingSlashIfNeeded(Context context) {
        String url = getApiUrl(context);
        // Si la URL está vacía o ya termina en "/", no agregamos nada
        if (url == null || url.isEmpty() || url.endsWith("/")) {
            return "";
        }
        return "/";
    }
}