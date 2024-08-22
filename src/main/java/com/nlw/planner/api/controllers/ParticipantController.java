package com.nlw.planner.api.controllers;

import com.nlw.planner.api.dto.ParticipantResquestDTO;
import com.nlw.planner.api.dto.TripCreateResponseDTO;
import com.nlw.planner.api.dto.TripResquestDTO;
import com.nlw.planner.model.participant.Participant;
import com.nlw.planner.model.trip.Trip;
import com.nlw.planner.repositories.ParticipantRepository;
import com.nlw.planner.repositories.TripRepository;
import com.nlw.planner.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ParticipantRepository participantRepository;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id,
                                                          @RequestBody ParticipantResquestDTO participantResquestDTO) {
        Optional<Participant> participant = this.participantRepository.findById(id);

        if(participant.isPresent()) {
            Participant rawParticipant = participant.get();
            rawParticipant.setIsConfirmed(true);
            rawParticipant.setName(participantResquestDTO.name());

            this.participantRepository.save(rawParticipant);

            return ResponseEntity.ok(rawParticipant);
        }
        return ResponseEntity.notFound().build();
    }




}
