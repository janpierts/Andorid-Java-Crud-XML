package com.rj.helpdesk.common.connections.auth;

import android.content.Context;
import com.rj.helpdesk.common.connections.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthConnectionManager {

    public interface ConnectionCallback {
        void onResult(boolean isSuccess);
    }

    public static void testConnection(Context context, ConnectionCallback callback) {
        Driver driver = RetrofitClient.createService(context, Driver.class);

        driver.loginConnect().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                boolean isOnline = response.isSuccessful() || response.code() == 404;
                callback.onResult(isOnline);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onResult(false);
            }
        });
    }
}
