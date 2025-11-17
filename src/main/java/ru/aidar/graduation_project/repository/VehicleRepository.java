package ru.aidar.graduation_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aidar.graduation_project.model.Vehicle;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByEnterpriseIdIn(Collection<Long> enterpriseIds);
    Optional<Vehicle> findByIdAndEnterpriseIdIn(Long id, Collection<Long> enterpriseIds);
}
