package com.testreservations.testreservation.reservation.service.impl;

import com.testreservations.testreservation.reservation.dto.BillDTO;
import com.testreservations.testreservation.reservation.dto.BusDTO;
import com.testreservations.testreservation.reservation.dto.ClientDTO;
import com.testreservations.testreservation.reservation.dto.ReservationDTO;
import com.testreservations.testreservation.reservation.model.Bus;
import com.testreservations.testreservation.reservation.model.Client;
import com.testreservations.testreservation.reservation.model.Reservation;
import com.testreservations.testreservation.reservation.repository.BillRepository;
import com.testreservations.testreservation.reservation.repository.BusRepository;
import com.testreservations.testreservation.reservation.repository.ClientRepository;
import com.testreservations.testreservation.reservation.repository.ReservationRepository;
import com.testreservations.testreservation.reservation.service.BillService;
import com.testreservations.testreservation.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImp implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillService billService; // Service pour gérer les factures

    @Override
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReservationDTO getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    @Override
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = convertToEntity(reservationDTO);
        Reservation savedReservation = reservationRepository.save(reservation);
        return convertToDTO(savedReservation);
    }

    @Override
    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Mettre à jour les détails de la réservation
        reservation.setTravelDate(reservationDTO.getTravelDate());

        // Mettre à jour le client associé
        Client client = clientRepository.findById(reservationDTO.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        reservation.setClient(client);

        // Mettre à jour le bus associé
        Bus bus = busRepository.findById(reservationDTO.getBus().getId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        reservation.setBus(bus);

        Reservation updatedReservation = reservationRepository.save(reservation);
        return convertToDTO(updatedReservation);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public boolean payReservation(Long reservationId, String paymentMethod) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        // Simuler la validation du paiement
        boolean paymentSuccessful = simulatePayment(paymentMethod);
        if (!paymentSuccessful) {
            return false; // Le paiement a échoué
        }

        // Créer la facture après le paiement réussi
        BillDTO billDTO = new BillDTO();
        billDTO.setReservationId(reservationId);
        billDTO.setPaymentMethod(paymentMethod);

        BillDTO createdBill = billService.createBill(reservationId, paymentMethod);

        if (createdBill != null) {
            // Le paiement est réussi et la facture est créée
            return true;
        } else {
            // Échec de la création de la facture
            return false;
        }
    }

    private boolean simulatePayment(String paymentMethod) {
        // Simuler le succès du paiement, dans une vraie application vous devrez intégrer un système de paiement
        return true;
    }

    // Mappage entité -> DTO
    private ReservationDTO convertToDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setTravelDate(reservation.getTravelDate());

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(reservation.getClient().getId());
        clientDTO.setName(reservation.getClient().getName());
        clientDTO.setEmail(reservation.getClient().getEmail());
        dto.setClient(clientDTO);

        BusDTO busDTO = new BusDTO();
        busDTO.setId(reservation.getBus().getId());
        busDTO.setBusNumber(reservation.getBus().getBusNumber());
        busDTO.setSeats(reservation.getBus().getSeats());
        busDTO.setDepartureTime(reservation.getBus().getDepartureTime());
        busDTO.setPrice(reservation.getBus().getPrice());
        dto.setBus(busDTO);

        return dto;
    }

    // Mappage DTO -> entité
    private Reservation convertToEntity(ReservationDTO dto) {
        Reservation reservation = new Reservation();
        reservation.setTravelDate(dto.getTravelDate());

        Client client = clientRepository.findById(dto.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        reservation.setClient(client);

        Bus bus = busRepository.findById(dto.getBus().getId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        reservation.setBus(bus);

        return reservation;
    }
}