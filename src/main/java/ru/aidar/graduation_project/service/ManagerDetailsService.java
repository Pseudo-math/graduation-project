package ru.aidar.graduation_project.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.aidar.graduation_project.repository.ManagerRepository;

@Service
public class ManagerDetailsService implements UserDetailsService {
    private ManagerRepository managerRepository;

    public ManagerDetailsService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.trim().isEmpty()) {
            throw new UsernameNotFoundException("Username is empty");
        }

        return managerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Manager with name " + username + " not found."));
    }
}
