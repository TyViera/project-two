package com.travelport.projecttwo.repository.impl;

import com.travelport.projecttwo.entities.SaleEntity;
import com.travelport.projecttwo.entities.SaleProductEntity;
import com.travelport.projecttwo.repository.SaleDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SaleDaoImpl implements SaleDao {

    private final JdbcTemplate jdbcTemplate;

    public SaleDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addSale(SaleEntity sale) {
        String insertSale = "INSERT INTO sales(id, client_id) VALUES(?, ?);";
        jdbcTemplate.update(insertSale, sale.getId(), sale.getClientId());
    }

    @Override
    public void addSaleProduct(SaleProductEntity saleProduct) {
        String insertPurchaseProduct = "INSERT INTO sales_products (sale_id, product_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertPurchaseProduct,
                saleProduct.getId().getSaleId(),
                saleProduct.getId().getProductId(),
                saleProduct.getQuantity());

        String updateProductStock = "UPDATE products SET stock = stock + ? WHERE id = ?";
        jdbcTemplate.update(updateProductStock,
                saleProduct.getId().getProductId(),
                saleProduct.getQuantity());
    }
}
