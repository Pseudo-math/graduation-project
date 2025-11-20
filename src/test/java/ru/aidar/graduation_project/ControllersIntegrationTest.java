package ru.aidar.graduation_project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import ru.aidar.graduation_project.dto.AuthManagerRequest;
import ru.aidar.graduation_project.dto.EnterpriseCreate;
import ru.aidar.graduation_project.dto.EnterpriseResponse;
import ru.aidar.graduation_project.dto.VehicleCreate;
import ru.aidar.graduation_project.mapper.EnterpriseMapper;
import ru.aidar.graduation_project.mapper.VehicleMapper;
import ru.aidar.graduation_project.model.Enterprise;
import ru.aidar.graduation_project.model.User;
import ru.aidar.graduation_project.model.Vehicle;
import ru.aidar.graduation_project.repository.EnterpriseRepository;
import ru.aidar.graduation_project.repository.VehicleModelRepository;
import ru.aidar.graduation_project.repository.VehicleRepository;
import ru.aidar.graduation_project.security.CustomUserDetailsService;
import ru.aidar.graduation_project.security.JwtUtils;
import ru.aidar.graduation_project.service.VisibilityService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ControllersIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private EnterpriseRepository enterpriseRepository;
    @MockitoBean private VehicleRepository vehicleRepository;
    @MockitoBean private VehicleModelRepository vehicleModelRepository;
    @MockitoBean private VisibilityService visibilityService;

    @MockitoBean private EnterpriseMapper enterpriseMapper;
    @MockitoBean private VehicleMapper vehicleMapper;

    @MockitoBean private AuthenticationManager authenticationManager;
    @MockitoBean private CustomUserDetailsService customUserDetailsService;
    @MockitoBean private JwtUtils jwtUtils;

    @Test
    @DisplayName("Login: Успешный вход -> 200 OK и токен")
    void login_Success() throws Exception {
        AuthManagerRequest request = new AuthManagerRequest("manager", "pass");

        User mockUser = new User();
        mockUser.setUsername("manager");

        when(customUserDetailsService.loadUserByUsername("manager")).thenReturn(mockUser);
        when(jwtUtils.generateToken("manager")).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("fake-jwt-token"));
    }

    @Test
    @DisplayName("Login: Неверный пароль -> 401 Unauthorized")
    void login_WrongPassword() throws Exception {
        AuthManagerRequest request = new AuthManagerRequest("manager", "wrong");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad creds"));

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Анонимный доступ запрещен -> 401")
    void anonymous_Access_Returns401() throws Exception {
        mockMvc.perform(get("/api/v1/enterprises"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "simpleUser", roles = "USER")
    @DisplayName("Обычный USER (не менеджер) -> 403 Forbidden")
    void roleUser_Access_Returns403() throws Exception {
        when(visibilityService.isManager()).thenReturn(false);

        mockMvc.perform(delete("/api/v1/enterprises/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    @DisplayName("Enterprise: Создание успешное -> 201 Created")
    void createEnterprise_Success() throws Exception {
        EnterpriseCreate dto = new EnterpriseCreate("New Corp", null, "City");
        Enterprise savedEnt = new Enterprise();
        savedEnt.setId(1L);
        savedEnt.setName("New Corp");

        when(enterpriseMapper.map(any(EnterpriseCreate.class))).thenReturn(savedEnt);

        EnterpriseResponse resp = new EnterpriseResponse();
        resp.setId(1L);
        when(enterpriseMapper.map(any(Enterprise.class))).thenReturn(resp);

        mockMvc.perform(post("/api/v1/enterprises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(enterpriseRepository).save(any(Enterprise.class));
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    @DisplayName("Enterprise: Невалидные данные -> 400 Bad Request")
    void createEnterprise_Invalid() throws Exception {
        EnterpriseCreate dto = new EnterpriseCreate("", null, "City");

        mockMvc.perform(post("/api/v1/enterprises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    @DisplayName("Enterprise: Доступ к чужому (невидимому) -> 403 Forbidden")
    void accessInvisibleEnterprise() throws Exception {
        Long id = 999L;
        when(enterpriseRepository.findById(id)).thenReturn(Optional.of(new Enterprise()));
        when(visibilityService.isManager()).thenReturn(true);

        doThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Not yours"))
                .when(visibilityService).assertVisible(id);

        mockMvc.perform(get("/api/v1/enterprises/{id}", id))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    @DisplayName("Enterprise: Удаление с машинами (Конфликт) -> 409 Conflict")
    void deleteEnterprise_Conflict() throws Exception {
        Long id = 5L;
        when(enterpriseRepository.existsById(id)).thenReturn(true);
        when(visibilityService.isManager()).thenReturn(true);
        doNothing().when(visibilityService).assertVisible(id);

        doThrow(new DataIntegrityViolationException("FK constraint"))
                .when(enterpriseRepository).deleteById(id);

        mockMvc.perform(delete("/api/v1/enterprises/{id}", id))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    @DisplayName("Enterprise: Удаление успешное -> 204 No Content")
    void deleteEnterprise_Success() throws Exception {
        Long id = 10L;
        when(enterpriseRepository.existsById(id)).thenReturn(true);
        when(visibilityService.isManager()).thenReturn(true);
        doNothing().when(visibilityService).assertVisible(id);
        doNothing().when(enterpriseRepository).deleteById(id);

        mockMvc.perform(delete("/api/v1/enterprises/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    @DisplayName("Vehicle: Создание в чужом предприятии -> 403 Forbidden")
    void createVehicle_InvisibleEnt() throws Exception {
        VehicleCreate dto = new VehicleCreate();
        dto.setEnterpriseId(999L); // Чужой ID

        dto.setManufactureYear(2022);
        dto.setMileageKm(100);
        dto.setPriceRub(1000);
        dto.setRegionRegistrationCode(16);
        dto.setNumberRu("A000AA");

        Vehicle v = new Vehicle();
        Enterprise e = new Enterprise();
        e.setId(999L);
        v.setEnterprise(e);

        when(vehicleMapper.map(any(VehicleCreate.class))).thenReturn(v);
        when(visibilityService.isManager()).thenReturn(true);

        doThrow(new ResponseStatusException(HttpStatus.FORBIDDEN))
                .when(visibilityService).assertVisible(999L);

        mockMvc.perform(post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }
}