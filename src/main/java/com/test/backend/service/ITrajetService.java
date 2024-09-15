package com.test.backend.service;

import com.test.backend.api.dto.TrajetDto;
import java.util.List;
import java.util.Optional;

public interface ITrajetService {

    Optional<TrajetDto> createTrajet(TrajetDto trajetDto);

    Optional<TrajetDto> updateTrajet(Long id, TrajetDto trajetDto);

    Optional<TrajetDto> getTrajetById(Long id);

    List<TrajetDto> getAllTrajets();

    void deleteTrajet(Long id);
}
