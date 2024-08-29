package com.nlw.planner.services;

import com.nlw.planner.api.dto.ActivityRegisterResponseDTO;
import com.nlw.planner.api.dto.ActivityRequestDTO;
import com.nlw.planner.model.activities.Activity;
import com.nlw.planner.model.trip.Trip;
import com.nlw.planner.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public ActivityRegisterResponseDTO registerActivity(ActivityRequestDTO activityRequestDTO, Trip trip) {

        Activity newActivity =  new Activity(activityRequestDTO.title(), activityRequestDTO.occursAt(), trip);

        this.activityRepository.save(newActivity);

        return new ActivityRegisterResponseDTO(newActivity.getId());

    }



//    public List<ParticipantResponseDTO> getAllParticipantsFromTrip (UUID tripId) {
//
//        return this.participantRepository.findByTripId(tripId).stream().map(participant ->
//                        new ParticipantResponseDTO(
//                                participant.getId(),
//                                participant.getName(),
//                                participant.getEmail(),
//                                participant.getIsConfirmed()
//                        )).toList();
//    };




}
