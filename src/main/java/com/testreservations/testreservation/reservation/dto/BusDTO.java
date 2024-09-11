package com.testreservations.testreservation.reservation.dto;

import lombok.Data;
import java.time.LocalTime;

@Data
public class BusDTO {
    private Long id;
    private String busNumber;
    private int seats;
    private LocalTime departureTime;
    private double price;
}
