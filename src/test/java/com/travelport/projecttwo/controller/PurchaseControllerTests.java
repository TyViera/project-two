package com.travelport.projecttwo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.PurchaseRequest;
import com.travelport.projecttwo.jpa.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PurchaseControllerTests {

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
        product.setQuantity(100);
        productRepository.save(product);
    }

    @Test
    public void testPurchaseProduct() throws Exception {
        PurchaseRequest purchaseRequest = new PurchaseRequest();
        PurchaseRequest.ProductRequest productRequest = new PurchaseRequest.ProductRequest();
        productRequest.setId(product.getId().toString());
        purchaseRequest.setProduct(productRequest);
        purchaseRequest.setSupplier("SupplierName");
        purchaseRequest.setQuantity(50);

        mockMvc.perform(post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(purchaseRequest))
                        .with(httpBasic("user", "password")))
                .andExpect(status().isCreated());
    }
}