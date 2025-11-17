package ru.aidar.graduation_project.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleUpdate {
    private Long modelId;

    private String numberRu;

    private Long enterpriseId;

    private List<Long> driverIds;

    @Min(1)
    @Max(999)
    private int regionRegistrationCode;

    @NotNull
    @Min(1700)
    private int manufactureYear;

    @NotNull
    @PositiveOrZero
    private int mileageKm;

    @NotNull
    @PositiveOrZero
    private int priceRub;

    private String description;
}
