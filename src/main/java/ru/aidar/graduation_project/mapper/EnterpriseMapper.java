package ru.aidar.graduation_project.mapper;

import org.mapstruct.*;
import ru.aidar.graduation_project.dto.EnterpriseResponse;
import ru.aidar.graduation_project.model.BaseEntity;
import ru.aidar.graduation_project.model.Enterprise;

import java.util.Collection;
import java.util.List;

@Mapper(
        uses = {ReferenceMapper.class},
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class EnterpriseMapper {
    @Mapping(target = "driverIds", source = "drivers", qualifiedByName = "entitiesToIds")
    @Mapping(target = "vehicleIds", source = "vehicles", qualifiedByName = "entitiesToIds")
    public abstract EnterpriseResponse map(Enterprise entity);
}
