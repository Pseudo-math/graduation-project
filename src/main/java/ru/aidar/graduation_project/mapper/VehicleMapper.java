package ru.aidar.graduation_project.mapper;

import org.mapstruct.*;
import ru.aidar.graduation_project.dto.VehicleCreate;
import ru.aidar.graduation_project.dto.VehicleResponse;
import ru.aidar.graduation_project.dto.VehicleUpdate;
import ru.aidar.graduation_project.model.Vehicle;

@Mapper(
        uses = {ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class VehicleMapper {
    @Mapping(target = "model", source = "modelId")
    public abstract Vehicle map(VehicleCreate dto);

    @Mapping(target = "modelId", source = "model.id")
    @Mapping(target = "modelName", source = "model.name")
    public abstract VehicleResponse map(Vehicle entity);

    @Mapping(target = "model", source = "modelId")
    public abstract void update(VehicleUpdate dto, @MappingTarget Vehicle entity);
}
