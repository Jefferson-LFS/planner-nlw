package com.nlw.planner.api.dto.link;

import java.util.UUID;

public record LinkResponseDTO(UUID id, String title, String url) {
}
