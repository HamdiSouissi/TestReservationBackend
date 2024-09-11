package com.testreservations.testreservation.reservation.dto;

import lombok.Data;

@Data
public class BillDTO {
    private Long id;
    private ReservationDTO reservation;
    private String paymentMethod;
}