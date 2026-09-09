package com.coffee.agendador_barbearia.controllers;

import com.coffee.agendador_barbearia.infrastructure.entity.user.AuthenticationDTO;
import com.coffee.agendador_barbearia.infrastructure.entity.user.LoginResponseDTO;
import com.coffee.agendador_barbearia.infrastructure.entity.user.RegisterDTO;
import com.coffee.agendador_barbearia.infrastructure.entity.user.User;
import com.coffee.agendador_barbearia.infrastructure.repository.UserRepository;
import com.coffee.agendador_barbearia.infrastructure.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TokenService tokenService;




    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Validated AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data){
        if (this.userRepository.findByLogin(data.login()) != null)
            return ResponseEntity.badRequest().build();

    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
    User newUser = new User(data.login(), encryptedPassword, data.role());
    this.userRepository.save(newUser);

    return ResponseEntity.ok().build();
    }
}
