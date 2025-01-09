package com.travelport.projecttwo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.SaleRequest;
import com.travelport.projecttwo.entities.SalesRecord;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        salesRecordRepository.deleteAll();
        productRepository.deleteAll();

        product1 = new Product();
        product1.setId(UUID.randomUUID());
        product1.setName("Product1");
        product1.setCode("Prod12345");
        product1.setQuantity(100);
        productRepository.save(product1);

        product2 = new Product();
        product2.setId(UUID.randomUUID());
        product2.setName("Product2");
        product2.setCode("Prod67890");
        product2.setQuantity(100);
        productRepository.save(product2);

        SalesRecord salesRecord1 = new SalesRecord();
        salesRecord1.setId(UUID.randomUUID());
        salesRecord1.setProductId(product1.getId());
        salesRecord1.setClientId(UUID.randomUUID());
        salesRecord1.setQuantitySold(50);
        salesRecordRepository.save(salesRecord1);

        SalesRecord salesRecord2 = new SalesRecord();
        salesRecord2.setId(UUID.randomUUID());
        salesRecord2.setProductId(product2.getId());
        salesRecord2.setClientId(UUID.randomUUID());
        salesRecord2.setQuantitySold(30);
        salesRecordRepository.save(salesRecord2);
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

    @Test
    @WithMockUser(username = "user", password = "password")
    public void testGetSalesByClientId() throws Exception {
        UUID clientId = UUID.randomUUID();
        UUID saleId = UUID.randomUUID();

        SalesRecord salesRecord = new SalesRecord();
        salesRecord.setId(saleId);
        salesRecord.setProductId(product.getId());
        salesRecord.setClientId(clientId);
        salesRecord.setQuantitySold(10);
        salesRecordRepository.save(salesRecord);

        mockMvc.perform(get("/clients/" + clientId + "/sales"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"" + saleId + "\",\"products\":[{\"productId\":\"" + product.getId() + "\",\"productName\":\"" + product.getName() + "\",\"quantity\":10}]}]"));
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    public void testGetMostSoldProducts() throws Exception {
        mockMvc.perform(get("/sales/most-sold-products"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"productId\":\"" + product1.getId() + "\",\"productName\":\"" + product1.getName() + "\",\"quantity\":50}," +
                        "{\"productId\":\"" + product2.getId() + "\",\"productName\":\"" + product2.getName() + "\",\"quantity\":30}]"));
    }
}
