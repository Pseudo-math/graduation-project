package ru.aidar.graduation_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicle_models")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "model")
    private List<Vehicle> vehicles = new ArrayList<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @NotNull
    @Positive
    @Column(name = "fuel_tank_volume")
    private int fuelTankVolume;

    @NotNull
    @Positive
    @Column(name = "load_capacity_kg")
    private int loadCapacityKg;

    @NotNull
    @Positive
    @Column(name = "seats_count")
    private int seatsCount;

    @NotNull
    @PositiveOrZero
    @Column(name = "trunk_volume_l")
    private int trunkVolumeL;

    public void addVehicle(Vehicle vehicle) {
        vehicle.setModel(this);
        vehicles.add(vehicle);
    }

    public void removeVehicle(Vehicle vehicle) {
        vehicle.setModel(null);
        vehicles.remove(vehicle);
    }

}
