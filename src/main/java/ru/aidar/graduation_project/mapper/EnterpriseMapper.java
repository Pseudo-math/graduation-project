package ru.aidar.graduation_project.mapper;

import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aidar.graduation_project.dto.EnterpriseCreate;
import ru.aidar.graduation_project.dto.EnterpriseResponse;
import ru.aidar.graduation_project.dto.EnterpriseUpdate;
import ru.aidar.graduation_project.model.BaseEntity;
import ru.aidar.graduation_project.model.Enterprise;
import ru.aidar.graduation_project.model.Manager;
import ru.aidar.graduation_project.repository.ManagerRepository;

import java.util.Collection;
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
    private ManagerRepository managerRepository;

    protected Set<Manager> managerIdsToManagers(List<Long> managerIds) {
        if (managerIds == null || managerIds.isEmpty()) {
            return null;
        }

        List<Manager> managers = managerRepository.findAllById(managerIds);

        if (managers.size() != managerIds.size()) {
            throw new RuntimeException("One or more Manager IDs are invalid or missing.");
        }

        return  new HashSet<>(managers);
    }

    @Mapping(target = "driverIds", source = "drivers", qualifiedByName = "entitiesToIds")
    @Mapping(target = "vehicleIds", source = "vehicles", qualifiedByName = "entitiesToIds")
    @Mapping(target = "managerIds", source = "managers", qualifiedByName = "entitiesToIds")
    public abstract EnterpriseResponse map(Enterprise entity);

    @Mapping(target = "managers", source = "managerIds")
    public abstract Enterprise map(EnterpriseCreate dto);

    @Mapping(target = "managers", source = "managerIds")
    public abstract void update(EnterpriseUpdate data, @MappingTarget Enterprise entity);
}
