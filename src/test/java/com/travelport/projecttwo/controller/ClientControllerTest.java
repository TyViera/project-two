package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entity.Client;
import com.travelport.projecttwo.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllClientsTest() throws Exception {
        Client client = new Client();
        client.setName("Client1");
        client.setNif("123456789");
        client.setAddress("123 Street");

        when(clientService.getAllClients()).thenReturn(List.of(client));


        mockMvc.perform(MockMvcRequestBuilders.get("/clients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Client1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nif").value("123456789"));
    }

    @Test
    void createClientTest() throws Exception {
        Client client = new Client();
        client.setName("Client1");
        client.setNif("123456789");
        client.setAddress("123 Street");

        when(clientService.createClient(any(Client.class))).thenReturn(client); // Ensure correct mock setup

        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                        .contentType("application/json")
                        .content("{\"name\":\"Client1\",\"nif\":\"123456789\",\"address\":\"123 Street\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void getClientByIdTest() throws Exception {
        Client client = new Client();
        client.setId("1");
        client.setName("Client1");
        client.setNif("123456789");
        client.setAddress("123 Street");

        when(clientService.getClientById("1")).thenReturn(java.util.Optional.of(client));

        mockMvc.perform(MockMvcRequestBuilders.get("/clients/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Client1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nif").value("123456789"));
    }
}
