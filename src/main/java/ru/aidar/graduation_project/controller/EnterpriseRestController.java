package ru.aidar.graduation_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.aidar.graduation_project.dto.EnterpriseResponse;
import ru.aidar.graduation_project.mapper.EnterpriseMapper;
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
}
