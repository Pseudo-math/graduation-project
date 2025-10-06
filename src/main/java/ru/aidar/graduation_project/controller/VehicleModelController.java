package ru.aidar.graduation_project.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.aidar.graduation_project.exception.ResourceNotFoundException;
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
        List<VehicleModel> allVehicleModels = vehicleModelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        model.addAttribute("vehicleModels", allVehicleModels);

        return "vehicles/vehicle-models-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        VehicleModel vehicleModel = new VehicleModel();
        model.addAttribute("vehicleModel", vehicleModel);

        return "vehicles/vehicle-model-form";
    }

    //TODO: Добавить BindingResult аргумент и обработку ошибок валидации на уровне контроллеров и представления
    @PostMapping
    public String createOrUpdate(@ModelAttribute VehicleModel vehicleModel) {
        vehicleModelRepository.save(vehicleModel);
        return "redirect:/vehicle-models";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        VehicleModel vehicleModel = vehicleModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VehicleModel with id " + id + " not found"));
        model.addAttribute("vehicleModel", vehicleModel);

        return "vehicles/vehicle-model-form";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        vehicleModelRepository.deleteById(id);

        return "redirect:/vehicle-models";
    }
}
