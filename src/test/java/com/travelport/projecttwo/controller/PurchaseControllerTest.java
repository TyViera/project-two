package com.travelport.projecttwo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.projecttwo.controllers.PurchaseController;
import com.travelport.projecttwo.requests.PurchaseRequest;
import com.travelport.projecttwo.requests.ProductPurchaseRequest;
import com.travelport.projecttwo.services.PurchaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PurchaseControllerTest {

  private MockMvc mockMvc;

  @Mock
  private PurchaseServiceImpl purchaseService;

  @InjectMocks
  private PurchaseController purchaseController;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(purchaseController).build();
  }

  @Test
  void purchaseProductSuccessTest() throws Exception {
    PurchaseRequest purchaseRequest = new PurchaseRequest();
    purchaseRequest.setSupplier("Rhysand NightCourt");

    ProductPurchaseRequest productPurchaseRequest1 = new ProductPurchaseRequest();
    productPurchaseRequest1.setId(UUID.randomUUID());
    productPurchaseRequest1.setQuantity(10);

    ProductPurchaseRequest productPurchaseRequest2 = new ProductPurchaseRequest();
    productPurchaseRequest2.setId(UUID.randomUUID());
    productPurchaseRequest2.setQuantity(5);

    purchaseRequest.setProducts(Arrays.asList(productPurchaseRequest1, productPurchaseRequest2));

    doNothing().when(purchaseService).purchaseProduct(eq(purchaseRequest.getSupplier()), anyList());

    mockMvc.perform(post("/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(purchaseRequest)))
        .andExpect(status().isCreated());

    verify(purchaseService, times(1)).purchaseProduct(eq("Rhysand NightCourt"), anyList());
  }

  @Test
  void purchaseProductFailureTest() throws Exception {
    PurchaseRequest purchaseRequest = new PurchaseRequest();
    purchaseRequest.setSupplier("Rhysand NightCourt");

    ProductPurchaseRequest productPurchaseRequest = new ProductPurchaseRequest();
    productPurchaseRequest.setId(UUID.randomUUID());
    productPurchaseRequest.setQuantity(10);

    purchaseRequest.setProducts(Arrays.asList(productPurchaseRequest));

    doThrow(new RuntimeException("Internal Server Error")).when(purchaseService).purchaseProduct(anyString(), anyList());

    mockMvc.perform(post("/purchases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(purchaseRequest)))
        .andExpect(status().isInternalServerError());

    verify(purchaseService, times(1)).purchaseProduct(eq("Rhysand NightCourt"), anyList());
  }
}
