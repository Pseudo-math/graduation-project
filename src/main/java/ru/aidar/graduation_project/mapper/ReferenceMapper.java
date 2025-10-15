package ru.aidar.graduation_project.mapper;

import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;
import ru.aidar.graduation_project.model.BaseEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING
)
public abstract class ReferenceMapper {
    @Autowired
    private EntityManager entityManager;

    public <T extends BaseEntity> T toEntity(Long id, @TargetType Class<T> entityClass) {
        return id != null ? entityManager.find(entityClass, id) : null;
    }

    @Named("entitiesToIds")
    protected <T extends BaseEntity> List<Long> mapEntitiesToEntityIds(Collection<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return Collections.emptyList();
        }

        return entities.stream().map(BaseEntity::getId).toList();
    }
}
