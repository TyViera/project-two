package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entities.ProductEntity;
import com.travelport.projecttwo.repository.ProductDao;
import com.travelport.projecttwo.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductDao productDao;

    @Test
    void getProductsTest() {
        List<ProductEntity> mockProducts = List.of(
                new ProductEntity("1", "Laptop", "LPT123", 10),
                new ProductEntity("2", "Smartphone", "SMP456", 15)
        );

        Mockito.when(productDao.getProducts()).thenReturn(mockProducts);

        List<ProductEntity> result = productService.getProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals("Smartphone", result.get(1).getName());

        Mockito.verify(productDao).getProducts();
    }

    @Test
    void getProductByIdTest() {
        String productId = "1";
        ProductEntity mockProduct = new ProductEntity("1", "Laptop", "LPT123", 10);

        Mockito.when(productDao.getProduct(productId)).thenReturn(Optional.of(mockProduct));

        Optional<ProductEntity> result = productService.getProductById(productId);

        assertTrue(result.isPresent());
        assertEquals("Laptop", result.get().getName());

        Mockito.verify(productDao).getProduct(productId);
    }

    @Test
    void createProductTest() {
        ProductEntity newProduct = new ProductEntity("3", "Tablet", "TAB789", 20);

        productService.createProduct(newProduct);

        Mockito.verify(productDao).createProduct(newProduct);
    }

    @Test
    void updateProductTest() {
        String productId = "1";
        ProductEntity updatedProduct = new ProductEntity("1", "Laptop Pro", "LPT123", 5);

        Mockito.when(productDao.updateProduct(productId, updatedProduct)).thenReturn(Optional.of(updatedProduct));

        Optional<ProductEntity> result = productService.updateProduct(productId, updatedProduct);

        assertTrue(result.isPresent());
        assertEquals("Laptop Pro", result.get().getName());

        Mockito.verify(productDao).updateProduct(productId, updatedProduct);
    }

    @Test
    void deleteProductSuccessTest() {
        String productId = "1";

        Mockito.when(productDao.getAssociatedSalesCount(productId)).thenReturn(0);
        Mockito.when(productDao.deleteProduct(productId)).thenReturn(1);

        assertDoesNotThrow(() -> productService.deleteProduct(productId));

        Mockito.verify(productDao).getAssociatedSalesCount(productId);
        Mockito.verify(productDao).deleteProduct(productId);
    }

    @Test
    void deleteProductWithSalesThrowsException() {
        String productId = "1";

        Mockito.when(productDao.getAssociatedSalesCount(productId)).thenReturn(5);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> productService.deleteProduct(productId));
        assertEquals("Product cannot be deleted as it has been sold.", exception.getMessage());

        Mockito.verify(productDao).getAssociatedSalesCount(productId);
        Mockito.verify(productDao, Mockito.never()).deleteProduct(productId);
    }

    @Test
    void deleteNonExistingProductThrowsException() {
        String productId = "1";

        Mockito.when(productDao.getAssociatedSalesCount(productId)).thenReturn(0);
        Mockito.when(productDao.deleteProduct(productId)).thenReturn(0);

        NullPointerException exception = assertThrows(NullPointerException.class, () -> productService.deleteProduct(productId));
        assertEquals("Product not found.", exception.getMessage());

        Mockito.verify(productDao).getAssociatedSalesCount(productId);
        Mockito.verify(productDao).deleteProduct(productId);
    }
}
