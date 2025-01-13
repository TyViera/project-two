package com.travelport.projecttwo.service.Impl;

import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.model.Purchase;
import com.travelport.projecttwo.model.request.PurchaseInput;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchaseServiceImplTest {

    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;
    private PurchaseServiceImpl purchaseService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        purchaseRepository = mock(PurchaseRepository.class);
        purchaseService = new PurchaseServiceImpl(productRepository, purchaseRepository);
    }

    @Test
    void purchaseProduct_successfulPurchase() {
        Product product = new Product();
        product.setId("product123");
        product.setStock(10);

        PurchaseInput purchaseInput = new PurchaseInput();
        purchaseInput.setSupplier("Best Supplier");
        purchaseInput.setQuantity(5);
        purchaseInput.setProduct(product);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        purchaseService.purchaseProduct(purchaseInput);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());

        Product savedProduct = productCaptor.getValue();
        assertEquals(15, savedProduct.getStock(), "Stock should be updated correctly");

        ArgumentCaptor<Purchase> purchaseCaptor = ArgumentCaptor.forClass(Purchase.class);
        verify(purchaseRepository).save(purchaseCaptor.capture());

        Purchase savedPurchase = purchaseCaptor.getValue();
        assertEquals(product, savedPurchase.getProduct(), "The product in the purchase should match the input product");
        assertEquals("Best Supplier", savedPurchase.getSupplier(), "Supplier should match the input");
        assertEquals(5, savedPurchase.getQuantity(), "Quantity should match the input");
    }

    @Test
    void purchaseProduct_productNotFound() {
        PurchaseInput purchaseInput = new PurchaseInput();
        Product product = new Product();
        product.setId("nonexistent");
        purchaseInput.setProduct(product);

        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> purchaseService.purchaseProduct(purchaseInput));

        assertEquals("Product not found", exception.getMessage(), "Exception message should match");
        verify(purchaseRepository, never()).save(any());
    }
}