package ru.aidar.graduation_project.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aidar.graduation_project.exception.ResourceNotFoundException;
import ru.aidar.graduation_project.model.Vehicle;
import ru.aidar.graduation_project.model.VehicleModel;
import ru.aidar.graduation_project.repository.VehicleModelRepository;
import ru.aidar.graduation_project.repository.VehicleRepository;

import java.util.List;

@Controller
@RequestMapping("/vehicles")
@AllArgsConstructor
public class VehicleController {
    private VehicleRepository vehicleRepository;
    private VehicleModelRepository modelRepository;

    @GetMapping
    public String showList(Model model) {
        List<Vehicle> allVehicles = vehicleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("vehicles", allVehicles);
        return "vehicles/vehicles-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Vehicle vehicle = new Vehicle();
        vehicle.setModel(new VehicleModel());
        model.addAttribute("vehicle", vehicle);

        List<VehicleModel> vehicleModels = modelRepository.findAll();
        model.addAttribute("vehicleModels", vehicleModels);

        return "vehicles/vehicle-form";
    }

    //TODO: Добавить BindingResult аргумент и обработку ошибок валидации на уровне контроллеров и представления
    @PostMapping
    public String createOrUpdate(@Valid @ModelAttribute(name = "vehicle") Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        return "redirect:/vehicles";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Vehicle vehicle = vehicleRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id " + id + " не найден"));
        model.addAttribute("vehicle", vehicle);

        List<VehicleModel> vehicleModels = modelRepository.findAll();
        model.addAttribute("vehicleModels", vehicleModels);

        return "/vehicles/vehicle-form";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        vehicleRepository.deleteById(id);

        return "redirect:/vehicles";
    }
}
