package com.test.backend.api.controller;
import com.test.backend.api.dto.TrajetDto;
import com.test.backend.service.ITrajetService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;


@RestController
@RequestMapping("/trajets")
@RequiredArgsConstructor
@Slf4j
public class TrajetController {

    private final ITrajetService trajetService;

    @PostMapping
    public ResponseEntity<TrajetDto> createTrajet(@RequestBody TrajetDto trajetDto) {
        Optional<TrajetDto> createdTrajet = trajetService.createTrajet(trajetDto);
        return createdTrajet.map(trajet -> ResponseEntity.status(HttpStatus.CREATED).body(trajet))
                .orElseGet(() -> internalServerError().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrajetDto> updateTrajet(@PathVariable Long id, @RequestBody TrajetDto trajetDto) {
        if (id == null || trajetDto.id() == null || !id.equals(trajetDto.id())) {
            return badRequest().build();
        }
        Optional<TrajetDto> updatedTrajet = trajetService.updateTrajet(id, trajetDto);

        return of(updatedTrajet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrajetDto> getTrajetById(@PathVariable Long id) {
        return of(trajetService.getTrajetById(id));
    }

    @GetMapping
    public ResponseEntity<List<TrajetDto>> getAllTrajets() {
        List<TrajetDto> trajets = trajetService.getAllTrajets();
        return trajets.isEmpty() ? noContent().build() : ok(trajets);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrajet(@PathVariable Long id) {
        try {
            trajetService.deleteTrajet(id);
            return noContent().build();
        } catch (EmptyResultDataAccessException e) {
            log.error("Trajet not found while deleting");
            return notFound().build();
        } catch (Exception e) {
            log.error("Error while deleting");
            return internalServerError().build();
        }
    }

}