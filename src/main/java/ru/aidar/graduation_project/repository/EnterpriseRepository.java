package ru.aidar.graduation_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aidar.graduation_project.model.Enterprise;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {
}
