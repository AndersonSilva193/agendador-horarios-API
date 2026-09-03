package com.coffee.agendador_barbearia.infrastructure.handlers;

import com.coffee.agendador_barbearia.exception.ScheduleFullException;
import com.coffee.agendador_barbearia.exception.ScheduleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ScheduleFullException.class)
    private ResponseEntity <String> scheduleFullHandler(ScheduleFullException exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Agendamento indisponivel (horário já reservado)");
    }

    @ExceptionHandler(ScheduleNotFoundException.class)
    private ResponseEntity <RestErrorMessage> scheduleNotFoundHandler(ScheduleNotFoundException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity <RestErrorMessage> RuntimeErrorHandler(RuntimeException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }

}
