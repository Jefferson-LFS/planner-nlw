package com.nlw.planner.api.dto;


import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityResponseDTO(UUID id, String title, LocalDateTime occursAt) {
}
