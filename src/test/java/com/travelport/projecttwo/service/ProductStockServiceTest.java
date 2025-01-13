package com.travelport.projecttwo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.travelport.projecttwo.entities.ProductStock;
import com.travelport.projecttwo.repositories.ProductStockRepository;

@ExtendWith(MockitoExtension.class)
public class ProductStockServiceTest {

  @InjectMocks
  private ProductStockServiceImpl productStockService;

  @Mock
  private ProductStockRepository productStockRepository;

  private ProductStock productStock;
  private UUID productId;

  @BeforeEach
  void setUp() {
    productId = UUID.fromString("12345678-1234-1234-1234-123456789A01");

    productStock = new ProductStock();
    productStock.setQuantity(100);
  }

  @Test
  void testCreateProductStock() {
    when(productStockRepository.save(productStock)).thenReturn(productStock);
    ProductStock createdProductStock = productStockService.create(productStock);
    assertEquals(productStock, createdProductStock);
    verify(productStockRepository, times(1)).save(productStock);
  }

  @Test
  void testGetStockByProductIdFound() {
    when(productStockRepository.findByProductId(productId)).thenReturn(Optional.of(productStock));
    ProductStock foundProductStock = productStockService.getStockByProductId(productId);
    assertEquals(100, foundProductStock.getQuantity());
    verify(productStockRepository, times(1)).findByProductId(productId);
  }

  @Test
  void testGetStockByProductIdNotFound() {
    UUID invalidProductId = UUID.randomUUID();
    when(productStockRepository.findByProductId(invalidProductId)).thenReturn(Optional.empty());
    ProductStock foundProductStock = productStockService.getStockByProductId(invalidProductId);
    assertNull(foundProductStock);
    verify(productStockRepository, times(1)).findByProductId(invalidProductId);
  }
}
