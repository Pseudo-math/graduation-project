package ru.aidar.graduation_project.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.aidar.graduation_project.model.Vehicle;
import ru.aidar.graduation_project.repository.VehicleRepository;

import java.util.List;

@Controller
@RequestMapping("/vehicles")
@AllArgsConstructor
public class VehicleController {
    private VehicleRepository vehicleRepository;

    @GetMapping
    public String show(Model model) {
        List<Vehicle> allVehicles = vehicleRepository.findAll();
        model.addAttribute("vehicles", allVehicles);
        return "vehicles/vehicles-list";
    }
}
