package com.test.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trajet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int nbrPlaces;

    private LocalDateTime departureTime;

    private double trajetPrice;

    @OneToOne(cascade = CascadeType.MERGE)
    private Bus bus;

}
