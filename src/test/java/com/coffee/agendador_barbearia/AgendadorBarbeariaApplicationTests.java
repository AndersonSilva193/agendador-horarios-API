package com.coffee.agendador_barbearia;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "api.security.token.secret=segredo-apenas-para-testes")
class AgendadorBarbeariaApplicationTests {

	@Test
	void contextLoads() {
	}

}
