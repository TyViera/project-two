package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.jpa.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    public void setUp() {
        clientRepository.deleteAll();
        client = new Client();
        client.setId(UUID.randomUUID());
        client.setName("JohnDoe");
        client.setNif("123456789");
        client.setAddress("123 Main St");
        clientRepository.save(client);
    }

    @Test
    public void testGetAllClients() throws Exception {
        mockMvc.perform(get("/clients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(client.getName()));
    }

    @Test
    public void testGetClientById() throws Exception {
        mockMvc.perform(get("/clients/" + client.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(client.getName()));
    }

    @Test
    public void testCreateClient() throws Exception {
        Client newClient = new Client();
        newClient.setName("JaneDoe");
        newClient.setNif("987654321");
        newClient.setAddress("456 Main St");

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newClient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(newClient.getName()));
    }

    @Test
    public void testUpdateClient() throws Exception {
        client.setName("JaneDoe");

        mockMvc.perform(put("/clients/" + client.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("JaneDoe"));
    }

    @Test
    public void testDeleteClient() throws Exception {
        mockMvc.perform(delete("/clients/" + client.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
