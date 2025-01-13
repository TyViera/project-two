package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.ClientEntity;
import com.travelport.projecttwo.services.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class ClientControllerTest {

    @Mock
    private ClientServiceImpl clientService;

    @InjectMocks
    private ClientController clientController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void getClientsTest() throws Exception {
        ClientEntity client1 = new ClientEntity("1", "AnaLev", "123456789", "BCN");
        ClientEntity client2 = new ClientEntity("2", "LevAna", "987654321", "Madrid");
        List<ClientEntity> clients = Arrays.asList(client1, client2);

        when(clientService.getClients()).thenReturn(clients);

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("AnaLev"))
                .andExpect(jsonPath("$[1].name").value("LevAna"));
    }

    @Test
    void getClientByIdTest() throws Exception {
        ClientEntity client = new ClientEntity("1", "AnaLev", "123456789", "BCN");

        when(clientService.getClientById("1")).thenReturn(Optional.of(client));

        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("AnaLev"));

        when(clientService.getClientById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/clients/999"))
                .andExpect(status().isNotFound());
    }


    @Test
    void postClientTest() throws Exception {
        ClientEntity newClient = new ClientEntity("1", "AnaLev", "123456789", "BCN");
        when(clientService.createClient(any(ClientEntity.class))).thenReturn(newClient);

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "AnaLev",
                                  "nif": "123456789",
                                  "address": "BCN"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("AnaLev"))
                .andExpect(jsonPath("$.nif").value("123456789"))
                .andExpect(jsonPath("$.address").value("BCN"));
    }

    @Test
    void updateClientTest() throws Exception {
        ClientEntity client = new ClientEntity("1", "AnaLev", "123456789", "BCN");
        ClientEntity updatedClient = new ClientEntity("1", "AnaLev Updated", "123456789", "Madrid");

        when(clientService.updateClient(eq("1"), any(ClientEntity.class))).thenReturn(Optional.of(updatedClient));

        mockMvc.perform(put("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"AnaLev Updated\",\"nif\":\"123456789\",\"address\":\"Madrid\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("AnaLev Updated"))
                .andExpect(jsonPath("$.address").value("Madrid"));

        when(clientService.updateClient(eq("999"), any(ClientEntity.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/clients/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"NonExisting\",\"nif\":\"000000000\",\"address\":\"Nowhere\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteClientTest() throws Exception {
        doNothing().when(clientService).deleteClient("1");

        mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isOk());

        doThrow(new NullPointerException()).when(clientService).deleteClient("999");

        mockMvc.perform(delete("/clients/999"))
                .andExpect(status().isNotFound());

        doThrow(new IllegalArgumentException()).when(clientService).deleteClient("2");

        mockMvc.perform(delete("/clients/2"))
                .andExpect(status().isUnprocessableEntity());
    }
}