package ru.aidar.graduation_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aidar.graduation_project.model.VehicleType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleModelCreate {
    @NotBlank(message = "Название модели обязательно")
    private String name;

    @NotNull(message = "Тип транспортного средства обязателен")
    private VehicleType vehicleType;

    @NotNull(message = "Объем топливного бака обязателен")
    @Positive(message = "Объем топливного бака должен быть положительным числом")
    private Integer fuelTankVolume;

    @NotNull(message = "Грузоподъемность обязательна")
    @Positive(message = "Грузоподъемность должна быть положительным числом")
    private Integer loadCapacityKg;

    @NotNull(message = "Количество мест обязательно")
    @Positive(message = "Количество мест должно быть положительным числом")
    private Integer seatsCount;

    @NotNull(message = "Объем багажника обязателен")
    @PositiveOrZero(message = "Объем багажника не может быть отрицательным")
    private Integer trunkVolumeL;
}