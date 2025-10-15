package ru.aidar.graduation_project.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.List;

@Entity
@Table(name = "drivers")
@NoArgsConstructor
@Getter
@Setter
public class Driver implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @ManyToMany
    @JoinTable(
            name = "driver_vehicle",
            joinColumns = @JoinColumn(name = "driver_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private List<Vehicle> vehicles;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "current_vehicle_id", referencedColumnName = "id", unique = true)
    private Vehicle currentVehicle;

    @PositiveOrZero
    @Column(name = "monthly_salary")
    private Integer monthlySalary;
}
