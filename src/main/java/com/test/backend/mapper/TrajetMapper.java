package com.test.backend.mapper;


import com.test.backend.api.dto.TrajetDto;
import com.test.backend.entity.Trajet;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TrajetMapper {

    Trajet dtoToEntity(TrajetDto trajetDto);

    TrajetDto entityToDto(Trajet trajet);
}
