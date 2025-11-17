package ru.aidar.graduation_project.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aidar.graduation_project.model.Manager;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    @EntityGraph(attributePaths = "visibleEnterprises")
    Optional<Manager> findByUsername(String username);
}
