package ru.aidar.graduation_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aidar.graduation_project.model.Enterprise;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {

    List<Enterprise> findByIdIn(Collection<Long> ids);

    Optional<Enterprise> findByIdAndIdIn(Long id, Collection<Long> ids);
}
