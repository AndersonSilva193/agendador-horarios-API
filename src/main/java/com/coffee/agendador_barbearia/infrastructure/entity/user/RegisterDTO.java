package com.coffee.agendador_barbearia.infrastructure.entity.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDTO(@NotBlank String login, @NotBlank @Size(min = 6) String password) {
}
