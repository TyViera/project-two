package com.travelport.projecttwo.service.Impl;

import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void getAllProducts() {

        Product product1 = new Product();
        product1.setId("product1");
        Product product2 = new Product();
        product2.setId("product2");

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size(), "There should be 2 products");
        assertTrue(products.contains(product1), "Product1 should be in the list");
        assertTrue(products.contains(product2), "Product2 should be in the list");
    }

    @Test
    void getProductById() {

        String productId = "product1";
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(productId);

        assertTrue(result.isPresent(), "Product should be found");
        assertEquals(productId, result.get().getId(), "The product ID should match");
    }

    @Test
    void createProduct() {

        Product product = new Product();
        product.setCode("LAP12345");
        when(productRepository.existsByCode("LAP12345")).thenReturn(false);
        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct.getId(), "Product ID should be generated");
        assertEquals(0, createdProduct.getStock(), "New product stock should be 0");
        verify(productRepository).save(product);
    }

    @Test
    void createProduct_duplicateCode() {

        Product product = new Product();
        product.setCode("LAP12345");
        when(productRepository.existsByCode("LAP12345")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct(product));

        assertEquals("There is already a product with the provided code", exception.getMessage());
        verify(productRepository, never()).save(product);
    }

    @Test
    void updateProduct() {

        String productId = "product1";
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        when(productRepository.existsById(productId)).thenReturn(true);

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Updated Product");

        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(productId, updatedProduct);

        assertEquals(updatedProduct.getName(), result.getName(), "Product name should be updated");
        verify(productRepository).save(updatedProduct);
    }

    @Test
    void updateProduct_notFound() {

        String productId = "nonexistent";
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);

        when(productRepository.existsById(productId)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(productId, updatedProduct));

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, never()).save(updatedProduct);
    }

    @Test
    void deleteProduct() {

        String productId = "product1";
        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }

    @Test
    void deleteProduct_notFound() {
        String productId = "nonexistent";
        when(productRepository.existsById(productId)).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> productService.deleteProduct(productId));

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, never()).deleteById(productId);
    }
}