package com.rj.helpdesk.common.models.mappers.admin;

import com.rj.helpdesk.common.models.api.admin.UsersEntityDTO;
import com.rj.helpdesk.common.models.domain.admin.Users;

import java.util.ArrayList;
import java.util.List;

public class UsersMappers {
    public static Users toDomain(UsersEntityDTO dto) {
        if (dto == null) return null;
        return new Users(dto.name, dto.email);
    }

    // Convierte una lista completa (útil para el ListAllUsers)
    public static List<Users> toDomainList(List<UsersEntityDTO> dtos) {
        List<Users> userslist = new ArrayList<>();
        if (dtos != null) {
            for (UsersEntityDTO dto : dtos) {
                userslist.add(toDomain(dto));
            }
        }
        return userslist;
    }
}
