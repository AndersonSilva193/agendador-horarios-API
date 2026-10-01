package com.coffee.agendador_barbearia.infrastructure.entity.user;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@NotBlank String login, @NotBlank String password) {
}
