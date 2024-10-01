package com.nlw.planner.api.controllers;

import com.nlw.planner.api.dto.*;
import com.nlw.planner.services.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripCreateResponseDTO> createTrip(@RequestBody TripRequestDTO tripRequestDTO,
                                                            UriComponentsBuilder uriComponentsBuilder) {

        TripCreateResponseDTO tripCreateResponseDTO = this.tripService.registerTrip(tripRequestDTO);

        var uri = uriComponentsBuilder.path("/trips/{tripId}").
                buildAndExpand(tripCreateResponseDTO.tripId()).
                toUri();

        return ResponseEntity.created(uri).body(tripCreateResponseDTO);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripResponseDTO> getTrip(@PathVariable UUID tripId) {

        TripResponseDTO trip = this.tripService.getTripDetails(tripId);
        return ResponseEntity.ok(trip);

    }

    @PutMapping("/{tripId}")
    public ResponseEntity<TripResponseDTO> updateTrip(@PathVariable UUID tripId, @RequestBody TripRequestDTO tripRequestDTO) {

        TripResponseDTO updatedTrip = this.tripService.updateTrip(tripId, tripRequestDTO);
        return ResponseEntity.ok(updatedTrip);
    }

    @GetMapping("/{tripId}/confirm")
    public ResponseEntity<TripResponseDTO> confirmTrip(@PathVariable UUID tripId) {

        TripResponseDTO confirmTrip = this.tripService.confirmTrip(tripId);
        return ResponseEntity.ok(confirmTrip);

    }

    @PostMapping("/{tripId}/invites")
    public ResponseEntity<ParticipantRegisterResponseDTO> inviteParticipant(@PathVariable UUID tripId, @RequestBody ParticipantRequestDTO ParticipantDTO) {

        ParticipantRegisterResponseDTO inviteParticipant = this.tripService.inviteParticipant(tripId, ParticipantDTO);
        return ResponseEntity.ok(inviteParticipant);

    }

    @GetMapping("/{tripId}/participants")
    public ResponseEntity<List<ParticipantResponseDTO>> getAllParticipants(@PathVariable UUID tripId) {

        List<ParticipantResponseDTO> participantList = this.tripService.getAllParticipants(tripId);
        return ResponseEntity.ok(participantList);
    }


    @PostMapping("/{tripId}/activities")
    public ResponseEntity<ActivityRegisterResponseDTO> registerActivity(@PathVariable UUID tripId, @RequestBody ActivityRequestDTO activityDTO) {

        ActivityRegisterResponseDTO activityRegisterResponseDTO = this.tripService.registerActivityTrip(tripId, activityDTO);
        return ResponseEntity.ok(activityRegisterResponseDTO);

    }

    @GetMapping("/{tripId}/activities")
    public ResponseEntity<List<ActivityResponseDTO>> getAllActivities(@PathVariable UUID tripId) {

        List<ActivityResponseDTO> activityList = this.tripService.getAllActivities(tripId);
        return ResponseEntity.ok(activityList);
    }


    @PostMapping("/{tripId}/links")
    public ResponseEntity<LinkRegisterResponseDTO> registerLink(@PathVariable UUID tripId, @RequestBody LinkRequestDTO linkDTO) {

        LinkRegisterResponseDTO linkRegisterResponseDTO = this.tripService.registerLink(tripId, linkDTO);
        return ResponseEntity.ok(linkRegisterResponseDTO);

    }

    @GetMapping("/{tripId}/links")
    public ResponseEntity<List<LinkResponseDTO>> getAllLinks(@PathVariable UUID tripId) {

        List<LinkResponseDTO> linkList = this.tripService.getAllLinks(tripId);
        return ResponseEntity.ok(linkList);
    }
}
