package ru.aidar.graduation_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aidar.graduation_project.model.VehicleModel;

public interface VehicleModelRepository extends JpaRepository<VehicleModel, Long> {
}
