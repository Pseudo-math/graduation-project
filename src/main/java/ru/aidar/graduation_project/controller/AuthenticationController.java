package ru.aidar.graduation_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.aidar.graduation_project.dto.AuthManagerRequest;
import ru.aidar.graduation_project.repository.ManagerRepository;
import ru.aidar.graduation_project.security.JwtUtils;
import ru.aidar.graduation_project.security.ManagerDetailsService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ManagerDetailsService managerDetailsService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@RequestBody AuthManagerRequest auth) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(auth.username(), auth.password())
        );

        final UserDetails userDetails = managerDetailsService.loadUserByUsername(auth.username());

        return jwtUtils.generateManagerToken(auth.username());
    }
}
