package com.travelport.projecttwo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    public void loadInitialData() throws SQLException {
        try (var conn = dataSource.getConnection()) {
            try (var statement = conn.prepareStatement("INSERT INTO clients (id, name, nif, address) VALUES (?, ?, ?, ?)")) {
                statement.setString(1, "123123adsf-asdf1");
                statement.setString(2, "Carlos Martinez");
                statement.setString(3, "12345678Z");
                statement.setString(4, "Calle Mayor, 10, Madrid, Spain");
                statement.executeUpdate();

                statement.setString(1, "123123adsf-asdf2");
                statement.setString(2, "Ana Lopez");
                statement.setString(3, "23456789J");
                statement.setString(4, "Calle de la Libertad, 15, Barcelona, Spain");
                statement.executeUpdate();
            }
            try (var statement = conn.prepareStatement("INSERT INTO sales (id, client_id) VALUES (?, ?)")) {
                statement.setString(1, "aueahjeauh8978879");
                statement.setString(2, "123123adsf-asdf1");
                statement.executeUpdate();
            }

            try (var statement = conn.prepareStatement("INSERT INTO products (id, name , code, stock) VALUES (?, ?, ?, ? )")) {
                statement.setString(1, "asfas-123sad-wdsadf-1k23");
                statement.setString(2, "Laptop Pro");
                statement.setString(3, "67YTN09");
                statement.setString(4, "100");
                statement.executeUpdate();
            }

            try (var statement = conn.prepareStatement("INSERT INTO sales_det (sale_id, product_id, quantity) VALUES (?, ?, ? )")) {
                statement.setString(1, "aueahjeauh8978879");
                statement.setString(2, "asfas-123sad-wdsadf-1k23");
                statement.setString(3, "12");
                statement.executeUpdate();
            }


        }
    }

    public void clearInserts() throws SQLException {
        try (var conn = dataSource.getConnection()) {
            var statement = conn.prepareStatement("DELETE FROM clients");
            statement.execute();

            statement = conn.prepareStatement("DELETE FROM products");
            statement.execute();
        }
    }

    @BeforeEach
    public void testSetUp() throws SQLException {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        clearInserts();
        loadInitialData();
    }

    @Test
    void getClients_Ok() throws Exception {
        mockMvc.perform(get("/clients")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("123123adsf-asdf1"))
                .andExpect(jsonPath("$[1].id").value("123123adsf-asdf2"))
                .andExpect(jsonPath("$[0].name").value("Carlos Martinez"))
                .andExpect(jsonPath("$[1].name").value("Ana Lopez"))
                .andExpect(jsonPath("$[0].nif").value("12345678Z"))
                .andExpect(jsonPath("$[1].nif").value("23456789J"))
                .andExpect(jsonPath("$[0].address").value("Calle Mayor, 10, Madrid, Spain"))
                .andExpect(jsonPath("$[1].address").value("Calle de la Libertad, 15, Barcelona, Spain"));
    }

    @Test
    void findById_Ok() throws Exception {
        mockMvc.perform(get("/clients/123123adsf-asdf1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("123123adsf-asdf1"))
                .andExpect(jsonPath("$.name").value("Carlos Martinez"))
                .andExpect(jsonPath("$.nif").value("12345678Z"))
                .andExpect(jsonPath("$.address").value("Calle Mayor, 10, Madrid, Spain"));
    }

    @Test
    void findById_NotFound() throws Exception{
        mockMvc.perform(get("/clients/123123adsf-asdfj")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("Client not found"));
        ;
    }

    @Test
    void postClient_Ok() throws Exception {
        String clientJson = """
        {
            "id": "randomUUID",
            "name": "Juan Pérez",
            "nif": "34567890A",
            "address": "Calle de la Paz, 30, Valencia, España"
        }
        """;

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Juan Pérez"))
                .andExpect(jsonPath("$.nif").value("34567890A"))
                .andExpect(jsonPath("$.address").value("Calle de la Paz, 30, Valencia, España"));
    }

    @Test
    void postClient_Invalid() throws Exception {
        String clientJson = """
        {
            "name": "Juan Pérez",
            "nif": "12345678Z",
            "address": "Calle de la Paz, 30, Valencia, España"
        }
        """;

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Client not saved"));
    }

    @Test
    void updateClientById() throws Exception {
        String updatedClientJson = """
        {
            "id": "123123adsf-asdf1",
            "name": "Carlos Martínez Updated",
            "nif": "12345678Z",
            "address": "Calle Mayor, 10, Madrid, España (Updated)"
        }
        """;

        mockMvc.perform(put("/clients/123123adsf-asdf1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedClientJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("123123adsf-asdf1"))
                .andExpect(jsonPath("$.name").value("Carlos Martínez Updated"))
                .andExpect(jsonPath("$.nif").value("12345678Z"))
                .andExpect(jsonPath("$.address").value("Calle Mayor, 10, Madrid, España (Updated)"));
    }

    @Test
    void deleteClientById() throws Exception {
        mockMvc.perform(delete("/clients/123123adsf-asdf2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Operation successful"));
    }

    @Test
    void deleteClientById_NotFound() throws Exception {
        mockMvc.perform(delete("/clients/123123adsf-asdfj"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Client not found"));
    }

    @Test
    void deleteClientById_WithSales() throws Exception {
        mockMvc.perform(delete("/clients/123123adsf-asdf1"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Sales in the system"));
    }

    @Test
    void getClientSales_Ok() throws Exception {
        mockMvc.perform(get("/clients/123123adsf-asdf1/sales")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getClientSales_NotFound() throws Exception {
        mockMvc.perform(get("/clients/123123adsf-asdfj/sales")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Client has no sales"));
    }


}
