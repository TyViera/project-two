package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.dto.MostSoldProductDTO;
import com.travelport.projecttwo.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getMostSoldProductsTest() throws Exception {
        MostSoldProductDTO product1 = new MostSoldProductDTO();
        MostSoldProductDTO product2 = new MostSoldProductDTO();

        when(reportService.getMostSoldProducts()).thenReturn(List.of(product1, product2));

        mockMvc.perform(MockMvcRequestBuilders.get("/reports/most-sold-products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productId").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").value("Product1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].quantitySold").value(100));
    }
}
