package com.travelport.projecttwo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTest {

    @Test
    void getId() {
        Purchase purchase = new Purchase();
        purchase.setId(1L);
        assertEquals(1L, purchase.getId());
    }

    @Test
    void setId() {
        Purchase purchase = new Purchase();
        purchase.setId(2L);
        assertEquals(2L, purchase.getId());
    }

    @Test
    void getProduct() {
        Purchase purchase = new Purchase();
        Product product = new Product();
        product.setId("product123");
        purchase.setProduct(product);
        assertEquals(product, purchase.getProduct());
    }

    @Test
    void setProduct() {
        Purchase purchase = new Purchase();
        Product product = new Product();
        product.setId("product456");
        purchase.setProduct(product);
        assertEquals(product, purchase.getProduct());
    }

    @Test
    void getSupplier() {
        Purchase purchase = new Purchase();
        purchase.setSupplier("Best Supplier Inc.");
        assertEquals("Best Supplier Inc.", purchase.getSupplier());
    }

    @Test
    void setSupplier() {
        Purchase purchase = new Purchase();
        purchase.setSupplier("Another Supplier");
        assertEquals("Another Supplier", purchase.getSupplier());
    }

    @Test
    void getQuantity() {
        Purchase purchase = new Purchase();
        purchase.setQuantity(100);
        assertEquals(100, purchase.getQuantity());
    }

    @Test
    void setQuantity() {
        Purchase purchase = new Purchase();
        purchase.setQuantity(200);
        assertEquals(200, purchase.getQuantity());
    }
}