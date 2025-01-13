package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.dto.SaleRequest;
import com.travelport.projecttwo.service.SaleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.Mockito.*;

@WebMvcTest(SaleController.class)
public class SaleControllerTest {

    @Mock
    private SaleService saleService;

    @InjectMocks
    private SaleController saleController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sellProductTest() throws Exception {
        SaleRequest request = new SaleRequest();
        request.setProductId("1");
        request.setClientId("1");
        request.setQuantity(10);

        doNothing().when(saleService).sellProduct(any(SaleRequest.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/sales")
                        .contentType("application/json")
                        .content("{\"productId\":\"1\",\"clientId\":\"1\",\"quantity\":10}"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
