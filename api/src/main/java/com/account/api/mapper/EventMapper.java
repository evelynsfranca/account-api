package com.account.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.account.api.domain.model.Event;
import com.account.api.dto.event.EventToCreateDTO;
import com.account.api.dto.event.EventToResponseDTO;

@Mapper(
    componentModel = "spring", 
    unmappedTargetPolicy = ReportingPolicy.IGNORE, 
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper {

    @Mapping(source = "destination", target = "destination.id")
    @Mapping(source = "origin", target = "origin.id")
    Event toEntityEvent(EventToCreateDTO dto);

    @Mapping(source = "destination", target = "destination", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "origin", target = "origin", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    EventToResponseDTO toResponseDTO(Event event);
}
