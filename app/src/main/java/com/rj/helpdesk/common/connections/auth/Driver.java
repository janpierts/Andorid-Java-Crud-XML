package com.rj.helpdesk.common.connections.auth;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Driver {
    @GET(".")
    Call<Void> loginConnect();
}
