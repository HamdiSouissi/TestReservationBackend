package com.testreservations.testreservation.reservation.service;

import com.testreservations.testreservation.reservation.dto.BillDTO;

import java.util.List;

public interface BillService {
    BillDTO createBill(Long reservationId, String paymentMethod);
    List<BillDTO> getAllBills();
}