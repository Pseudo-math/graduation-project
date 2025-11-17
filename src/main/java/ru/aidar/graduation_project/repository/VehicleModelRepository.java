package ru.aidar.graduation_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aidar.graduation_project.model.VehicleModel;

@Repository
public interface VehicleModelRepository extends JpaRepository<VehicleModel, Long> {
}
