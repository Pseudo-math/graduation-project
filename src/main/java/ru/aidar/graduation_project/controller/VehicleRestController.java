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
import ru.aidar.graduation_project.service.VisibilityService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleRestController {
    private VehicleRepository vehicleRepository;
    private VehicleModelRepository modelRepository;
    private VehicleMapper mapper;
    private VisibilityService visibilityService;

    public VehicleRestController(VehicleRepository vehicleRepository,
                                 VehicleModelRepository modelRepository,
                                 VehicleMapper mapper,
                                 VisibilityService visibilityService) {
        this.vehicleRepository = vehicleRepository;
        this.modelRepository = modelRepository;
        this.mapper = mapper;
        this.visibilityService = visibilityService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VehicleResponse> show() {
        if (!visibilityService.isManager()) {
            return vehicleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                    .stream().map(mapper::map).toList();
        }
        var ids = visibilityService.visibleEnterpriseIds();
        if (ids.isEmpty()) return List.of();
        return vehicleRepository.findByEnterpriseIdIn(ids).stream().map(mapper::map).toList();
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VehicleResponse index(@PathVariable Long id) {
        var v = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle with id " + id + " not found"));

        if (visibilityService.isManager()) {
            visibilityService.assertVisible(v.getEnterprise().getId());
        }
        return mapper.map(v);
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
