package com.coffee.agendador_barbearia.services;


import com.coffee.agendador_barbearia.exception.ScheduleFullException;
import com.coffee.agendador_barbearia.exception.ScheduleNotFoundException;
import com.coffee.agendador_barbearia.infrastructure.entity.Agendamento;
import com.coffee.agendador_barbearia.infrastructure.repository.AgendamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public Agendamento salvarAgendamento(Agendamento agendamento){

        LocalDateTime horaAgendamento = agendamento.getDataHoraAgendamento();
        LocalDateTime horaFim = agendamento.getDataHoraAgendamento().plusHours(1);


        Agendamento agendados = agendamentoRepository.findByServicoAndDataHoraAgendamentoBetween(agendamento.getServico(),
                horaAgendamento, horaFim);


        if (Objects.nonNull(agendados)){
            throw new ScheduleFullException();
        } else {
            return agendamentoRepository.save(agendamento);
        }
    }


    public void deletarAgendamento(LocalDateTime dataHoraAgendamento, String cliente){

        agendamentoRepository.deleteByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

    }


    public List<Agendamento> buscarAgendamentosDia(LocalDate data){
        LocalDateTime primeiraHoraDia = data.atStartOfDay();
        LocalDateTime horaFinalDia = data.atTime(23, 59);


        return agendamentoRepository.findByDataHoraAgendamentoBetween(primeiraHoraDia, horaFinalDia);
    }


    public Agendamento alterarAgendamento(Agendamento agendamento, String cliente,
                                          LocalDateTime dataHoraAgendamento) {

    Agendamento agenda = agendamentoRepository.findByDataHoraAgendamentoAndCliente(dataHoraAgendamento, cliente);

        if (Objects.isNull(agenda)){
            throw new ScheduleNotFoundException();
        } else {
            agendamento.setId(agenda.getId());
            return agendamentoRepository.save(agendamento);
        }


    }


}
