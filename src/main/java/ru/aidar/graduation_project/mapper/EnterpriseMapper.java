package ru.aidar.graduation_project.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aidar.graduation_project.dto.EnterpriseCreate;
import ru.aidar.graduation_project.dto.EnterpriseResponse;
import ru.aidar.graduation_project.dto.EnterpriseUpdate;
import ru.aidar.graduation_project.model.Enterprise;
import ru.aidar.graduation_project.model.ManagerProfile;
import ru.aidar.graduation_project.repository.ManagerProfileRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(
        uses = {ReferenceMapper.class},
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class EnterpriseMapper {
    @Autowired
    private ManagerProfileRepository managerProfileRepository;

    protected Set<ManagerProfile> managerProfileIdsToManagerProfiles(List<Long> managerProfileIds) {
        if (managerProfileIds == null || managerProfileIds.isEmpty()) {
            return null;
        }

        List<ManagerProfile> managers = managerProfileRepository.findAllById(managerProfileIds);

        if (managers.size() != managerProfileIds.size()) {
            throw new RuntimeException("One or more Manager IDs are invalid or missing.");
        }

        return new HashSet<>(managers);
    }

    @Mapping(target = "driverIds", source = "drivers", qualifiedByName = "entitiesToIds")
    @Mapping(target = "vehicleIds", source = "vehicles", qualifiedByName = "entitiesToIds")
    @Mapping(target = "managerProfileIds", source = "managerProfiles", qualifiedByName = "entitiesToIds")
    public abstract EnterpriseResponse map(Enterprise entity);

    @Mapping(target = "managerProfiles", source = "managerProfileIds")
    public abstract Enterprise map(EnterpriseCreate dto);

    @Mapping(target = "managerProfiles", source = "managerProfileIds")
    public abstract void update(EnterpriseUpdate data, @MappingTarget Enterprise entity);
}
