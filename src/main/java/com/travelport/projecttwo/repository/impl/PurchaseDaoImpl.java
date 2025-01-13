package com.travelport.projecttwo.repository.impl;

import com.travelport.projecttwo.entities.PurchaseEntity;
import com.travelport.projecttwo.entities.PurchaseProductEntity;
import com.travelport.projecttwo.repository.PurchaseDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseDaoImpl implements PurchaseDao {

    private final JdbcTemplate jdbcTemplate;

    public PurchaseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addPurchase(PurchaseEntity purchase) {
        String insertPurchase = "INSERT INTO purchases (id, supplier) VALUES (?, ?)";
        jdbcTemplate.update(insertPurchase, purchase.getId(), purchase.getSupplier());
    }

    @Override
    public void addPurchaseProduct(PurchaseProductEntity purchaseProduct) {
        String insertPurchaseProduct = "INSERT INTO purchases_products (purchase_id, product_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(insertPurchaseProduct,
                purchaseProduct.getPurchaseProductId().getPurchaseId(),
                purchaseProduct.getPurchaseProductId().getProductId(),
                purchaseProduct.getQuantity());

        String updateProductStock = "UPDATE products SET stock = stock + ? WHERE id = ?";
        jdbcTemplate.update(updateProductStock,
                purchaseProduct.getPurchaseProductId().getProductId(),
                purchaseProduct.getQuantity());
    }
}
