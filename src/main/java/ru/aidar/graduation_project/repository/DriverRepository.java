package ru.aidar.graduation_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aidar.graduation_project.model.Driver;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findAllByEnterpriseId(Long id);
    List<Driver> findByEnterpriseIdIn(Collection<Long> enterpriseIds);
    Optional<Driver> findByIdAndEnterpriseIdIn(Long id, Collection<Long> enterpriseIds);
}
