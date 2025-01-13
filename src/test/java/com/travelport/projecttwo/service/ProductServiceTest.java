package com.travelport.projecttwo.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.repositories.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @InjectMocks
  private ProductServiceImpl productService;

  @Mock
  private ProductRepository productRepository;

  private Product product1;
  private Product product2;
  private UUID product1Id;
  private UUID product2Id;

  @BeforeEach
  void setUp() {
    product1Id = UUID.fromString("12345678-1234-1234-1234-123456789A01");
    product2Id = UUID.fromString("98765432-9876-9876-9876-987654321B01");

    product1 = new Product();
    product1.setId(product1Id);
    product1.setName("Throne of Glass Bundle");
    product1.setCode("TOG11");

    product2 = new Product();
    product2.setId(product2Id);
    product2.setName("A Court of Thorns and Roses");
    product2.setCode("ACOTAR9");
  }

  @Test
  void testCreateProduct() {
    when(productRepository.save(product1)).thenReturn(product1);
    Product createdProduct = productService.create(product1);
    assertEquals(product1, createdProduct);
    verify(productRepository, times(1)).save(product1);
  }

  @Test
  void testGetAllProducts() {
    when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
    List<Product> products = productService.getAllProducts();
    assertEquals(2, products.size());
    assertEquals("Throne of Glass Bundle", products.get(0).getName());
    assertEquals("A Court of Thorns and Roses", products.get(1).getName());
    verify(productRepository, times(1)).findAll();
  }

  @Test
  void testGetProductById() {
    when(productRepository.findById(product1Id)).thenReturn(Optional.of(product1));
    Product foundProduct = productService.read(product1Id);
    assertEquals("Throne of Glass Bundle", foundProduct.getName());
    assertEquals("TOG11", foundProduct.getCode());
    verify(productRepository, times(1)).findById(product1Id);
  }

  @Test
  void testGetProductByIdNotFound() {
    UUID invalidId = UUID.randomUUID();
    when(productRepository.findById(invalidId)).thenReturn(Optional.empty());
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      productService.read(invalidId);
    });
    assertEquals("Product not found", exception.getMessage());
    verify(productRepository, times(1)).findById(invalidId);
  }

  @Test
  void testUpdateProduct() {
    Product updatedProduct = new Product();
    updatedProduct.setName("A Court of Mist and Fury Special Edition");
    updatedProduct.setCode("ACOMAF");

    when(productRepository.findById(product1Id)).thenReturn(Optional.of(product1));
    when(productRepository.save(product1)).thenReturn(product1);

    Product updated = productService.update(product1Id, updatedProduct);
    assertEquals("A Court of Mist and Fury Special Edition", updated.getName());
    assertEquals("ACOMAF", updated.getCode());
    verify(productRepository, times(1)).findById(product1Id);
    verify(productRepository, times(1)).save(product1);
  }

  @Test
  void testUpdateProductNotFound() {
    Product updatedProduct = new Product();
    updatedProduct.setName("A Court of Mist and Fury Special Edition");
    updatedProduct.setCode("ACOMAF");

    UUID invalidId = UUID.randomUUID();
    when(productRepository.findById(invalidId)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      productService.update(invalidId, updatedProduct);
    });
    assertEquals("Product not found", exception.getMessage());
    verify(productRepository, times(1)).findById(invalidId);
  }

  @Test
  void testDeleteProduct() {
    doNothing().when(productRepository).deleteById(product1Id);
    assertDoesNotThrow(() -> productService.delete(product1Id));
    verify(productRepository, times(1)).deleteById(product1Id);
  }
}
