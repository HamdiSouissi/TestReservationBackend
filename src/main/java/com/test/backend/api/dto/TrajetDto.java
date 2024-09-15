package com.test.backend.api.dto;

import java.time.LocalDateTime;

public record TrajetDto(Long id, Integer nbrPlaces, LocalDateTime departureTime, BusDto bus) {
}
