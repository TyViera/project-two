package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.dto.PurchaseRequest;
import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    private PurchaseRequest purchaseRequest;
    private PurchaseRequest.PurchasedProduct product1;
    private PurchaseRequest.PurchasedProduct product2;

    @BeforeEach
    void setUp() {
        product1 = new PurchaseRequest.PurchasedProduct("productId1", 5);
        product2 = new PurchaseRequest.PurchasedProduct("productId2", 10);

        purchaseRequest = new PurchaseRequest("Amazon", List.of(product1, product2));
    }

    @Test
    void renewStock_Success() {
        when(productRepository.findById(product1.getId())).thenReturn(Optional.of(new Product("productId1", "Product A", "A123")));
        when(productRepository.findById(product2.getId())).thenReturn(Optional.of(new Product("productId2", "Product B", "B123")));
        when(productRepository.updateProductStock(product1.getId(), product1.getQuantity())).thenReturn(1);
        when(productRepository.updateProductStock(product2.getId(), product2.getQuantity())).thenReturn(1);

        assertDoesNotThrow(() -> purchaseService.renewStock(purchaseRequest));

        verify(productRepository, times(1)).findById(product1.getId());
        verify(productRepository, times(1)).findById(product2.getId());
        verify(productRepository, times(1)).updateProductStock(product1.getId(), product1.getQuantity());
        verify(productRepository, times(1)).updateProductStock(product2.getId(), product2.getQuantity());
    }

    @Test
    void renewStock_ProductNotFound() {
        when(productRepository.findById(product1.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> purchaseService.renewStock(purchaseRequest));

        verify(productRepository, times(1)).findById(product1.getId());
        verify(productRepository, never()).updateProductStock(anyString(), anyInt());
    }

}
