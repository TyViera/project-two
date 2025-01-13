package com.travelport.projecttwo.model.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseInputTest {

    @Test
    void getProduct() {

        PurchaseInput purchaseInput = new PurchaseInput();
        PurchaseInput.ProductRef productRef = new PurchaseInput.ProductRef();
        productRef.setId("product123");
        purchaseInput.setProduct(productRef);

        PurchaseInput.ProductRef result = purchaseInput.getProduct();

        assertNotNull(result, "Product should not be null");
        assertEquals("product123", result.getId(), "Product ID should match");
    }

    @Test
    void setProduct() {

        PurchaseInput purchaseInput = new PurchaseInput();
        PurchaseInput.ProductRef productRef = new PurchaseInput.ProductRef();
        productRef.setId("product123");

        purchaseInput.setProduct(productRef);

        assertEquals("product123", purchaseInput.getProduct().getId(), "Product ID should match the one set");
    }

    @Test
    void getSupplier() {

        PurchaseInput purchaseInput = new PurchaseInput();
        purchaseInput.setSupplier("Best Supplier Inc.");

        String result = purchaseInput.getSupplier();

        assertNotNull(result, "Supplier should not be null");
        assertEquals("Best Supplier Inc.", result, "Supplier name should match");
    }

    @Test
    void setSupplier() {

        PurchaseInput purchaseInput = new PurchaseInput();

        purchaseInput.setSupplier("Best Supplier Inc.");

        assertEquals("Best Supplier Inc.", purchaseInput.getSupplier(), "Supplier name should match the one set");
    }

    @Test
    void getQuantity() {

        PurchaseInput purchaseInput = new PurchaseInput();
        purchaseInput.setQuantity(50);

        int result = purchaseInput.getQuantity();

        assertEquals(50, result, "Quantity should match the one set");
    }

    @Test
    void setQuantity() {

        PurchaseInput purchaseInput = new PurchaseInput();

        purchaseInput.setQuantity(50);

        assertEquals(50, purchaseInput.getQuantity(), "Quantity should match the one set");
    }
}