package com.coffee.agendador_barbearia.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table (name = "agendamento")
@Entity

public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String servico;
    private String profissional;
    private String cliente;
    private String telefoneCliente;
    private LocalDateTime dataHoraAgendamento;
    private LocalDateTime dataIncersao = LocalDateTime.now();

}
