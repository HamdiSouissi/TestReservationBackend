package com.testreservations.testreservation.reservation.repository;

import com.testreservations.testreservation.reservation.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {}
