package ru.aidar.graduation_project.mapper;

import org.mapstruct.*;
import ru.aidar.graduation_project.dto.VehicleModelCreate;
import ru.aidar.graduation_project.dto.VehicleModelResponse;
import ru.aidar.graduation_project.dto.VehicleModelUpdate;
import ru.aidar.graduation_project.model.VehicleModel;

@Mapper(
        uses = {ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class VehicleModelMapper {
    public abstract VehicleModel map(VehicleModelCreate dto);

    @Mapping(target = "vehiclesCount", expression = "java(entity.getVehicles() != null ? entity.getVehicles().size() : 0)")
    public abstract VehicleModelResponse map(VehicleModel entity);

    public abstract void update(VehicleModelUpdate dto, @MappingTarget VehicleModel entity);
}
