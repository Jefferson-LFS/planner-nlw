package com.nlw.planner.api.controllers;

import com.nlw.planner.api.dto.TripCreateResponseDTO;
import com.nlw.planner.api.dto.TripResquestDTO;
import com.nlw.planner.model.trip.Trip;
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
}
