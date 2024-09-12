package com.testreservations.testreservation.reservation.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Data
@Table(name="bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="bus_number")
    private String busNumber;

    @Column(name="seats")
    private int seats;

    @Column(name="departure_time")
    private LocalTime departureTime;

    @Column(name="price")
    private double price;
}