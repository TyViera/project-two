package com.travelport.projecttwo.service.Impl;

import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.model.Response.MostSoldProductResponse;
import com.travelport.projecttwo.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IncomeReportServiceImplTest {

    @Mock
    private SaleRepository saleRepository;

    @InjectMocks
    private IncomeReportServiceImpl incomeReportService;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId("1");
        product.setName("Product A");
    }

    @Test
    void getMostSoldProducts() {

        Object[] result1 = new Object[]{product, 100L};
        List<Object[]> results = List.of(new Object[][]{result1});
        when(saleRepository.findTop5MostSoldProducts()).thenReturn(results);

        List<MostSoldProductResponse> mostSoldProducts = incomeReportService.getMostSoldProducts();

        assertNotNull(mostSoldProducts);
        assertEquals(1, mostSoldProducts.size());

        MostSoldProductResponse responseItem = mostSoldProducts.get(0);
        assertEquals("1", responseItem.getProduct().getId());
        assertEquals("Product A", responseItem.getProduct().getName());
        assertEquals(100L, responseItem.getQuantity());
    }

    @Test
    void getMostSoldProductsEmpty() {

        when(saleRepository.findTop5MostSoldProducts()).thenReturn(List.of());

        List<MostSoldProductResponse> mostSoldProducts = incomeReportService.getMostSoldProducts();

        assertNotNull(mostSoldProducts);
        assertEquals(0, mostSoldProducts.size());
    }
}