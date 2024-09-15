package com.test.backend.service.impl;

import com.test.backend.api.dto.BusDto;
import com.test.backend.domain.Bus;
import com.test.backend.entity.BusMapper;
import com.test.backend.repository.BusRepository;
import com.test.backend.service.IBusService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements IBusService {

    private final BusRepository busRepository;
    private final BusMapper busMapper;

    @Override
    public Optional<BusDto> createBus(BusDto busDto) {
        Bus bus = busMapper.dtoToEntity(busDto);
        bus = busRepository.save(bus);
        return Optional.of(busMapper.entityToDto(bus));
    }

    @Override
    public Optional<BusDto> updateBus(Long id, BusDto busDto) {
        Bus existingBus = busRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Bus not found."));
        Bus updatedBus = busMapper.dtoToEntity(busDto);

        existingBus.setBusNumber(updatedBus.getBusNumber());
        existingBus.setCapacity(updatedBus.getCapacity());
        existingBus = busRepository.save(existingBus);
        return Optional.of(busMapper.entityToDto(existingBus));
    }

    @Override
    public Optional<BusDto> getBusById(Long id) {
        return busRepository.findById(id)
                .map(busMapper::entityToDto);
    }

    @Override
    public Optional<BusDto> getBusByBusNumber(Integer busNumber) {
        return busRepository.findByBusNumber(busNumber)
                .map(busMapper::entityToDto);
    }

    @Override
    public List<BusDto> getAllBuses() {
        return busRepository.findAll()
                .stream()
                .map(busMapper::entityToDto)
                .toList();
    }

    @Override
    public void deleteBus(Long id) {
        try {
            busRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("Bus not found.", 1);
        }
    }
}
