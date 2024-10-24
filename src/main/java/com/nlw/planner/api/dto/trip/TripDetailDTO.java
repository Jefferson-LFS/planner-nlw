package com.nlw.planner.api.dto;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record TripDetailDTO(
        UUID id,
        String destination,
        LocalDateTime startsAt,
        LocalDateTime endsAt,
        Boolean isConfirmed,
        String ownerName,
        String ownerEmail

) {
}
