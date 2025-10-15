package ru.aidar.graduation_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.aidar.graduation_project.dto.DriverResponse;
import ru.aidar.graduation_project.mapper.DriverMapper;
import ru.aidar.graduation_project.repository.DriverRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverRestController {
    private DriverRepository driverRepository;
    private DriverMapper mapper;

    public DriverRestController(DriverRepository driverRepository, DriverMapper mapper) {
        this.driverRepository = driverRepository;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DriverResponse> show() {
        return driverRepository.findAll()
                .stream()
                .map(mapper::map)
                .toList();
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
