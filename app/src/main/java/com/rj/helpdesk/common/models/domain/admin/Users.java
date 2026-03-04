package com.rj.helpdesk.common.models.domain.admin;

public class Users {
    private Long Id;
    private String nombre;
    private String email;
    private String created;
    private String updated;
    private Boolean state;
    public Users(Long Id, String nombre, String email, String created, String updated, Boolean state) {
        this.Id = Id;
        this.nombre = nombre;
        this.email = email;
        this.created = created;
        this.updated = updated;
        this.state = state;
    }
    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        this.Id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCreated() { return created; }
    public void setCreated(String created) { this.created = created; }
    public String getUpdated() { return updated; }
    public void setUpdated(String updated) { this.updated = updated; }
    public Boolean getState() { return state; }
    public void setState(Boolean state) { this.state = state; }
}
