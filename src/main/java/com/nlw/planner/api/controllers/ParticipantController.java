package com.nlw.planner.api.controllers;

import com.nlw.planner.api.dto.participant.ParticipantRequestDTO;
import com.nlw.planner.api.dto.participant.ParticipantResponseDTO;
import com.nlw.planner.services.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService  participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<ParticipantResponseDTO> confirmParticipant(@PathVariable UUID id,
                                                                     @RequestBody ParticipantRequestDTO participantRequestDTO) {

        ParticipantResponseDTO confirmParticipant = this.participantService.
                confirmParticipant(id, participantRequestDTO);

        return ResponseEntity.ok(confirmParticipant);
    }



}
