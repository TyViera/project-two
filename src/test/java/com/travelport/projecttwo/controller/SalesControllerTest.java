package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.ProjectTwoApplication;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SalesControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    public void loadInitialData() throws SQLException {
        try (var conn = dataSource.getConnection()) {
            try (var statement = conn.prepareStatement("INSERT INTO products (id, name , code, stock) VALUES (?, ?, ?, ? )")) {
                statement.setString(1, "asfas-123sad-wdsadf-1k23");
                statement.setString(2, "Laptop Pro");
                statement.setString(3, "67YTN09");
                statement.setString(4, "100");
                statement.executeUpdate();

                statement.setString(1, "asfas-123sad-wdsadf-123j");
                statement.setString(2, "Smartwatch");
                statement.setString(3, "67YTN08");
                statement.setString(4, "1");
                statement.executeUpdate();
            }
            try (var statement = conn.prepareStatement("INSERT INTO clients (id, name, nif, address) VALUES (?, ?, ?, ?)")) {
                statement.setString(1, "123123adsf-asdf1");
                statement.setString(2, "Carlos Martinez");
                statement.setString(3, "12345678Z");
                statement.setString(4, "Calle Mayor, 10, Madrid, Spain");
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
    void sellProduct_Ok() throws Exception {
        String saleJson = """
                {
                  "client": {
                    "id": "123123adsf-asdf1"
                  },
                  "products": [
                    {
                      "id": "asfas-123sad-wdsadf-1k23",
                      "quantity": "10"
                    },
                     {
                      "id": "asfas-123sad-wdsadf-123j",
                      "quantity": "1"
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(saleJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Operation Successful"));
    }

    @Test
    void sellProduct_InvalidStock() throws Exception {
        String saleJson = """
                {
                  "client": {
                    "id": "123123adsf-asdf1"
                  },
                  "products": [
                     {
                      "id": "asfas-123sad-wdsadf-123j",
                      "quantity": "10"
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(saleJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Requested stock not available"));
    }
    @Test
    void sellProduct_InvalidProduct() throws Exception {
        String saleJson = """
                {
                  "client": {
                    "id": "123123adsf-asdf1"
                  },
                  "products": [
                     {
                      "id": "asfas",
                      "quantity": "1"
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(saleJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }

    @Test
    void getMostSoldProducts() {
    }
}