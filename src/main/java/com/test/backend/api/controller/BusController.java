package com.test.backend.api.controller;

import com.test.backend.api.dto.BusDto;
import com.test.backend.service.IBusService;
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
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/buses")
@RequiredArgsConstructor
@Slf4j
public class BusController {

    private final IBusService busService;

    @PostMapping
    public ResponseEntity<BusDto> createBus(@RequestBody BusDto busDto) {
        Optional<BusDto> createdBus = busService.createBus(busDto);
        return createdBus.map(bus -> ResponseEntity.status(HttpStatus.CREATED).body(bus))
                .orElseGet(() -> internalServerError().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusDto> updateBus(@PathVariable Long id, @RequestBody BusDto busDto) {
        if (id == null || busDto.id() == null || !id.equals(busDto.id())) {
            return badRequest().build();
        }
        Optional<BusDto> updatedBus = busService.updateBus(id, busDto);

        return of(updatedBus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusDto> getBusById(@PathVariable Long id) {
        return of(busService.getBusById(id));
    }

    @GetMapping
    public ResponseEntity<List<BusDto>> getAllBuss() {
        List<BusDto> buss = busService.getAllBuses();
        return buss.isEmpty() ? noContent().build() : ok(buss);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        try {
            busService.deleteBus(id);
            return noContent().build();
        } catch (EmptyResultDataAccessException e) {
            log.error("Bus not found while deleting");
            return notFound().build();
        } catch (Exception e) {
            log.error("Error while deleting");
            return internalServerError().build();
        }
    }
}
