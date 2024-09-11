package com.testreservations.testreservation.reservation.repository;

import com.testreservations.testreservation.reservation.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}
