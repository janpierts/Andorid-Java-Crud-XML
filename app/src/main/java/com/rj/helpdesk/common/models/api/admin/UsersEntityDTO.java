package com.rj.helpdesk.common.models.api.admin;

import com.google.gson.annotations.SerializedName;

public class UsersEntityDTO {
    @SerializedName("id")
    public Long Id;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("created")
    public String created;
    @SerializedName("updated")
    public String updated;
    @SerializedName("state")
    public Boolean state;

}
