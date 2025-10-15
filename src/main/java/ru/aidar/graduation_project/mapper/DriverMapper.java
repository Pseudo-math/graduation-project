package ru.aidar.graduation_project.mapper;

import org.mapstruct.*;
import ru.aidar.graduation_project.dto.DriverResponse;
import ru.aidar.graduation_project.model.Driver;
import ru.aidar.graduation_project.model.Vehicle;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Mapper(
        uses = {ReferenceMapper.class},
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class DriverMapper {
    @Mapping(target = "vehicleIds", source = "vehicles", qualifiedByName = "entitiesToIds")
    @Mapping(target = "enterpriseId", source = "enterprise.id")
    @Mapping(target = "currentVehicleId", source = "currentVehicle.id", defaultValue = "-1L")
    public abstract DriverResponse map(Driver entity);
}
