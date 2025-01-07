package com.travelport.projecttwo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.jpa.ProductRepository;
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
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
        product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("ProductName");
        product.setCode("Prod12345");
        productRepository.save(product);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(product.getName()));
    }

    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(get("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product.getName()));
    }

    @Test
    public void testCreateProduct() throws Exception {
        Product newProduct = new Product();
        newProduct.setName("NewProduct");
        newProduct.setCode("New12345");

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(newProduct.getName()));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        product.setName("UpdatedName");

        mockMvc.perform(put("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedName"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
