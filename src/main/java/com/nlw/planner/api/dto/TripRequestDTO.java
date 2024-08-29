package com.nlw.planner.api.dto;

import java.util.List;

public record TripRequestDTO(
        String destination,
        String startsAt,
        String endsAt,
        List<String> emailsToInvite,
        Boolean isConfirmed,
        String ownerName,
        String ownerEmail

        ) {
}
