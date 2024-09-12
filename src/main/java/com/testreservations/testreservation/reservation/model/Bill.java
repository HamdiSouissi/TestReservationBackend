package com.testreservations.testreservation.reservation.model;


import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "bills")
@Data
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_id", nullable = false)
    private Long reservationId;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "amount", nullable = false)
    private double amount;
}