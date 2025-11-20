package ru.aidar.graduation_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "enterprises")
@NoArgsConstructor
@Getter
@Setter
public class Enterprise implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "visibleEnterprises")
    private Set<ManagerProfile> managerProfiles = new HashSet<>();

    @OneToMany(mappedBy = "enterprise")
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "enterprise")
    private List<Driver> drivers = new ArrayList<>();

    @NotNull
    @Column(name = "city")
    private String city;
}
