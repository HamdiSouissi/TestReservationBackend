package com.testreservations.testreservation.reservation.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservationDTO {
    private Long id;
    private LocalDate travelDate;
    private ClientDTO client;
    private BusDTO bus;
}
