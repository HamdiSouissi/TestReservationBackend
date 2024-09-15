package com.test.backend.service.impl;

import com.test.backend.api.dto.ReservationDto;
import com.test.backend.entity.Bus;
import com.test.backend.entity.Reservation;
import com.test.backend.entity.Trajet;
import com.test.backend.mapper.ReservationMapper;
import com.test.backend.repository.BusRepository;
import com.test.backend.repository.ReservationRepository;
import com.test.backend.service.IReservationService;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final BusRepository busRepository;

    @Override
    @Transactional()
    public Optional<ReservationDto> createReservation(ReservationDto reservationDto) {
        Reservation reservation = reservationMapper.dtoToEntity(reservationDto);
        completeBusInformation(reservation);
        calculateAndSetPrices(reservation);
        reservation.setReservationDate(LocalDate.now());
        reservation = reservationRepository.save(reservation);
        return Optional.of(reservationMapper.entityToDto(reservation));
    }

    @Override
    @Transactional()
    public Optional<ReservationDto> updateReservation(Long id, ReservationDto reservationDto) {
        Reservation existingReservation = reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found."));
        Reservation updatedReservation = reservationMapper.dtoToEntity(reservationDto);

        existingReservation.setTrajets(updatedReservation.getTrajets());
        existingReservation.setClient(updatedReservation.getClient());
        completeBusInformation(existingReservation);
        calculateAndSetPrices(existingReservation);
        existingReservation = reservationRepository.save(existingReservation);
        return Optional.of(reservationMapper.entityToDto(existingReservation));

    }

    @Override
    public Optional<ReservationDto> getReservation(Long id) {
        return reservationRepository.findById(id).map(reservationMapper::entityToDto);
    }

    @Override
    public List<ReservationDto> getReservationsByClientId(Long clientId) {
        return reservationRepository.findAllByClient_Id(clientId)
                .stream()
                .map(reservationMapper::entityToDto)
                .toList();
    }

    @Override
    @Transactional()
    public void deleteReservation(Long id) {
        try {
            reservationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("Reservation not found", 1);
        }
    }

    private void completeBusInformation(Reservation reservation) {
        Set<Integer> busNumbers = reservation.getTrajets().stream()
                .map(trajet -> trajet.getBus().getBusNumber())
                .collect(Collectors.toSet());

        List<Bus> buses = busRepository.findByBusNumberIn(busNumbers);
        Map<Integer, Bus> busMap = buses.stream()
                .collect(Collectors.toMap(Bus::getBusNumber, Function.identity()));

        reservation.getTrajets().forEach(trajet -> {
            Bus bus = busMap.get(trajet.getBus().getBusNumber());
            trajet.setBus(bus);
        });
    }
    private void calculateAndSetPrices(Reservation reservation) {
        reservation.getTrajets().forEach(trajet -> {
            double price = trajet.getNbrPlaces() * trajet.getBus().getUnitTrajetPrice();
            if (price > 100) {
                price = price * 0.95;
            }
            trajet.setTrajetPrice(price);
            trajet.getBus().setCapacity(trajet.getBus().getCapacity() - trajet.getNbrPlaces());
        });

        double totalPrice = reservation.getTrajets().stream().mapToDouble(Trajet::getTrajetPrice).sum();
        reservation.setReservationPrice(totalPrice);
    }
}
