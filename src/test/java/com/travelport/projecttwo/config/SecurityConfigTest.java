package com.travelport.projecttwo.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    void accessRestrictedEndpointWithAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/sales"))
                .andExpect(status().isOk());  // Expect 200 OK
    }

    @Test
    void accessRestrictedEndpointWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/sales"))
                .andExpect(status().isUnauthorized());  // Expect 401 Unauthorized
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles = "USER")
    void accessNonRestrictedEndpointWithAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/public"))
                .andExpect(status().isOk());  // Expect 200 OK
    }
}
