package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.jpa.ProductRepository;
import com.travelport.projecttwo.service.impl.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("ProductName");
        product.setCode("Prod12345");
    }

    @Test
    public void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(product);

        assertNotNull(createdProduct);
        assertEquals(product.getName(), createdProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testGetProductById() {
        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(product.getId());

        assertNotNull(foundProduct);
        assertEquals(product.getName(), foundProduct.getName());
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    public void testUpdateProduct() {
        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        product.setName("UpdatedName");
        Product updatedProduct = productService.updateProduct(product.getId(), product);

        assertNotNull(updatedProduct);
        assertEquals("UpdatedName", updatedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testDeleteProduct() {
        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);

        productService.deleteProduct(product.getId());

        verify(productRepository, times(1)).delete(product);
    }
}
