package com.coffee.agendador_barbearia;

import com.coffee.agendador_barbearia.infrastructure.entity.user.UserRole;
import com.coffee.agendador_barbearia.infrastructure.repository.UserRepository;
import com.coffee.agendador_barbearia.infrastructure.security.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "api.security.token.secret=segredo-apenas-para-testes",
        "spring.datasource.url=jdbc:h2:mem:testdb"
})
@AutoConfigureMockMvc
class AuthenticationRolesTests {

    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;
    @Autowired TokenService tokenService;

    private String json(String login) {
        return "{\"login\":\"" + login + "\",\"password\":\"senha123\"}";
    }

    @Test
    void cadastroPublicoIgnoraRoleEnviadaECriaCliente() throws Exception {
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"fulano\",\"password\":\"senha123\",\"role\":\"BARBEIRO\"}"))
                .andExpect(status().isOk());

        var user = (com.coffee.agendador_barbearia.infrastructure.entity.user.User) userRepository.findByLogin("fulano");
        assertEquals(UserRole.CLIENTE, user.getRole());
    }

    @Test
    void cadastroDeBarbeiroSemLoginEhNegado() throws Exception {
        mockMvc.perform(post("/auth/register/barbeiro").contentType(MediaType.APPLICATION_JSON)
                        .content(json("intruso")))
                .andExpect(status().isForbidden());
    }

    @Test
    void cadastroDeBarbeiroComTokenDeBarbeiroFunciona() throws Exception {
        var admin = (com.coffee.agendador_barbearia.infrastructure.entity.user.User) userRepository.findByLogin("barbeiro");
        String token = tokenService.generateToken(admin);

        mockMvc.perform(post("/auth/register/barbeiro").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(json("novo-barbeiro")))
                .andExpect(status().isOk());
    }

    @Test
    void senhaCurtaEhRejeitada() throws Exception {
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"x\",\"password\":\"123\"}"))
                .andExpect(status().isBadRequest());
    }
}
