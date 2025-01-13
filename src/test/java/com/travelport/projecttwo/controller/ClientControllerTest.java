package com.travelport.projecttwo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.projecttwo.controllers.ClientController;
import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.entities.Sale;
import com.travelport.projecttwo.services.ClientServiceImpl;
import com.travelport.projecttwo.services.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class ClientControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ClientServiceImpl clientService;

  @Mock
  private SaleService saleService;

  @InjectMocks
  private ClientController clientController;

  private ObjectMapper objectMapper = new ObjectMapper();

  private String basicAuthHeader;

  @BeforeEach
  void setup() {
    String username = "admin";
    String password = "badia123";
    basicAuthHeader = "Basic " + java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());

    mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
  }

  @Test
  void testGetClientById() throws Exception {
    UUID clientId = UUID.randomUUID();
    Client client = new Client("Aelin Galathynius", "123456789A", "Terrasen");
    client.setId(clientId);

    when(clientService.read(clientId)).thenReturn(client);

    mockMvc.perform(get("/clients/{id}", clientId)
            .header("Authorization", basicAuthHeader))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Aelin Galathynius"))
        .andExpect(jsonPath("$.nif").value("123456789A"))
        .andExpect(jsonPath("$.address").value("Terrasen"));
  }

  @Test
  void testUpdateClient() throws Exception {
    UUID clientId = UUID.randomUUID();
    Client updatedClient = new Client("Rowan Whitethorn", "987654321B", "Terrasen");
    updatedClient.setId(clientId);

    when(clientService.update(eq(clientId), any(Client.class))).thenReturn(updatedClient);

    mockMvc.perform(put("/clients/{id}", clientId)
            .header("Authorization", basicAuthHeader)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(updatedClient)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Rowan Whitethorn"))
        .andExpect(jsonPath("$.nif").value("987654321B"))
        .andExpect(jsonPath("$.address").value("Terrasen"));
  }

  @Test
  void testDeleteClient_WithSales() throws Exception {
    UUID clientId = UUID.randomUUID();
    when(saleService.hasSales(clientId)).thenReturn(true);

    mockMvc.perform(delete("/clients/{id}", clientId)
            .header("Authorization", basicAuthHeader))
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void testDeleteClient_NoSales() throws Exception {
    UUID clientId = UUID.randomUUID();
    when(saleService.hasSales(clientId)).thenReturn(false);
    doNothing().when(clientService).delete(clientId);

    mockMvc.perform(delete("/clients/{id}", clientId)
            .header("Authorization", basicAuthHeader))
        .andExpect(status().isNoContent());
  }

  @Test
  void testGetClientSales() throws Exception {
    UUID clientId = UUID.randomUUID();
    Sale sale = new Sale(new Client("Aelin Galathynius", "123456789A", "Terrasen"), Collections.emptyList());
    sale.setId(UUID.randomUUID());

    when(saleService.getPastSales(clientId)).thenReturn(Collections.singletonList(sale));

    mockMvc.perform(get("/clients/{id}/sales", clientId)
            .header("Authorization", basicAuthHeader))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1));
  }
}
