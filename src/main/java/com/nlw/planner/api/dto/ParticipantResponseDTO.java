package com.nlw.planner.api.dto;


import java.util.UUID;

public record ParticipantResponseDTO(UUID id, String name, String email, Boolean  isConfirmed) {
}
