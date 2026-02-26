package com.rj.helpdesk.common.network.auth.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthService {
    @GET(".")
    Call<Void> loginConnect();
}
