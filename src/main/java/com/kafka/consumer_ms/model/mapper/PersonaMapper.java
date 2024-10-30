package com.kafka.consumer_ms.model.mapper;

import com.kafka.consumer_ms.model.dto.PersonaDTO;
import com.kafka.consumer_ms.model.entities.Persona;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonaMapper {

    PersonaDTO toDTO(Persona entity);
    Persona toEntity(PersonaDTO dto);

}
