package com.test.backend.service.impl;

import com.test.backend.api.dto.TrajetDto;
import com.test.backend.domain.Trajet;
import com.test.backend.entity.TrajetMapper;
import com.test.backend.repository.BusRepository;
import com.test.backend.repository.TrajetRepository;
import com.test.backend.service.ITrajetService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrajetServiceImpl implements ITrajetService {

    private final TrajetRepository trajetRepository;

    private final TrajetMapper trajetMapper;
    private final BusRepository busRepository;

    @Override
    public Optional<TrajetDto> createTrajet(TrajetDto trajetDto) {
        Trajet trajet = trajetMapper.dtoToEntity(trajetDto);
        trajet = trajetRepository.save(trajet);
        return Optional.of(trajetMapper.entityToDto(trajet));

    }

    @Override
    public Optional<TrajetDto> updateTrajet(Long id, TrajetDto trajetDto) {
        Optional<Trajet> existingTrajet = trajetRepository.findById(id);
        if (existingTrajet.isPresent()) {
            Trajet trajet = existingTrajet.get();
            trajet.setNbrPlaces(trajetDto.nbrPlaces());
            trajet.setDepartureTime(trajetDto.departureTime());
            trajet = trajetRepository.save(trajet);
            return Optional.of(trajetMapper.entityToDto(trajet));
        }
        return Optional.empty();
    }

    @Override
    public Optional<TrajetDto> getTrajetById(Long id) {
        return trajetRepository.findById(id).map(trajetMapper::entityToDto);
    }

    @Override
    public List<TrajetDto> getAllTrajets() {
        return trajetRepository.findAll()
                .stream()
                .map(trajetMapper::entityToDto)
                .toList();
    }

    @Override
    public void deleteTrajet(Long id) {
        try {
            trajetRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EmptyResultDataAccessException("Trajet not found", 1);
        }
    }
}
