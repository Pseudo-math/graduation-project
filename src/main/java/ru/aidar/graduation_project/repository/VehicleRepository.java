package ru.aidar.graduation_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aidar.graduation_project.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
