package ru.aidar.graduation_project.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aidar.graduation_project.dto.VehicleCreate;
import ru.aidar.graduation_project.dto.VehicleResponse;
import ru.aidar.graduation_project.dto.VehicleUpdate;
import ru.aidar.graduation_project.model.Driver;
import ru.aidar.graduation_project.model.Vehicle;
import ru.aidar.graduation_project.repository.DriverRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(
        uses = {ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class VehicleMapper {
    @Autowired
    private DriverRepository driverRepository;

    protected Set<Driver> driverIdsToDrivers(List<Long> driverIds) {
        if (driverIds == null || driverIds.isEmpty()) {
            return null;
        }

        List<Driver> drivers = driverRepository.findAllById(driverIds);

        if (drivers.size() != driverIds.size()) {
            throw new RuntimeException("One or more Driver IDs are invalid or missing.");
        }

        return new HashSet<>(drivers);
    }


    @Mapping(target = "model", source = "modelId")
    @Mapping(target = "enterprise", source = "enterpriseId")
    @Mapping(target = "drivers", source = "driverIds")
    public abstract Vehicle map(VehicleCreate dto);

    @Mapping(target = "driverIds", source = "drivers", qualifiedByName = "entitiesToIds")
    @Mapping(target = "enterpriseId", source = "enterprise.id")
    @Mapping(target = "modelId", source = "model.id")
    public abstract VehicleResponse map(Vehicle entity);

    @Mapping(target = "model", source = "modelId")
    @Mapping(target = "enterprise", source = "enterpriseId")
    @Mapping(target = "drivers", source = "driverIds")
    public abstract void update(VehicleUpdate dto, @MappingTarget Vehicle entity);
}
