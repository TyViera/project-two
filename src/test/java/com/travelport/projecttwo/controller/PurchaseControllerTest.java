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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PurchaseControllerTest {
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
                statement.setString(4, "50");
                statement.executeUpdate();
            }
        }
    }

    public void clearInserts() throws SQLException {
        try (var conn = dataSource.getConnection()) {
            var statement = conn.prepareStatement("DELETE FROM sales_det");
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
    void purchaseProduct_Ok() throws Exception {
        String purchaseJson = """
                {
                  "supplier": "Amazon",
                  "products": [
                    {
                      "id": "asfas-123sad-wdsadf-1k23",
                      "quantity": "100"
                    },
                    {
                      "id": "asfas-123sad-wdsadf-123j",
                      "quantity": "100"
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseJson))
                .andExpect(status().isCreated())
                .andExpect(content().string("Product successfully purchased"));
    }

    @Test
    void purchaseProduct_InvalidQuantity() throws Exception {
        String purchaseJson = """
                {
                  "supplier": "Amazon",
                  "products": [
                    {
                      "id": "asfas-123sad-wdsadf-1k23",
                      "quantity": "-100"
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseJson))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Purchase was not made"));
    }

    @Test
    void purchaseProduct_InvalidProduct() throws Exception {
        String purchaseJson = """
                {
                  "supplier": "Amazon",
                  "products": [
                    {
                      "id": "asfas",
                      "quantity": "100"
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseJson))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Purchase was not made"));
    }
}