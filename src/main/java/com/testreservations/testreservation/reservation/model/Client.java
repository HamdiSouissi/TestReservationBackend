package com.testreservations.testreservation.reservation.model;

import  jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;
}
