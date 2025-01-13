package com.travelport.projecttwo.persistence;

import com.travelport.projecttwo.entities.PurchaseEntity;
import com.travelport.projecttwo.entities.PurchaseProductEntity;
import com.travelport.projecttwo.entities.PurchaseProductId;
import com.travelport.projecttwo.repository.impl.PurchaseDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@JdbcTest
@ExtendWith(MockitoExtension.class)
class PurchaseDaoImplTest {

    @InjectMocks
    private PurchaseDaoImpl purchaseDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void addPurchaseTest() {
        String insertPurchase = "INSERT INTO purchases (id, supplier) VALUES (?, ?)";
        PurchaseEntity mockPurchase = new PurchaseEntity("1", "Supplier A");

        Mockito.when(jdbcTemplate.update(eq(insertPurchase), eq("1"), eq("Supplier A"))).thenReturn(1);

        assertDoesNotThrow(() -> purchaseDao.addPurchase(mockPurchase));

        Mockito.verify(jdbcTemplate).update(eq(insertPurchase), eq("1"), eq("Supplier A"));
    }

    @Test
    void addPurchaseProductTest() {
        String insertPurchaseProduct = "INSERT INTO purchases_products (purchase_id, product_id, quantity) VALUES (?, ?, ?)";
        String updateProductStock = "UPDATE products SET stock = ? WHERE id = ?";
        PurchaseProductId mockPurchaseProductId = new PurchaseProductId("1", "101");
        PurchaseProductEntity mockPurchaseProduct = new PurchaseProductEntity(mockPurchaseProductId, 50);

        Mockito.when(jdbcTemplate.update(eq(insertPurchaseProduct), eq("1"), eq("101"), eq(50))).thenReturn(1);
        Mockito.when(jdbcTemplate.update(eq(updateProductStock), eq("101"), eq(50))).thenReturn(1);

        assertDoesNotThrow(() -> purchaseDao.addPurchaseProduct(mockPurchaseProduct));

        Mockito.verify(jdbcTemplate).update(eq(insertPurchaseProduct), eq("1"), eq("101"), eq(50));
        Mockito.verify(jdbcTemplate).update(eq(updateProductStock), eq("101"), eq(50));
    }
}
