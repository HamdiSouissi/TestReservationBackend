package com.testreservations.testreservation.reservation.service;

import com.testreservations.testreservation.reservation.dto.ReservationDTO;

import java.util.List;

public interface ReservationService {

    List<ReservationDTO> getAllReservations();

    ReservationDTO getReservationById(Long id);

    ReservationDTO createReservation(ReservationDTO reservationDTO);

    ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO);

    void deleteReservation(Long id);

    boolean payReservation(Long reservationId, String paymentMethod);
}