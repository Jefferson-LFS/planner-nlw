package com.nlw.planner.services;

import com.nlw.planner.api.dto.*;
import com.nlw.planner.model.trip.Trip;
import com.nlw.planner.model.trip.exceptions.TripNotFoundException;
import com.nlw.planner.repositories.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


@Service
public class TripService {

    private final TripRepository tripRepository;

    private final ParticipantService participantService;

    private final ActivityService activityService;

    private final LinkService linkService;

    public TripService(TripRepository tripRepository, ParticipantService participantService, ActivityService activityService, LinkService linkService) {
        this.tripRepository = tripRepository;
        this.participantService = participantService;
        this.activityService = activityService;
        this.linkService = linkService;
    }

    public TripCreateResponseDTO registerTrip(TripRequestDTO tripRequestDTO) {

        Trip trip = new Trip(tripRequestDTO);

        this.tripRepository.save(trip);
        this.participantService.registerParticipantsToTrip(tripRequestDTO.emailsToInvite(), trip);

        return new TripCreateResponseDTO(trip.getId());
    }

    public TripResponseDTO getTripDetails(UUID tripId) {

        Trip trip = this.getTripById(tripId);
        return new TripResponseDTO(trip);
    }

    public TripResponseDTO updateTrip(UUID tripId, TripRequestDTO tripRequestDTO) {

        Trip updatedTrip = this.getTripById(tripId);

        updatedTrip.setEndsAt(LocalDateTime.parse(tripRequestDTO.endsAt(), DateTimeFormatter.ISO_DATE_TIME));
        updatedTrip.setStartsAt(LocalDateTime.parse(tripRequestDTO.startsAt(), DateTimeFormatter.ISO_DATE_TIME));
        updatedTrip.setDestination(tripRequestDTO.destination());

        this.tripRepository.save(updatedTrip);

        return new TripResponseDTO (updatedTrip);
    }

    public TripResponseDTO confirmTrip(@PathVariable UUID tripId){

        Trip rawTrip = this.getTripById(tripId);

        rawTrip.setIsConfirmed(true);
        this.tripRepository.save(rawTrip);
        participantService.triggerConfirmationEmailToParticipants(tripId);

        return new TripResponseDTO(rawTrip);
    }

    private Trip getTripById(UUID tripId) {
        return this.tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException("Trip not found with ID: " + tripId));
    }

    public ParticipantRegisterResponseDTO  inviteParticipant(UUID tripId, ParticipantRequestDTO ParticipantDTO) {

        Trip rawTrip = this.getTripById(tripId);

        ParticipantRegisterResponseDTO participantRegisterResponseDTO =  this.participantService.registerParticipantToTrip(ParticipantDTO.email(), rawTrip);

        if(rawTrip.getIsConfirmed()) this.participantService.triggerConfirmationEmailToParticipant(ParticipantDTO.email());

        return participantRegisterResponseDTO;
    }

    public List<ParticipantResponseDTO> getAllParticipants(UUID tripId){

        Trip trip = this.getTripById(tripId);
        return this.participantService.getAllParticipantsFromTrip(trip.getId());
    }

    public ActivityRegisterResponseDTO registerActivityTrip(UUID tripId, ActivityRequestDTO activityDTO) {

        Trip rawTrip = this.getTripById(tripId);
        return this.activityService.registerActivity(activityDTO, rawTrip);
    }

    public List<ActivityResponseDTO> getAllActivities(UUID tripId){

        Trip trip = this.getTripById(tripId);
        return this.activityService.getAllActivitiesFromTrip(trip.getId());
    }


    public LinkRegisterResponseDTO registerLink(UUID tripId, LinkRequestDTO linkDTO) {

        Trip rawTrip = this.getTripById(tripId);
        return this.linkService.registerLink(linkDTO, rawTrip);
    }


    public List<LinkResponseDTO> getAllLinks(UUID tripId){

        Trip trip = this.getTripById(tripId);
        return this.linkService.getAllLinksFromTrip(trip.getId());
    }

}
