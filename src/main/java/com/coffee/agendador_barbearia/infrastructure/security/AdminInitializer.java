package com.coffee.agendador_barbearia.infrastructure.security;

import com.coffee.agendador_barbearia.infrastructure.entity.user.User;
import com.coffee.agendador_barbearia.infrastructure.entity.user.UserRole;
import com.coffee.agendador_barbearia.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${api.admin.login}")
    private String adminLogin;

    @Value("${api.admin.password}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (userRepository.existsByRole(UserRole.BARBEIRO)) return;

        userRepository.save(new User(adminLogin, passwordEncoder.encode(adminPassword), UserRole.BARBEIRO));
    }
}
