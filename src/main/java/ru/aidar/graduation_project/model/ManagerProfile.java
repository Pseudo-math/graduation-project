package ru.aidar.graduation_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "manager_profiles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManagerProfile implements BaseEntity {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "manager_enterprises",
            joinColumns = @JoinColumn(name = "manager_profile_id"),
            inverseJoinColumns = @JoinColumn(name = "enterprise_id")
    )
    private Set<Enterprise> visibleEnterprises = new HashSet<>();
}
