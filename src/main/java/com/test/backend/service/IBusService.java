package com.test.backend.service;

import com.test.backend.api.dto.BusDto;
import java.util.List;
import java.util.Optional;

public interface IBusService {

    Optional<BusDto> createBus(BusDto busDto);

    Optional<BusDto> updateBus(Long id, BusDto busDto);

    Optional<BusDto> getBusById(Long id);

    Optional<BusDto> getBusByBusNumber(Integer busNumber);

    List<BusDto> getAllBuses();

    void deleteBus(Long id);
}
