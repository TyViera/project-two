package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entity.Product;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.PurchaseRepository;
import com.travelport.projecttwo.entity.Purchase;
import com.travelport.projecttwo.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PurchaseServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PurchaseRepository purchaseRepository;

    @InjectMocks
    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void renewStockTest() {
        String productId = "1";
        Product product = new Product();
        product.setId(productId);
        product.setStock(10);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        purchaseService.renewStock(productId, "Supplier1", 5);

        assertEquals(15, product.getStock());
        verify(purchaseRepository, times(1)).save(any(Purchase.class));
    }

    @Test
    void renewStockProductNotFoundTest() {
        String productId = "1";
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> purchaseService.renewStock(productId, "Supplier1", 5));
    }
}
