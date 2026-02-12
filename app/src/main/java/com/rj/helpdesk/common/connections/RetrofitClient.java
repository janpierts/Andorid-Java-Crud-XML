package com.rj.helpdesk.common.connections;

import android.content.Context;
import com.rj.helpdesk.common.utils.PreferenceUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        String baseUrl = PreferenceUtils.getApiUrl(context);
        
        // Retrofit requiere que la URL base termine en /
        if (!baseUrl.isEmpty() && !baseUrl.endsWith("/")) {
            baseUrl += "/";
        }

        // Si la URL es vacía, podrías manejar un error o devolver una por defecto
        if (baseUrl.isEmpty()) {
            baseUrl = "http://localhost/"; // Fallback para evitar crash al construir
        }

        // Reconstruimos el cliente si la URL ha cambiado o es la primera vez
        if (retrofit == null || !retrofit.baseUrl().toString().equals(baseUrl)) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient.Builder().build())
                    .build();
        }
        
        return retrofit;
    }

    public static <T> T createService(Context context, Class<T> serviceClass) {
        return getClient(context).create(serviceClass);
    }
}
