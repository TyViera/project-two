package com.travelport.projecttwo.persistence;

import com.travelport.projecttwo.entities.ProductEntity;
import com.travelport.projecttwo.repository.impl.ProductDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@JdbcTest
@ExtendWith(MockitoExtension.class)
class ProductDaoImplTest {

    @InjectMocks
    private ProductDaoImpl productDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void getProductsTest() {
        String query = "SELECT * FROM products";
        List<ProductEntity> mockProducts = List.of(
                new ProductEntity("1", "Product A", "P001", 100),
                new ProductEntity("2", "Product B", "P002", 200)
        );

        Mockito.when(jdbcTemplate.query(eq(query), any(RowMapper.class))).thenReturn(mockProducts);

        List<ProductEntity> result = productDao.getProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Product A", result.get(0).getName());
        assertEquals("Product B", result.get(1).getName());

        Mockito.verify(jdbcTemplate).query(eq(query), any(RowMapper.class));
    }

    @Test
    void getProductTest() {
        String query = "SELECT * FROM products WHERE id = ?";
        ProductEntity mockProduct = new ProductEntity("1", "Product A", "P001", 100);

        Mockito.when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), eq("1")))
                .thenReturn(Optional.of(mockProduct));

        Optional<ProductEntity> result = productDao.getProduct("1");

        assertTrue(result.isPresent());
        assertEquals("Product A", result.get().getName());

        Mockito.verify(jdbcTemplate).queryForObject(eq(query), any(RowMapper.class), eq("1"));
    }

    @Test
    void getProductNotFoundTest() {
        String query = "SELECT * FROM products WHERE id = ?";
        Mockito.when(jdbcTemplate.queryForObject(eq(query), any(RowMapper.class), eq("999")))
                .thenThrow(new EmptyResultDataAccessException(1));

        Optional<ProductEntity> result = productDao.getProduct("999");

        assertTrue(result.isEmpty());
        Mockito.verify(jdbcTemplate).queryForObject(eq(query), any(RowMapper.class), eq("999"));
    }

    @Test
    void createProductTest() {
        String insert = "INSERT INTO products (id, name, code, stock) VALUES (?, ?, ?, ?)";
        ProductEntity newProduct = new ProductEntity("3", "Product C", "P003", 300);

        Mockito.when(jdbcTemplate.update(eq(insert), eq("3"), eq("Product C"), eq("P003"), eq(300))).thenReturn(1);

        productDao.createProduct(newProduct);

        Mockito.verify(jdbcTemplate).update(eq(insert), eq("3"), eq("Product C"), eq("P003"), eq(300));
    }

    @Test
    void updateProductTest() {
        String update = "UPDATE products SET name = ?, code = ? WHERE id = ?";
        ProductEntity updatedProduct = new ProductEntity("1", "Product A Updated", "P001U", 100);

        Mockito.when(jdbcTemplate.update(eq(update), eq("Product A Updated"), eq("P001U"), eq("1"))).thenReturn(1);

        Optional<ProductEntity> result = productDao.updateProduct("1", updatedProduct);

        assertTrue(result.isPresent());
        assertEquals("Product A Updated", result.get().getName());

        Mockito.verify(jdbcTemplate).update(eq(update), eq("Product A Updated"), eq("P001U"), eq("1"));
    }

    @Test
    void deleteProductTest() {
        String deleteSql = "DELETE FROM products WHERE id = ?";
        Mockito.when(jdbcTemplate.update(eq(deleteSql), eq("1"))).thenReturn(1);

        int result = productDao.deleteProduct("1");

        assertEquals(1, result);
        Mockito.verify(jdbcTemplate).update(eq(deleteSql), eq("1"));
    }

    @Test
    void getAssociatedSalesCountTest() {
        String query = "SELECT COUNT(*) FROM sales_products WHERE product_id = ?";
        Mockito.when(jdbcTemplate.queryForObject(eq(query), eq(Integer.class), eq("1"))).thenReturn(10);

        int result = productDao.getAssociatedSalesCount("1");

        assertEquals(10, result);
        Mockito.verify(jdbcTemplate).queryForObject(eq(query), eq(Integer.class), eq("1"));
    }
}

