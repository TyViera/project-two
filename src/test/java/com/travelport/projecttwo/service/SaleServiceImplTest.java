package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entities.SaleEntity;
import com.travelport.projecttwo.entities.SaleProductEntity;
import com.travelport.projecttwo.model.PurchaseProduct;
import com.travelport.projecttwo.model.Sale;
import com.travelport.projecttwo.repository.SaleDao;
import com.travelport.projecttwo.services.impl.SaleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {

    @InjectMocks
    private SaleServiceImpl saleService;

    @Mock
    private SaleDao saleDao;

    @Test
    void addSaleTest() {
        PurchaseProduct product1 = new PurchaseProduct("prod1", 3);
        PurchaseProduct product2 = new PurchaseProduct("prod2", 7);
        Sale sale = new Sale("sale1", "client1", List.of(product1, product2));

        SaleEntity saleEntity = new SaleEntity();
        saleEntity.setId(sale.getId());
        saleEntity.setClientId(sale.getClientId());

        // Mocking DAO interactions
        Mockito.doNothing().when(saleDao).addSale(any(SaleEntity.class));
        Mockito.doNothing().when(saleDao).addSaleProduct(any(SaleProductEntity.class));

        // Act
        Sale result = saleService.addSale(sale);

        // Assert
        assertEquals(sale, result);

        Mockito.verify(saleDao, times(1)).addSale(any(SaleEntity.class));
        Mockito.verify(saleDao, times(2)).addSaleProduct(any(SaleProductEntity.class));
    }
}
