package com.testreservations.testreservation.reservation.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Reservation reservation;
    private String paymentMethod; // "PayPal" ou "Credit Card"
}
