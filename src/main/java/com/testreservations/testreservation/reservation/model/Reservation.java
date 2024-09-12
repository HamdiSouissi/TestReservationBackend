package com.testreservations.testreservation.reservation.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name="reservation")
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name="date")
    private LocalDate travelDate;
}