package com.coffee.agendador_barbearia.infrastructure.entity.user;

import org.springframework.security.core.userdetails.UserDetails;

public record RegisterDTO(String login, String password, UserRole role) {
}
