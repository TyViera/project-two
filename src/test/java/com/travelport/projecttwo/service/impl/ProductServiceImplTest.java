package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        product1 = new Product(UUID.randomUUID().toString(), "Product A", "A123");
        product2 = new Product(UUID.randomUUID().toString(), "Product B", "B123");
    }

    @Test
    void getProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        var result = productService.getProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Product A", result.get(0).getName());
        assertEquals("Product B", result.get(1).getName());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById() {
        when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));

        var result = productService.getProductById(product1.getId());

        assertNotNull(result);
        assertEquals("Product A", result.getName());
        verify(productRepository, times(1)).findById(product1.getId());
    }

    @Test
    void getProductById_NotFound() {
        String nonExistentId = UUID.randomUUID().toString();
        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(nonExistentId));

        verify(productRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void deleteProduct() {
        when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));

        productService.deleteProduct(product1.getId());

        verify(productRepository, times(1)).findById(product1.getId());
        verify(productRepository, times(1)).deleteById(product1.getId());
    }

    @Test
    void deleteProduct_NotFound() {
        String nonExistentId = UUID.randomUUID().toString();
        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.deleteProduct(nonExistentId));

        verify(productRepository, times(1)).findById(nonExistentId);
        verify(productRepository, never()).deleteById(nonExistentId);
    }

    @Test
    void deleteProduct_ThrowsException() {
        when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
        doThrow(new IllegalArgumentException("Product exists on sale")).when(productRepository).deleteById(product1.getId());

        assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(product1.getId()));

        verify(productRepository, times(1)).findById(product1.getId());
        verify(productRepository, times(1)).deleteById(product1.getId());
    }

    @Test
    void addProduct() {
        Product newProduct = new Product(null, "Product C", "C123");
        when(productRepository.save(any(Product.class))).thenReturn(product2);

        var result = productService.addProduct(newProduct);

        assertNotNull(result);
        assertEquals("Product B", result.getName());
        assertNotNull(result.getId());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct() {
        Product product1 = new Product("1", "Original Product", "O123");
        Product product2 = new Product("1", "Updated Product", "U123");

        when(productRepository.findById(product1.getId())).thenReturn(Optional.of(product1));
        when(productRepository.save(any(Product.class))).thenReturn(product2);

        Product updatedProductData = new Product(product1.getId(), "Updated Product", "U123");

        Product result = productService.updateProduct(product1.getId(), updatedProductData);

        assertNotNull(result, "El producto actualizado no debería ser nulo");
        assertEquals("Updated Product", result.getName(), "El nombre del producto debería haberse actualizado");
        assertEquals("U123", result.getCode(), "El código del producto debería haberse actualizado");
        verify(productRepository, times(1)).findById(product1.getId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_NotFound() {
        String nonExistentId = UUID.randomUUID().toString();
        Product updatedProductData = new Product(null, "Updated Product", "U123");

        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.updateProduct(nonExistentId, updatedProductData));

        verify(productRepository, times(1)).findById(nonExistentId);
        verify(productRepository, never()).save(any(Product.class));
    }
}
