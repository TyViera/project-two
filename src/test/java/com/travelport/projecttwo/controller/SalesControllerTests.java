package com.travelport.projecttwo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.SaleRequest;
import com.travelport.projecttwo.jpa.ProductRepository;
import com.travelport.projecttwo.jpa.SalesRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SalesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SalesRecordRepository salesRecordRepository;

    private Product product;

    @BeforeEach
    public void setUp() {
        salesRecordRepository.deleteAll();
        productRepository.deleteAll();
        product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("ProductName");
        product.setCode("Prod12345");
        product.setQuantity(100);
        productRepository.save(product);
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void testSellProduct() throws Exception {
        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setProductId(product.getId());
        saleRequest.setClientId(UUID.randomUUID());
        saleRequest.setQuantity(10);

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(saleRequest)))
                .andExpect(status().isCreated());
    }
}
