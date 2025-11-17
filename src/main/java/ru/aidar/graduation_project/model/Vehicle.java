package ru.aidar.graduation_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vehicles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vehicle implements BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private VehicleModel model;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @ManyToMany(mappedBy = "vehicles")
    private Set<Driver> drivers;

    @Column(name = "number_ru")
    private String numberRu;

    @Min(1)
    @Max(999)
    @Column(name = "region_registration_code")
    private int regionRegistrationCode;

    @NotNull
    @Min(1700)
    @Column(name = "manufacture_year")
    private int manufactureYear;

    @NotNull
    @PositiveOrZero
    @Column(name = "mileage_km")
    private int mileageKm;

    @NotNull
    @PositiveOrZero
    @Column(name = "price_rub")
    private int priceRub;

    @Column(name = "description")
    private String description;
}
