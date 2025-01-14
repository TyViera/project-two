package com.travelport.projecttwo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.projecttwo.controllers.dtos.past_sales.ClientPastSalesDto;
import com.travelport.projecttwo.controllers.dtos.past_sales.ProductInPastSalesDto;
import com.travelport.projecttwo.controllers.dtos.past_sales.ProductsBoughtByClientDto;
import com.travelport.projecttwo.exceptions.ClientHasSalesException;
import com.travelport.projecttwo.services.IClientService;
import com.travelport.projecttwo.services.domainModels.ClientDomain;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest {

    private static final String CLIENT_TEST_ID = "2cba93e4-1eac-4ba7-b8cb-6febf3384318";
    private static final String SALE1_TEST_ID = "2cba93e4-1eac-4ba7-b8cb-6febf3384317";
    private static final String SALE2_TEST_ID = "2cba93e4-1eac-4ba7-b8cb-6febf3384316";
    private static final String PRODUCT1_TEST_ID = "2cba93e4-1eac-4ba7-b8cb-6febf3384315";
    private static final String PRODUCT2_TEST_ID = "2cba93e4-1eac-4ba7-b8cb-6febf3384314";

    @MockBean
    private IClientService clientService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getClients() throws Exception {
        // GIVEN
        when(clientService.getClients()).thenReturn(List.of());

        // WHEN
        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    void getClientById_whenClientDoesNotExist_shouldReturn404() throws Exception {
        // GIVEN
        when(clientService.getClientById(any())).thenReturn(Optional.empty());

        // WHEN
        mockMvc.perform(get("/clients/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getClientById_whenClientExists_shouldReturnOkAndClient() throws Exception {
        // GIVEN
        var clientDomain = new ClientDomain();
        clientDomain.setId(CLIENT_TEST_ID);
        clientDomain.setName("John Doe");
        clientDomain.setNif("12345678A");
        clientDomain.setAddress("Fake St 123");

        when(clientService.getClientById(CLIENT_TEST_ID)).thenReturn(Optional.of(clientDomain));

        // WHEN
        mockMvc.perform(get("/clients/" + CLIENT_TEST_ID))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CLIENT_TEST_ID))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.nif").value("12345678A"))
                .andExpect(jsonPath("$.address").value("Fake St 123"));
    }


    @Test
    void postClient_whenNifAlreadyExists_shouldReturn400() throws Exception {
        // GIVEN
        when(clientService.createClient(any(ClientDomain.class))).thenThrow(new IllegalArgumentException("NIF already exists"));

        // WHEN & THEN
        mockMvc.perform(post("/clients")
                .contentType("application/json")
                .content("""
                        {
                            "name": "John Doe",
                            "nif": "12345678A",
                            "address": "Fake St 123"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void postClient_whenNifDoesntExist_shouldCreateAndReturn201AndClient() throws Exception {
        // GIVEN
        var requestBody = """
                {
                    "name": "John Doe",
                    "nif": "12345678A",
                    "address": "Fake St 123"
                }
                """;

        var clientDomain = new ClientDomain();
        clientDomain.setId(CLIENT_TEST_ID);
        clientDomain.setName("John Doe");
        clientDomain.setNif("12345678A");
        clientDomain.setAddress("Fake St 123");

        when(clientService.createClient(any(ClientDomain.class))).thenReturn(clientDomain);

        // WHEN
        mockMvc.perform(post("/clients")
                        .contentType("application/json")
                        .content(requestBody))
                // THEN
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(CLIENT_TEST_ID))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.nif").value("12345678A"))
                .andExpect(jsonPath("$.address").value("Fake St 123"));
    }


    @Test
    void updateClient_whenNifDoestExist_shouldReturn404() throws Exception {
        // GIVEN
        when(clientService.updateClient(eq(CLIENT_TEST_ID), any(ClientDomain.class))).thenThrow(new IllegalArgumentException("Client not found"));

        // WHEN & THEN
        mockMvc.perform(put("/clients/" + CLIENT_TEST_ID)
                .contentType("application/json")
                .content("""
                        {
                            "name": "John Doe",
                            "nif": "12345678A",
                            "address": "Fake St 123"
                        }
                        """))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateClient_whenNifExists_shouldReturn200AndClient() throws Exception {
        // GIVEN
        var requestBody = """
                {
                    "name": "John Doe",
                    "nif": "12345678A",
                    "address": "Fake St 123"
                }
                """;

        var clientDomain = new ClientDomain();
        clientDomain.setId(CLIENT_TEST_ID);
        clientDomain.setName("John Doe");
        clientDomain.setNif("12345678A");
        clientDomain.setAddress("Fake St 123");

        when(clientService.updateClient(eq(CLIENT_TEST_ID), any(ClientDomain.class))).thenReturn(clientDomain);

        // WHEN
        mockMvc.perform(put("/clients/" + CLIENT_TEST_ID)
                        .contentType("application/json")
                        .content(requestBody))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CLIENT_TEST_ID))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.nif").value("12345678A"))
                .andExpect(jsonPath("$.address").value("Fake St 123"));
    }

    @Test
    void deleteClient_whenNifDoesntExist_shouldReturn404() throws Exception {
        // GIVEN
        doThrow(new IllegalArgumentException("Client not found")).when(clientService).deleteClient(eq(CLIENT_TEST_ID));

        // WHEN & THEN
        mockMvc.perform(delete("/clients/" + CLIENT_TEST_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteClient_whenClientHasSales_shouldReturn422() throws Exception {
        // GIVEN
        doThrow(new ClientHasSalesException("Client has sales")).when(clientService).deleteClient(eq(CLIENT_TEST_ID));

        // WHEN & THEN
        mockMvc.perform(delete("/clients/" + CLIENT_TEST_ID))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void deleteClient_whenNifExistsAndClientHasNoSales_shouldReturn204() throws Exception {
        // GIVEN
        // WHEN & THEN
        mockMvc.perform(delete("/clients/" + CLIENT_TEST_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void getClientSales_whenNoSales_shouldReturnEmptyList() throws Exception {
        // GIVEN
        when(clientService.getClientSales(eq(CLIENT_TEST_ID))).thenReturn(List.of());

        // WHEN & THEN
        mockMvc.perform(get("/clients/" + CLIENT_TEST_ID + "/sales"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getClientSales_whenSalesExist_shouldReturnSales() throws Exception {
        // GIVEN
            // Sale 1
        var productInSale1 = new ProductInPastSalesDto(PRODUCT1_TEST_ID, "Laptop");

        var product1 = new ProductsBoughtByClientDto(productInSale1, 1);

        var productInSale2 = new ProductInPastSalesDto(PRODUCT2_TEST_ID, "Phone");

        var product2 = new ProductsBoughtByClientDto(productInSale2, 2);

        var sale1 = new ClientPastSalesDto();
        sale1.setId(SALE1_TEST_ID);
        sale1.setProducts(List.of(product1, product2));

            // Sale 2
        var productInSale3 = new ProductInPastSalesDto(PRODUCT1_TEST_ID, "Laptop");

        var product3 = new ProductsBoughtByClientDto(productInSale3, 1);

        var productInSale4 = new ProductInPastSalesDto(PRODUCT2_TEST_ID, "Phone");

        var product4 = new ProductsBoughtByClientDto(productInSale4, 2);

        var sale2 = new ClientPastSalesDto();
        sale2.setId(SALE2_TEST_ID);
        sale2.setProducts(List.of(product3, product4));

        var sales = List.of(sale1, sale2);

        when(clientService.getClientSales(eq(CLIENT_TEST_ID))).thenReturn(sales);

        // WHEN & THEN
        mockMvc.perform(get("/clients/" + CLIENT_TEST_ID + "/sales"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(sales)));
    }
}