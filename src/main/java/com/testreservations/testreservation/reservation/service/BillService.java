package com.testreservations.testreservation.reservation.service;

import com.testreservations.testreservation.reservation.dto.BillDTO;

import java.util.List;

public interface BillService {
    List<BillDTO> getAllBills();
    BillDTO createBill(Long reservationId, String paymentMethod);
}