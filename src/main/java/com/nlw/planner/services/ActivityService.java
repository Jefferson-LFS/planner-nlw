package com.nlw.planner.services;

import com.nlw.planner.api.dto.ActivityRegisterResponseDTO;
import com.nlw.planner.api.dto.ActivityRequestDTO;
import com.nlw.planner.api.dto.ActivityResponseDTO;
import com.nlw.planner.model.activity.Activity;
import com.nlw.planner.model.trip.Trip;
import com.nlw.planner.repositories.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public ActivityRegisterResponseDTO registerActivity(ActivityRequestDTO activityRequestDTO, Trip trip) {

        Activity newActivity =  new Activity(activityRequestDTO.title(), activityRequestDTO.occursAt(), trip);

        this.activityRepository.save(newActivity);

        return new ActivityRegisterResponseDTO(newActivity.getId());
    }


    public List<ActivityResponseDTO> getAllActivitiesFromTrip (UUID tripId) {

        return this.activityRepository.findByTripId(tripId).stream().map(activity ->
                        new ActivityResponseDTO(
                                activity.getId(),
                                activity.getTitle(),
                                activity.getOccursAt()
                        )).toList();
    }

}
