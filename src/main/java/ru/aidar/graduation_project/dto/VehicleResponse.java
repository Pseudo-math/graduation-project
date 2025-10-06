package ru.aidar.graduation_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
    private Long id;
    private String modelName;
    private String numberRu;
    private int regionRegistrationCode;
    private int manufactureYear;
    private int mileageKm;
    private int priceRub;
    private String description;
}
