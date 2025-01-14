package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.exceptions.ProductHasSalesException;
import com.travelport.projecttwo.services.mappings.ProductMappings;
import com.travelport.projecttwo.repository.IProductRepository;
import com.travelport.projecttwo.repository.ISalesDetRepository;
import com.travelport.projecttwo.repository.entities.ProductEntity;
import com.travelport.projecttwo.services.domainModels.ProductDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private ISalesDetRepository salesDetRepository;

    @Mock
    ProductMappings productMappings;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, salesDetRepository);
    }

    @Test
    void getProducts_shouldReturnAllProducts() {
        // GIVEN

        var product1 = new ProductEntity();
        product1.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        product1.setCode("PROD1");
        product1.setName("Laptop");
        product1.setStock(10);

        var product2 = new ProductEntity();
        product2.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384318");
        product2.setCode("PROD2");
        product2.setName("Mouse");
        product2.setStock(20);

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        // WHEN
        var result = productService.getProducts();

        // THEN
        assertEquals(2, result.size());
        assertEquals("2cba93e4-1eac-4ba7-b8cb-6febf3384319", result.get(0).getId());
        assertEquals("PROD1", result.get(0).getCode());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals(10, result.get(0).getStock());

        assertEquals("2cba93e4-1eac-4ba7-b8cb-6febf3384318", result.get(1).getId());
        assertEquals("PROD2", result.get(1).getCode());
        assertEquals("Mouse", result.get(1).getName());
        assertEquals(20, result.get(1).getStock());

        verify(productRepository).findAll();
    }

    @Test
    void getProductById_shouldReturnProductDomainIfExists() {
        // GIVEN
        var product = new ProductEntity();
        product.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        product.setCode("PROD1");
        product.setName("Laptop");
        product.setStock(10);

        when(productRepository.findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319")).thenReturn(java.util.Optional.of(product));

        // WHEN
        var result = productService.getProductById("2cba93e4-1eac-4ba7-b8cb-6febf3384319");

        // THEN
        assertTrue(result.isPresent());
        assertEquals("2cba93e4-1eac-4ba7-b8cb-6febf3384319", result.get().getId());
        assertEquals("PROD1", result.get().getCode());
        assertEquals("Laptop", result.get().getName());
        assertEquals(10, result.get().getStock());

        verify(productRepository).findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
    }

    @Test
    void createProduct_whenProductCodeAlreadyExists_shouldThrowIllegalArgument() {
        // GIVEN
        var product = new ProductDomain();
        product.setCode("PROD1");

        when(productRepository.existsByCode("PROD1")).thenReturn(true);

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> productService.createProduct(product));
        verify(productRepository).existsByCode("PROD1");
    }

    @Test
    void createProduct_whenProductCodeIsNew_shouldCreate() {
        // GIVEN
        var product = new ProductDomain();
        product.setCode("PROD1");
        product.setName("Laptop");

        var productEntity = new ProductEntity();
        productEntity.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        productEntity.setCode("PROD1");
        productEntity.setName("Laptop");
        productEntity.setStock(0);

        when(productRepository.existsByCode("PROD1")).thenReturn(false);
        when(productRepository.save(any())).thenReturn(productEntity);

        // WHEN
        var result = productService.createProduct(product);

        // THEN
        assertEquals("2cba93e4-1eac-4ba7-b8cb-6febf3384319", result.getId());
        assertEquals("PROD1", result.getCode());
        assertEquals("Laptop", result.getName());
        assertEquals(0, result.getStock());

        verify(productRepository).existsByCode("PROD1");
        verify(productRepository).save(any());
    }

    @Test
    void updateProduct_whenProductExists_shouldReturnOk() {
        // GIVEN
        var product = new ProductDomain();
        product.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        product.setCode("PROD1");
        product.setName("Laptop");
        product.setStock(10);

        var productEntity = new ProductEntity();
        productEntity.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        productEntity.setCode("PROD1");
        productEntity.setName("Laptop");
        productEntity.setStock(10);

        when(productRepository.findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319")).thenReturn(java.util.Optional.of(productEntity));
        when(productRepository.save(any())).thenReturn(productEntity);

        // WHEN
        var result = productService.updateProduct("2cba93e4-1eac-4ba7-b8cb-6febf3384319", product);

        // THEN
        assertEquals("2cba93e4-1eac-4ba7-b8cb-6febf3384319", result.getId());
        assertEquals("PROD1", result.getCode());
        assertEquals("Laptop", result.getName());
        assertEquals(10, result.getStock());

        verify(productRepository).findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        verify(productRepository).save(any());
    }

    @Test
    void updateProduct_whenProductDoesNotExist_shouldThrowIllegalArgument() {
        // GIVEN
        var product = new ProductDomain();
        product.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
        product.setCode("PROD1");
        product.setName("Laptop");
        product.setStock(10);

        when(productRepository.findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319")).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct("2cba93e4-1eac-4ba7-b8cb-6febf3384319", product));
        verify(productRepository).findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
    }

    @Test
    void deleteProduct_whenProductDoesNotExist_shouldThrowIllegalArgument() {
        // GIVEN
        when(productRepository.findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319")).thenReturn(java.util.Optional.empty());

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct("2cba93e4-1eac-4ba7-b8cb-6febf3384319"));
        verify(productRepository).findById("2cba93e4-1eac-4ba7-b8cb-6febf3384319");
    }

    @Test
    void deleteProduct_whenProductHasSales_shouldThrowProductHasSalesException() {
        // GIVEN
        var productEntity = new ProductEntity();
        productEntity.setId("2cba93e4-1eac-4ba7-b8cb-6febf3384319");

        when(productRepository.findById(productEntity.getId())).thenReturn(java.util.Optional.of(productEntity));
        when(salesDetRepository.existsByIdProductId(productEntity.getId())).thenReturn(true);

        // WHEN & THEN
        assertThrows(ProductHasSalesException.class, () -> productService.deleteProduct(productEntity.getId()));
        verify(productRepository).findById(productEntity.getId());
        verify(salesDetRepository).existsByIdProductId(productEntity.getId());
    }
}