package com.travelport.projecttwo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.projecttwo.controllers.ProductStockController;
import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.ProductStock;
import com.travelport.projecttwo.services.ProductStockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductStockControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ProductStockService productStockService;

  @InjectMocks
  private ProductStockController productStockController;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(productStockController).build();
  }

  @Test
  void getProductStockFoundTest() throws Exception {
    UUID productId = UUID.randomUUID();
    Product product = new Product();
    product.setId(productId);

    ProductStock productStock = new ProductStock();
    productStock.setProduct(product);
    productStock.setQuantity(100);

    when(productStockService.getStockByProductId(productId)).thenReturn(productStock);

    mockMvc.perform(get("/products/{id}/stock", productId)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.stock").value(100));

    verify(productStockService, times(1)).getStockByProductId(productId);
  }


  @Test
  void getProductStockNotFoundTest() throws Exception {
    UUID productId = UUID.randomUUID();

    when(productStockService.getStockByProductId(productId)).thenReturn(null);

    mockMvc.perform(get("/products/{id}/stock", productId)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    verify(productStockService, times(1)).getStockByProductId(productId);
  }
}
