package ru.aidar.graduation_project.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.aidar.graduation_project.model.Enterprise;
import ru.aidar.graduation_project.model.Manager;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VisibilityService {

    public boolean isManager() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_MANAGER".equals(a.getAuthority()));
    }

    /** Для менеджера вернёт набор видимых enterpriseId; для не-менеджера — пустой Set (нет ограничений) */
    public Set<Long> visibleEnterpriseIds() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof Manager m)) return Set.of();
        return m.getVisibleEnterprises().stream().map(Enterprise::getId).collect(Collectors.toUnmodifiableSet());
    }

    /** Бросаем 403, если менеджер и enterprise не входит в видимость */
    public void assertVisible(Long enterpriseId) {
        if (!isManager()) return;
        if (!visibleEnterpriseIds().contains(enterpriseId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access to enterprise " + enterpriseId + " is forbidden");
        }
    }
}

