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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

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

                statement.setString(1, "asfas-123sad-wdsadf-123j");
                statement.setString(2, "Smartwatch");
                statement.setString(3, "67YTN08");
                statement.setString(4, "50");
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
    void getProducts() throws Exception {
        mockMvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("asfas-123sad-wdsadf-1k23"))
                .andExpect(jsonPath("$[1].id").value("asfas-123sad-wdsadf-123j"))
                .andExpect(jsonPath("$[0].name").value("Laptop Pro"))
                .andExpect(jsonPath("$[1].name").value("Smartwatch"))
                .andExpect(jsonPath("$[0].code").value("67YTN09"))
                .andExpect(jsonPath("$[1].code").value("67YTN08"));
    }

    @Test
    void findById_Ok() throws Exception {
        mockMvc.perform(get("/products/asfas-123sad-wdsadf-1k23")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("asfas-123sad-wdsadf-1k23"))
                .andExpect(jsonPath("$.name").value("Laptop Pro"))
                .andExpect(jsonPath("$.code").value("67YTN09"));
    }

    @Test
    void findById_NotFound() throws Exception {
        mockMvc.perform(get("/products/asfas-123sad-wdsadf-1k2312")
                 .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void postProduct_Ok() throws Exception {
        String productJson = """
        {
          "name": "MacBook Pro",
          "code": "123123OK"
        }
        """;

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("MacBook Pro"))
                .andExpect(jsonPath("$.code").value("123123OK"));
    }

    @Test
    void postProduct_Invalid() throws Exception {
        String productJson = """
        {
          "name": "MacBook Pro",
          "code": "67YTN09"
        }
        """;
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().string("Product not saved"));
        ;
    }

    @Test
    void updateProductById_Ok() throws Exception {
        String updatedClientJson = """
        {
          "name": "Product Updated",
          "code": "67YTN09d"
        }
        """;

        mockMvc.perform(put("/products/asfas-123sad-wdsadf-1k23")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedClientJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("asfas-123sad-wdsadf-1k23"))
                .andExpect(jsonPath("$.name").value("Product Updated"))
                .andExpect(jsonPath("$.code").value("67YTN09d"));
    }

    @Test
    void updateProductById_NotFound() throws Exception {
        String updatedClientJson = """
        {
          "name": "Product Updated",
          "code": "67YTN09D"
        }
        """;
        mockMvc.perform(put("/products/asfas-123sad-wdsadf-1k20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedClientJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }

    @Test
    void deleteProductById_Ok() throws Exception {
        mockMvc.perform(delete("/products/asfas-123sad-wdsadf-123j"))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Operation successful"));
    }

    @Test
    void deleteProductById_NotFound() throws Exception {
        mockMvc.perform(delete("/products/asfas"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }

    @Test
    void deleteProductById_HasSales() throws Exception {
        mockMvc.perform(delete("/products/asfas-123sad-wdsadf-1k23"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("Exists a sales in the system for this product"));
    }
}