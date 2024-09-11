package com.testreservations.testreservation.reservation.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate travelDate;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Bus bus;
}