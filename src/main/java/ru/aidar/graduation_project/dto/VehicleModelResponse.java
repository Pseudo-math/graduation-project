package ru.aidar.graduation_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aidar.graduation_project.model.VehicleType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleModelResponse {
    private Long id;
    private String name;
    private VehicleType vehicleType;
    private Integer fuelTankVolume;
    private Integer loadCapacityKg;
    private Integer seatsCount;
    private Integer trunkVolumeL;
    private Integer vehiclesCount;
}