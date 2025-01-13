package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.model.Purchase;
import com.travelport.projecttwo.model.PurchaseProduct;
import com.travelport.projecttwo.services.impl.PurchaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PurchaseControllerTest {

    @Mock
    private PurchaseServiceImpl purchaseService;

    @InjectMocks
    private PurchaseController purchaseController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(purchaseController).build();
    }

    @Test
    @WithMockUser(username = "user", password = "123")
    void addPurchaseTest() throws Exception {
        Purchase newPurchase = new Purchase("1", "Supplier A", List.of(new PurchaseProduct("111", 1)));
        when(purchaseService.addPurchase(any(Purchase.class))).thenReturn(newPurchase);

        mockMvc.perform(MockMvcRequestBuilders.post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "productId": "ProductA",
                                  "quantity": 5
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value("ProductA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(100.0));
    }
}
