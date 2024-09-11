package com.testreservations.testreservation.reservation.service.impl;

import com.testreservations.testreservation.reservation.dto.BusDTO;
import com.testreservations.testreservation.reservation.model.Bus;
import com.testreservations.testreservation.reservation.repository.BusRepository;
import com.testreservations.testreservation.reservation.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusServiceImp implements BusService {

    @Autowired
    private BusRepository busRepository;

    @Override
    public List<BusDTO> getAllBuses() {
        return busRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BusDTO getBusById(Long id) {
        return busRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
    }

    @Override
    public BusDTO createBus(BusDTO busDTO) {
        Bus bus = convertToEntity(busDTO);
        return convertToDTO(busRepository.save(bus));
    }

    @Override
    public BusDTO updateBus(Long id, BusDTO busDTO) {
        Bus bus = busRepository.findById(id).orElseThrow(() -> new RuntimeException("Bus not found"));
        bus.setBusNumber(busDTO.getBusNumber());
        bus.setSeats(busDTO.getSeats());
        bus.setDepartureTime(busDTO.getDepartureTime());
        bus.setPrice(busDTO.getPrice());
        return convertToDTO(busRepository.save(bus));
    }

    @Override
    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }

    // Mappage entité -> DTO
    private BusDTO convertToDTO(Bus bus) {
        BusDTO busDTO = new BusDTO();
        busDTO.setId(bus.getId());
        busDTO.setBusNumber(bus.getBusNumber());
        busDTO.setSeats(bus.getSeats());
        busDTO.setDepartureTime(bus.getDepartureTime());
        busDTO.setPrice(bus.getPrice());
        return busDTO;
    }

    // Mappage DTO -> entité
    private Bus convertToEntity(BusDTO busDTO) {
        Bus bus = new Bus();
        bus.setBusNumber(busDTO.getBusNumber());
        bus.setSeats(busDTO.getSeats());
        bus.setDepartureTime(busDTO.getDepartureTime());
        bus.setPrice(busDTO.getPrice());
        return bus;
    }
}
