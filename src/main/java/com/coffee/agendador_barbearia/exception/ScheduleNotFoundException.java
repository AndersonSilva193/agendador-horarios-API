package com.coffee.agendador_barbearia.exception;

public class ScheduleNotFoundException extends RuntimeException{

    public ScheduleNotFoundException(){
        super("Nenhum horário encontrado no sistema.");
    }


}
