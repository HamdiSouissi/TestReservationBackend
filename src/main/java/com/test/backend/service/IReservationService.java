package com.test.backend.service;

import com.test.backend.api.dto.ReservationDto;
import java.util.List;
import java.util.Optional;

public interface IReservationService {

    Optional<ReservationDto> createReservation(ReservationDto reservationDto);

    Optional<ReservationDto> updateReservation(Long id, ReservationDto reservationDto);

    Optional<ReservationDto> getReservation(Long id);

    List<ReservationDto> getReservationsByClientId(Long clientId);

    void deleteReservation(Long id);

}
