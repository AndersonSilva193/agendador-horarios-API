package com.coffee.agendador_barbearia.exception;

public class ScheduleFullException extends RuntimeException{

    public ScheduleFullException(){
        super("Agendamento indisponivel (horário já reservado)");
    }
}
