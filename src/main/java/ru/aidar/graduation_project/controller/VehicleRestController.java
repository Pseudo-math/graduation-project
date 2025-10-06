package ru.aidar.graduation_project.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.aidar.graduation_project.exception.ResourceNotFoundException;
import ru.aidar.graduation_project.model.Vehicle;
import ru.aidar.graduation_project.repository.VehicleModelRepository;
import ru.aidar.graduation_project.repository.VehicleRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleRestController {
    private VehicleRepository vehicleRepository;
    private VehicleModelRepository modelRepository;

    public VehicleRestController(VehicleRepository vehicleRepository, VehicleModelRepository modelRepository) {
        this.vehicleRepository = vehicleRepository;
        this.modelRepository = modelRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Vehicle> show() {
        List<Vehicle> allVehicles = vehicleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return allVehicles;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicle create(@Valid @RequestBody Vehicle vehicle) {
        vehicleRepository.save(vehicle);
        return vehicle;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Vehicle update(@PathVariable Long id, @Valid @RequestBody Vehicle data) {
        Vehicle vehicle = vehicleRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id " + id + " not found"));
        data.setId(id);
        vehicleRepository.save(data);
        return data;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle with id " + id + " not found");
        }

        vehicleRepository.deleteById(id);
    }
}
