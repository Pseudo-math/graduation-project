package ru.aidar.graduation_project.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.aidar.graduation_project.dto.EnterpriseCreate;
import ru.aidar.graduation_project.dto.EnterpriseResponse;
import ru.aidar.graduation_project.dto.EnterpriseUpdate;
import ru.aidar.graduation_project.exception.ResourceNotFoundException;
import ru.aidar.graduation_project.mapper.EnterpriseMapper;
import ru.aidar.graduation_project.model.Enterprise;
import ru.aidar.graduation_project.repository.EnterpriseRepository;
import ru.aidar.graduation_project.service.VisibilityService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enterprises")
public class EnterpriseRestController {
    private EnterpriseRepository enterpriseRepository;
    private EnterpriseMapper mapper;
    private VisibilityService visibilityService;

    public EnterpriseRestController(EnterpriseRepository enterpriseRepository,
                                    EnterpriseMapper mapper,
                                    VisibilityService visibilityService) {
        this.enterpriseRepository = enterpriseRepository;
        this.mapper = mapper;
        this.visibilityService = visibilityService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EnterpriseResponse> show() {
        if (!visibilityService.isManager()) {
            return enterpriseRepository.findAll().stream()
                    .map(mapper::map)
                    .toList();
        }

        // Если менеджер — фильтруем по видимым id
        var visibleIds = visibilityService.visibleEnterpriseIds();
        if (visibleIds.isEmpty()) {
            return List.of(); // ничего не видит
        }

        return enterpriseRepository.findByIdIn(visibleIds).stream()
                .map(mapper::map)
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EnterpriseResponse index(@PathVariable Long id) {
        var enterprise = enterpriseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enterprise with id " + id + " not found"));

        if (visibilityService.isManager()) {
            visibilityService.assertVisible(enterprise.getId());
        }
        return mapper.map(enterprise);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnterpriseResponse create(@Valid @RequestBody EnterpriseCreate enterpriseCreate) {
        Enterprise enterprise = mapper.map(enterpriseCreate);

        enterpriseRepository.save(enterprise);

        return mapper.map(enterprise);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EnterpriseResponse update(@PathVariable Long id, @Valid @RequestBody EnterpriseUpdate data) {
        Enterprise enterprise = enterpriseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Enterprise with id " + id + " not found"));
        mapper.update(data, enterprise);

        enterpriseRepository.save(enterprise);

        return mapper.map(enterprise);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!enterpriseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Enterprise with id " + id + " not found");
        }

        enterpriseRepository.deleteById(id);
    }
}
