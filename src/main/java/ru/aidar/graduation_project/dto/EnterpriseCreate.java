package ru.aidar.graduation_project.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.aidar.graduation_project.model.Driver;
import ru.aidar.graduation_project.model.Manager;
import ru.aidar.graduation_project.model.Vehicle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseCreate {
    @NotBlank
    private String name;

    private List<Long> managerIds;

    @NotNull
    private String city;
}
