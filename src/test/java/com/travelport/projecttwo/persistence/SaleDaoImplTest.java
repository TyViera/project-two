package com.travelport.projecttwo.persistence;

import com.travelport.projecttwo.entities.SaleEntity;
import com.travelport.projecttwo.entities.SaleProductEntity;
import com.travelport.projecttwo.entities.SaleProductId;
import com.travelport.projecttwo.repository.impl.SaleDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SaleDaoImplTest {

    @InjectMocks
    private SaleDaoImpl saleDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Test
    void addSaleTest() {
        SaleEntity sale = new SaleEntity("1", "123");

        String insertSale = "INSERT INTO sales(id, client_id) VALUES(?, ?);";

        saleDao.addSale(sale);

        Mockito.verify(jdbcTemplate).update(eq(insertSale), eq(sale.getId()), eq(sale.getClientId()));
    }

    @Test
    void addSaleProductTest() {
        SaleProductId saleProductId = new SaleProductId("1", "P123");
        SaleProductEntity saleProduct = new SaleProductEntity(saleProductId, 5);

        String insertSaleProduct = "INSERT INTO sales_products (sale_id, product_id, quantity) VALUES (?, ?, ?)";
        String updateProductStock = "UPDATE products SET stock = stock + ? WHERE id = ?";

        saleDao.addSaleProduct(saleProduct);

        Mockito.verify(jdbcTemplate).update(eq(insertSaleProduct), eq(saleProductId.getSaleId()), eq(saleProductId.getProductId()), eq(saleProduct.getQuantity()));
        Mockito.verify(jdbcTemplate).update(eq(updateProductStock), eq(saleProduct.getQuantity()), eq(saleProductId.getProductId()));
    }
}
