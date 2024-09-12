package com.testreservations.testreservation.reservation.dto;

import lombok.Data;

@Data
public class BillDTO {
    private Long id;
    private Long reservationId;
    private String paymentMethod;
    private Double amount;

}