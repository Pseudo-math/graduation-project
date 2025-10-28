package ru.aidar.graduation_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "managers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Manager implements BaseEntity, UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Column(name = "password_digest")
    private String passwordDigest;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "surname")
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @ManyToMany(mappedBy = "managers")
    private Set<Enterprise> visibleEnterprises = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_MANAGER"));
    }

    @Override
    public String getPassword() {
        return passwordDigest;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
