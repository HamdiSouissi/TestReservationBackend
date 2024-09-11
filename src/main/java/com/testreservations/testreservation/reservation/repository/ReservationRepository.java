package com.testreservations.testreservation.reservation.repository;

import com.testreservations.testreservation.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {}
