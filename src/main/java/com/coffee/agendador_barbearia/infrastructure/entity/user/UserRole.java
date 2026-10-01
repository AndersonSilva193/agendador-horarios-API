package com.coffee.agendador_barbearia.infrastructure.entity.user;

public enum UserRole {

    BARBEIRO ("barbeiro"),
    CLIENTE ("cliente");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
