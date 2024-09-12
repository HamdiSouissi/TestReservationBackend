package com.testreservations.testreservation.reservation.service.impl;

import com.testreservations.testreservation.reservation.dto.BillDTO;
import com.testreservations.testreservation.reservation.model.Bill;
import com.testreservations.testreservation.reservation.model.Reservation;
import com.testreservations.testreservation.reservation.repository.BillRepository;
import com.testreservations.testreservation.reservation.repository.ReservationRepository;
import com.testreservations.testreservation.reservation.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImp implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public BillDTO createBill(Long reservationId, String paymentMethod) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        double amount = calculateAmount(reservation);

        Bill bill = new Bill();
        bill.setReservationId(reservationId);
        bill.setPaymentMethod(paymentMethod);
        bill.setAmount(amount);

        Bill savedBill = billRepository.save(bill);

        BillDTO billDTO = new BillDTO();
        billDTO.setId(savedBill.getId());
        billDTO.setReservationId(savedBill.getReservationId());
        billDTO.setPaymentMethod(savedBill.getPaymentMethod());
        billDTO.setAmount(savedBill.getAmount());

        return billDTO;
    }

    private double calculateAmount(Reservation reservation) {
        return 100.0;
    }

    @Override
    public List<BillDTO> getAllBills() {
        return billRepository.findAll().stream()
                .map(bill -> {
                    BillDTO billDTO = new BillDTO();
                    billDTO.setId(bill.getId());
                    billDTO.setReservationId(bill.getReservationId());
                    billDTO.setAmount(bill.getAmount());
                    return billDTO;
                })
                .collect(Collectors.toList());
    }
}