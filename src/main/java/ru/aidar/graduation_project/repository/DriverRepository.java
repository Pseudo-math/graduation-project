package ru.aidar.graduation_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aidar.graduation_project.model.Driver;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> findAllByEnterpriseId(Long id);
}
