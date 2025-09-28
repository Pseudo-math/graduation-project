package ru.aidar.graduation_project.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aidar.graduation_project.model.VehicleModel;
import ru.aidar.graduation_project.repository.VehicleModelRepository;

import java.util.List;

@Controller
@RequestMapping("/vehicle-models")
@AllArgsConstructor
public class VehicleModelController {
    private VehicleModelRepository vehicleModelRepository;

    @GetMapping
    public String show(Model model) {
        List<VehicleModel> allVehicleModels = vehicleModelRepository.findAll();
        model.addAttribute("vehicleModels", allVehicleModels);
        return "vehicles/vehicle-models-list";
    }
}
