package ru.aidar.graduation_project.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.aidar.graduation_project.dto.VehicleCreate;
import ru.aidar.graduation_project.dto.VehicleResponse;
import ru.aidar.graduation_project.dto.VehicleUpdate;
import ru.aidar.graduation_project.exception.ResourceNotFoundException;
import ru.aidar.graduation_project.mapper.VehicleMapper;
import ru.aidar.graduation_project.model.Vehicle;
import ru.aidar.graduation_project.repository.VehicleModelRepository;
import ru.aidar.graduation_project.repository.VehicleRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleRestController {
    private VehicleRepository vehicleRepository;
    private VehicleModelRepository modelRepository;
    private VehicleMapper mapper;

    public VehicleRestController(VehicleRepository vehicleRepository, VehicleModelRepository modelRepository, VehicleMapper mapper) {
        this.vehicleRepository = vehicleRepository;
        this.modelRepository = modelRepository;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleResponse> show() {
        List<Vehicle> allVehicles = vehicleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return allVehicles.stream()
                .map(mapper::map)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse create(@Valid @RequestBody VehicleCreate vehicleCreate) {
        Vehicle vehicle = mapper.map(vehicleCreate);

        vehicleRepository.save(vehicle);

        return mapper.map(vehicle);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleResponse update(@PathVariable Long id, @Valid @RequestBody VehicleUpdate data) {
        Vehicle vehicle = vehicleRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id " + id + " not found"));
        mapper.update(data, vehicle);

        vehicleRepository.save(vehicle);

        return mapper.map(vehicle);
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
