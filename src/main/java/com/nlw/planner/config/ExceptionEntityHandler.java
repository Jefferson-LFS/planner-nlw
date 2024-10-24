package com.nlw.planner.config;

import com.nlw.planner.api.dto.geral.ErrorResponseDTO;
import com.nlw.planner.model.participant.exceptions.ParticipantNotFoundException;
import com.nlw.planner.model.trip.exceptions.TripDateException;
import com.nlw.planner.model.trip.exceptions.TripNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {

    @ExceptionHandler(TripNotFoundException.class)
    public ResponseEntity<Object>  handleTripNotFound(TripNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(ParticipantNotFoundException.class)
    public ResponseEntity<Object>  handleParticipantNotFound(ParticipantNotFoundException exception) {
        return ResponseEntity.notFound().build();
    }


    @ExceptionHandler(TripDateException.class)
    public ResponseEntity<ErrorResponseDTO> handleTripDate(TripDateException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }
}
