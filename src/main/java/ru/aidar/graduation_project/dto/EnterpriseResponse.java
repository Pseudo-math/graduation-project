package ru.aidar.graduation_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseResponse {
    private Long id;
    private String name;
    private List<Long> vehicleIds;
    private List<Long> managerProfileIds;
    private List<Long> driverIds;
    private String city;
}
