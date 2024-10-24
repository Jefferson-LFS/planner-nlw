package com.nlw.planner.api.dto;

import com.nlw.planner.model.trip.Trip;
import lombok.Getter;

@Getter
public class TripResponseDTO {

    TripDetailDTO trip;

    public TripResponseDTO(Trip trip) {
        this.trip = new TripDetailDTO(
                trip.getId(),
                trip.getDestination(),
                trip.getStartsAt(),
                trip.getEndsAt(),
                trip.getIsConfirmed(),
                trip.getOwnerName(),
                trip.getOwnerEmail());
    }
}
