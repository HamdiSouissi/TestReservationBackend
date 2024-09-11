package com.testreservations.testreservation.reservation.dto;

import lombok.Data;

@Data
public class BillDTO {
    private Long id; // Identifiant de la facture
    private Long reservationId; // Identifiant de la réservation associée
    private String paymentMethod; // Méthode de paiement
    private Double amount; // Montant de la facture
}