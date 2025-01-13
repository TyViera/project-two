package com.travelport.projecttwo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.ProductPurchase;
import com.travelport.projecttwo.entities.ProductStock;
import com.travelport.projecttwo.entities.Purchase;
import com.travelport.projecttwo.repositories.ProductRepository;
import com.travelport.projecttwo.repositories.ProductStockRepository;
import com.travelport.projecttwo.repositories.PurchaseRepository;
import com.travelport.projecttwo.services.PurchaseServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

  @InjectMocks
  private PurchaseServiceImpl purchaseService;

  @Mock
  private PurchaseRepository purchaseRepository;

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductStockRepository productStockRepository;

  private Product product;
  private ProductStock productStock;
  private ProductPurchase productPurchase;
  private UUID productId;
  private Purchase purchase;

  @BeforeEach
  void setUp() {
    productId = UUID.fromString("12345678-1234-1234-1234-123456789A01");

    product = new Product();
    product.setId(productId);
    product.setName("Throne of Glass Bundle");
    product.setCode("TOG11");

    productStock = new ProductStock();
    productStock.setProduct(product);
    productStock.setQuantity(100);

    productPurchase = new ProductPurchase();
    productPurchase.setProduct(product);
    productPurchase.setQuantity(50);

    purchase = new Purchase("Rhysand NightCourt", Collections.singletonList(productPurchase));
  }

  @Test
  void testPurchaseProduct() {
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(productStock));

    ArgumentCaptor<Purchase> purchaseCaptor = ArgumentCaptor.forClass(Purchase.class);
    when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);

    purchaseService.purchaseProduct("Rhysand NightCourt", Collections.singletonList(productPurchase));

    assertEquals(150, productStock.getQuantity());

    verify(productRepository, times(1)).findById(productId);
    verify(productStockRepository, times(1)).findByProductId(productId);
    verify(productStockRepository, times(1)).save(productStock);

    verify(purchaseRepository, times(1)).save(purchaseCaptor.capture());

    Purchase capturedPurchase = purchaseCaptor.getValue();

    assertEquals("Rhysand NightCourt", capturedPurchase.getSupplier());
    assertEquals(1, capturedPurchase.getProducts().size());
    assertEquals(product, capturedPurchase.getProducts().getFirst().getProduct());
    assertEquals(50, capturedPurchase.getProducts().getFirst().getQuantity());
  }

  @Test
  void testPurchaseProductProductNotFound() {
    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      purchaseService.purchaseProduct("Rhysand NightCourt", Collections.singletonList(productPurchase));
    });

    assertEquals("Product not found", exception.getMessage());
    verify(productRepository, times(1)).findById(productId);
    verify(productStockRepository, times(0)).findByProductId(any());
    verify(productStockRepository, times(0)).save(any());
    verify(purchaseRepository, times(0)).save(any());
  }
}
