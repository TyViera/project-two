package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entities.PurchaseEntity;
import com.travelport.projecttwo.entities.PurchaseProductEntity;
import com.travelport.projecttwo.model.Purchase;
import com.travelport.projecttwo.model.PurchaseProduct;
import com.travelport.projecttwo.repository.PurchaseDao;
import com.travelport.projecttwo.services.impl.PurchaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PurchaseServiceImplTest {

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    @Mock
    private PurchaseDao purchaseDao;

    @Test
    void addPurchaseTest() {
        PurchaseProduct product1 = new PurchaseProduct("prod1", 10);
        PurchaseProduct product2 = new PurchaseProduct("prod2", 5);
        Purchase purchase = new Purchase("purchase1", "Supplier A", List.of(product1, product2));

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(purchase.getId());
        purchaseEntity.setSupplier(purchase.getSupplierName());

        // Mocking addPurchase call
        Mockito.doNothing().when(purchaseDao).addPurchase(any(PurchaseEntity.class));

        // Mocking addPurchaseProduct calls
        Mockito.doNothing().when(purchaseDao).addPurchaseProduct(any(PurchaseProductEntity.class));

        // Act
        Purchase result = purchaseService.addPurchase(purchase);

        // Assert
        assertEquals(purchase, result);

        Mockito.verify(purchaseDao, times(1)).addPurchase(any(PurchaseEntity.class));
        Mockito.verify(purchaseDao, times(2)).addPurchaseProduct(any(PurchaseProductEntity.class));
    }
}

