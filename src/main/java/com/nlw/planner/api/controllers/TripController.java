package com.nlw.planner.api.controllers;

import com.nlw.planner.api.dto.*;
import com.nlw.planner.model.participant.Participant;
import com.nlw.planner.model.trip.Trip;
import com.nlw.planner.repositories.TripRepository;
import com.nlw.planner.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TripRepository tripRepository;

    @PostMapping
    public ResponseEntity<TripCreateResponseDTO> createTrip(@RequestBody TripResquestDTO tripResquestDTO) {
        Trip trip = new Trip(tripResquestDTO);

        this.tripRepository.save(trip);

        this.participantService.registerParticipantsToTrip(tripResquestDTO.emailsToInvite(), trip);


        return ResponseEntity.ok(new TripCreateResponseDTO(trip.getId()));

    }

    @PostMapping("/{tripId}/invites")
    public ResponseEntity<ParticipantRegisterResponseDTO> inviteParticipant(@PathVariable UUID tripId, @RequestBody ParticipantResquestDTO ParticipantDTO) {

        Optional<Trip> trip = this.tripRepository.findById(tripId);

        if(trip.isPresent()) {
            Trip rawTrip = trip.get();

            ParticipantRegisterResponseDTO participantRegisterResponseDTO =  this.participantService.registerParticipantToTrip(ParticipantDTO.email(), rawTrip);

            if(rawTrip.getIsConfirmed()) this.participantService.triggerConfirmationEmailToParticipant(ParticipantDTO.email());

            return ResponseEntity.ok(participantRegisterResponseDTO);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{tripId}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID tripId) {

        Optional<Trip> trip = this.tripRepository.findById(tripId);
        
        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{tripId}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID tripId, @RequestBody TripResquestDTO tripResquestDTO) {

        Optional<Trip> trip = this.tripRepository.findById(tripId);

        if(trip.isPresent()) {
            Trip updatedTrip = trip.get();
            updatedTrip.setEndsAt(LocalDateTime.parse(tripResquestDTO.endsAt(), DateTimeFormatter.ISO_DATE_TIME));
            updatedTrip.setStartsAt(LocalDateTime.parse(tripResquestDTO.startsAt(), DateTimeFormatter.ISO_DATE_TIME));
            updatedTrip.setDestination(tripResquestDTO.destination());

            this.tripRepository.save(updatedTrip);

            return ResponseEntity.ok(updatedTrip);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{tripId}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID tripId){
        Optional<Trip> trip = this.tripRepository.findById(tripId);

        if(trip.isPresent()) {
            Trip rawTrip = trip.get();
            rawTrip.setIsConfirmed(true);

            this.tripRepository.save(rawTrip);

            participantService.triggerConfirmationEmailToParticipants(tripId);

            return ResponseEntity.ok(rawTrip);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{tripId}/participants")
    public ResponseEntity<List<ParticipantResponseDTO>> getAllParticipants(@PathVariable UUID tripId){

        Optional<Trip> trip = this.tripRepository.findById(tripId);

        if(trip.isPresent()) {

            List<ParticipantResponseDTO> participantList = this.participantService.getAllParticipantsFromTrip(tripId);

            return ResponseEntity.ok(participantList);
        }

        return ResponseEntity.notFound().build();

    }
}
