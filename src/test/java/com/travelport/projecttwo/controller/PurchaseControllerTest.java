package com.travelport.projecttwo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.projecttwo.model.request.PurchaseInput;
import com.travelport.projecttwo.service.PurchaseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void purchaseProduct_withValidInput_shouldReturnCreated() throws Exception {
        PurchaseInput.ProductRef productRef = new PurchaseInput.ProductRef();
        productRef.setId("123e4567-e89b-12d3-a456-426614174000");

        PurchaseInput purchaseInput = new PurchaseInput();
        purchaseInput.setProduct(productRef);
        purchaseInput.setSupplier("Best Supplier Inc.");
        purchaseInput.setQuantity(50);

        Mockito.doNothing().when(purchaseService).purchaseProduct(Mockito.any(PurchaseInput.class));

        mockMvc.perform(post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseInput)))
                .andExpect(status().isCreated());
    }

    @Test
    void purchaseProduct_withInvalidInput_shouldReturnBadRequest() throws Exception {
        PurchaseInput purchaseInput = new PurchaseInput();

        mockMvc.perform(post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseInput)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void purchaseProduct_withoutAuthentication_shouldReturnForbidden() throws Exception {
        PurchaseInput.ProductRef productRef = new PurchaseInput.ProductRef();
        productRef.setId("123e4567-e89b-12d3-a456-426614174000");

        PurchaseInput purchaseInput = new PurchaseInput();
        purchaseInput.setProduct(productRef);
        purchaseInput.setSupplier("Best Supplier Inc.");
        purchaseInput.setQuantity(50);

        mockMvc.perform(post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseInput)))
                .andExpect(status().isForbidden());
    }
}