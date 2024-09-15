package com.test.backend.api.controller;

import com.test.backend.api.dto.ReservationDto;
import com.test.backend.service.IReservationService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.of;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final IReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        Optional<ReservationDto> createdReservation = reservationService.createReservation(reservationDto);
        return createdReservation.map(reservation -> ResponseEntity.status(HttpStatus.CREATED).body(reservation))
                .orElseGet(() -> internalServerError().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Long id, @RequestBody ReservationDto reservationDto) {
        if (id == null || reservationDto.id() == null || !id.equals(reservationDto.id())) {
            return badRequest().build();
        }
        Optional<ReservationDto> updatedReservation = reservationService.updateReservation(id, reservationDto);

        return of(updatedReservation);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        return of(reservationService.getReservation(id));
    }

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        // Hard coded client. should be if spring security SecurityContextHolder.getContext().getAuthentication().getPrincipal() to get the active user
        Long clientId = 1L;
        List<ReservationDto> reservations = reservationService.getReservationsByClientId(clientId);
        return reservations.isEmpty() ? noContent().build() : ok(reservations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        try {
            reservationService.deleteReservation(id);
            return noContent().build();
        } catch (EmptyResultDataAccessException e) {
            log.error("Reservation not found while deleting");
            return notFound().build();
        } catch (Exception e) {
            log.error("Error while deleting");
            return internalServerError().build();
        }
    }
}
