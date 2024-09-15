package com.test.backend.entity;

import com.test.backend.api.dto.BusDto;
import com.test.backend.domain.Bus;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BusMapper {

    Bus dtoToEntity(BusDto dto);

    BusDto entityToDto(Bus bus);
}
