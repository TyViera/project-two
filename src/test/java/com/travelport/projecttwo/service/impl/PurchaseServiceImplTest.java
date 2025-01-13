package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.controller.model.PurchaseRequest;
import com.travelport.projecttwo.exception.ProductNotFoundException;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.entity.ProductEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class PurchaseServiceImplTest {

  @InjectMocks
  private PurchaseServiceImpl purchaseService;

  @Mock
  private ProductRepository productRepository;

  @Test
  @DisplayName("Given empty database, when renewStock, then throw ProductNotFoundException")
  void testRenewStock_emptyDatabase_throwException() {
    var productId = UUID.randomUUID().toString();
    var product = new PurchaseRequest.Product();
    product.setId(productId);
    product.setQuantity(5);

    var productList = List.of(product);

    var purchaseRequest = new PurchaseRequest();
    purchaseRequest.setSupplier("supplier");
    purchaseRequest.setProducts(productList);

    Mockito.when(productRepository.findById(eq(productId))).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> purchaseService.renewStock(purchaseRequest));

    Mockito.verify(productRepository).findById(eq(productId));
    Mockito.verify(productRepository, Mockito.never()).save(Mockito.any(ProductEntity.class));
  }

  @Test
  @DisplayName("Given product exists, when renewStock, then update stock")
  void testRenewStock_productExists_success() {
    var productId = UUID.randomUUID().toString();

    var productEntity = new ProductEntity();
    productEntity.setId(productId);
    productEntity.setStock(10);

    var product = new PurchaseRequest.Product();
    product.setId(productId);
    product.setQuantity(5);

    var productList = List.of(product);

    var purchaseRequest = new PurchaseRequest();
    purchaseRequest.setSupplier("supplier");
    purchaseRequest.setProducts(productList);

    var expectedStock = productEntity.getStock() + product.getQuantity();
    var updatedProductEntity = new ProductEntity();
    updatedProductEntity.setId(productId);
    updatedProductEntity.setStock(expectedStock);

    Mockito.when(productRepository.findById(eq(productId))).thenReturn(Optional.of(productEntity));

    assertDoesNotThrow(() -> purchaseService.renewStock(purchaseRequest));

    Mockito.verify(productRepository).findById(eq(productId));
    Mockito.verify(productRepository).save(eq(updatedProductEntity));
  }
}