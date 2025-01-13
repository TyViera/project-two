package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entity.Product;
import com.travelport.projecttwo.exception.ResourceNotFoundException;
import com.travelport.projecttwo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProductsTest() {
    }

    @Test
    void getProductByIdTest() {
        String productId = "1";
        Product mockProduct = new Product();
        mockProduct.setId(productId);
        mockProduct.setName("Product1");
        mockProduct.setCode("P001");

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Optional<Product> product = productService.getProductById(productId);

        assertTrue(product.isPresent());
        assertEquals("Product1", product.get().getName());
    }

    @Test
    void createProductTest() {
        Product product = new Product();
        product.setName("Product1");
        product.setCode("P001");

        when(productRepository.save(product)).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals("Product1", createdProduct.getName());
    }

    @Test
    void updateProductTest() {
        String productId = "1";
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Product1");
        existingProduct.setCode("P001");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Product");

        Product result = productService.updateProduct(productId, updatedProduct);

        assertEquals("Updated Product", result.getName());
    }

    @Test
    void deleteProductTest() {
        String productId = "1";
        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void deleteProductNotFoundTest() {
        String productId = "1";
        when(productRepository.existsById(productId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(productId));
    }
}
