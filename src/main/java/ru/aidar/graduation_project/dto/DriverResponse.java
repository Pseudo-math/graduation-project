package ru.aidar.graduation_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aidar.graduation_project.model.DriverStatus;
import ru.aidar.graduation_project.model.Vehicle;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverResponse {
    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private Long enterpriseId;
    private List<Long> vehicleIds;
    private Long currentVehicleId;
    private Integer monthlySalary;
}
