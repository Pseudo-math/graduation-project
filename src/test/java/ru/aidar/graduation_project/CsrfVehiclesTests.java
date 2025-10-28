package ru.aidar.graduation_project;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class CsrfVehiclesTests {

    @Autowired
    MockMvc mvc;

    @Test
    @WithMockUser
    void postWithoutCsrf_forbidden() throws Exception {
        mvc.perform(post("/api/v1/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"x\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void postWithCsrf_notForbidden() throws Exception {
        mvc.perform(post("/api/v1/vehicles")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"x\"}"))
                .andExpect(result -> {
                    int s = result.getResponse().getStatus();
                    if (s == 403) {
                        throw new AssertionError("Expected not 403 when CSRF present, but was 403");
                    }
                });
    }

}
