package ru.aidar.graduation_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.aidar.graduation_project.dto.DriverResponse;
import ru.aidar.graduation_project.mapper.DriverMapper;
import ru.aidar.graduation_project.repository.DriverRepository;
import ru.aidar.graduation_project.service.VisibilityService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverRestController {
    private DriverRepository driverRepository;
    private DriverMapper mapper;
    private VisibilityService visibilityService;

    public DriverRestController(DriverRepository driverRepository,
                                DriverMapper mapper,
                                VisibilityService visibilityService) {
        this.driverRepository = driverRepository;
        this.mapper = mapper;
        this.visibilityService = visibilityService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DriverResponse> show() {
        if (!visibilityService.isManager()) {
            return driverRepository.findAll().stream().map(mapper::map).toList();
        }
        var ids = visibilityService.visibleEnterpriseIds();
        if (ids.isEmpty()) return List.of();
        return driverRepository.findByEnterpriseIdIn(ids).stream().map(mapper::map).toList();
    }


    /*
    @GetMapping("/with-enterprise/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<DriverResponse> showWithEnterpriseId(@PathVariable Long id) {
        return driverRepository.findAllByEnterpriseId(id)
                .stream()
                .map(mapper::map)
                .toList();
    }
    */
}
