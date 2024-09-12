package com.testreservations.testreservation.reservation.controller;

import com.testreservations.testreservation.reservation.dto.BillDTO;
import com.testreservations.testreservation.reservation.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping
    public ResponseEntity<List<BillDTO>> getAllBills() {
        return ResponseEntity.ok(billService.getAllBills());
    }

    @PostMapping
    public ResponseEntity<BillDTO> createBill(@RequestParam Long reservationId, @RequestParam String paymentMethod) {
        try {
            BillDTO billDTO = billService.createBill(reservationId, paymentMethod);
            return new ResponseEntity<>(billDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
