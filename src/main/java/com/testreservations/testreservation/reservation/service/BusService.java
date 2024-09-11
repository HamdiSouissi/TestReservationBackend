package com.testreservations.testreservation.reservation.service;

import com.testreservations.testreservation.reservation.dto.BusDTO;

import java.util.List;

public interface BusService {
    List<BusDTO> getAllBuses();
    BusDTO getBusById(Long id);
    BusDTO createBus(BusDTO busDTO);
    BusDTO updateBus(Long id, BusDTO busDTO);
    void deleteBus(Long id);
}
