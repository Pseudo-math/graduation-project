package ru.aidar.graduation_project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.aidar.graduation_project.dto.EnterpriseResponse;
import ru.aidar.graduation_project.mapper.EnterpriseMapper;
import ru.aidar.graduation_project.repository.EnterpriseRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enterprises")
public class EnterpriseRestController {
    private EnterpriseRepository enterpriseRepository;
    private EnterpriseMapper mapper;

    public EnterpriseRestController(EnterpriseRepository enterpriseRepository, EnterpriseMapper mapper) {
        this.enterpriseRepository = enterpriseRepository;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EnterpriseResponse> show() {
        return enterpriseRepository.findAll()
                .stream()
                .map(mapper::map)
                .toList();
    }
}
