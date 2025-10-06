package ru.aidar.graduation_project.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.aidar.graduation_project.dto.VehicleModelCreate;
import ru.aidar.graduation_project.dto.VehicleModelResponse;
import ru.aidar.graduation_project.dto.VehicleModelUpdate;
import ru.aidar.graduation_project.exception.ResourceNotFoundException;
import ru.aidar.graduation_project.mapper.VehicleModelMapper;
import ru.aidar.graduation_project.model.VehicleModel;
import ru.aidar.graduation_project.repository.VehicleModelRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle-models")
@AllArgsConstructor
public class VehicleModelRestController {
    private final VehicleModelRepository vehicleModelRepository;
    private final VehicleModelMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleModelResponse> getAll() {
        List<VehicleModel> models = vehicleModelRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return models.stream()
                .map(mapper::map)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleModelResponse getById(@PathVariable Long id) {
        VehicleModel model = vehicleModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VehicleModel with id " + id + " not found"));
        return mapper.map(model);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleModelResponse create(@Valid @RequestBody VehicleModelCreate dto) {
        VehicleModel model = mapper.map(dto);
        VehicleModel saved = vehicleModelRepository.save(model);
        return mapper.map(saved);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleModelResponse update(@PathVariable Long id, @Valid @RequestBody VehicleModelUpdate dto) {
        VehicleModel model = vehicleModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VehicleModel with id " + id + " not found"));

        mapper.update(dto, model);
        VehicleModel updated = vehicleModelRepository.save(model);
        return mapper.map(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        VehicleModel model = vehicleModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VehicleModel with id " + id + " not found"));

        vehicleModelRepository.delete(model);
    }
}