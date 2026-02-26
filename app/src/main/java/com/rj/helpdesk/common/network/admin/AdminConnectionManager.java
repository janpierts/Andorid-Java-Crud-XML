package com.rj.helpdesk.common.network.admin;

import android.content.Context;

import com.rj.helpdesk.common.models.api.admin.UsersEntityDTO;
import com.rj.helpdesk.common.models.domain.admin.Users;
import com.rj.helpdesk.common.models.mappers.admin.UsersMappers;
import com.rj.helpdesk.common.network.RetrofitClient;
import com.rj.helpdesk.common.network.admin.service.AdminService;
import com.rj.helpdesk.common.network.auth.service.AuthService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminConnectionManager {
    public interface UsersCallback {
        void onSuccess(List<Users> usuarios);
        void onError(String mensaje);
    }
    public static void ListAllUsers(Context context, UsersCallback callback) {
        AdminService adminService = RetrofitClient.createService(context, AdminService.class);

        // El servicio ahora devuelve List<UsersEntityDTO>
        adminService.listAllUsers().enqueue(new Callback<List<UsersEntityDTO>>() {
            @Override
            public void onResponse(Call<List<UsersEntityDTO>> call, Response<List<UsersEntityDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // USAMOS EL MAPPER PARA LIMPIAR LOS DATOS
                    List<Users> listaLimpia = UsersMappers.toDomainList(response.body());
                    callback.onSuccess(listaLimpia);
                } else {
                    callback.onError("Error en el servidor: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<UsersEntityDTO>> call, Throwable t) {
                callback.onError("Fallo de red: " + t.getMessage());
            }
        });
    }
}
