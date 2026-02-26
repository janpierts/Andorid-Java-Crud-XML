package com.rj.helpdesk.common.network.admin.service;

import com.rj.helpdesk.common.models.api.admin.UsersEntityDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AdminService {
    @GET("inMysqlAdapter_JPA/find/all")
    Call<List<UsersEntityDTO>> listAllUsers();
}
