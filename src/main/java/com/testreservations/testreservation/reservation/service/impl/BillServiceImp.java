package com.testreservations.testreservation.reservation.service.impl;

import com.testreservations.testreservation.reservation.dto.BillDTO;
import com.testreservations.testreservation.reservation.model.Bill;
import com.testreservations.testreservation.reservation.model.Reservation;
import com.testreservations.testreservation.reservation.repository.BillRepository;
import com.testreservations.testreservation.reservation.repository.ReservationRepository;
import com.testreservations.testreservation.reservation.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        // Calculer le montant de la facture (par exemple, appliquer une remise si le prix dépasse 100 euros)
        double amount = reservation.getBus().getPrice();
        if (amount > 100) {
            amount *= 0.95; // Remise de 5%
        }

        Bill bill = new Bill();
        bill.setReservationId(reservationId);
        bill.setPaymentMethod(paymentMethod);
        bill.setAmount(amount);

        Bill savedBill = billRepository.save(bill);

        // Convertir la facture en DTO
        BillDTO billDTO = new BillDTO();
        billDTO.setId(savedBill.getId());
        billDTO.setReservationId(reservationId);
        billDTO.setPaymentMethod(paymentMethod);
        billDTO.setAmount(amount);

        return billDTO;
    }
}
