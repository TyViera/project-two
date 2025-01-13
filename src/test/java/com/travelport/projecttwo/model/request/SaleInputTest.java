package com.travelport.projecttwo.model.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaleInputTest {

    @Test
    void getProduct() {

        SaleInput saleInput = new SaleInput();
        SaleInput.ProductRef productRef = new SaleInput.ProductRef();
        productRef.setId("product123");
        saleInput.setProduct(productRef);

        SaleInput.ProductRef result = saleInput.getProduct();

        assertNotNull(result, "Product should not be null");
        assertEquals("product123", result.getId(), "Product ID should match");
    }

    @Test
    void setProduct() {

        SaleInput saleInput = new SaleInput();
        SaleInput.ProductRef productRef = new SaleInput.ProductRef();
        productRef.setId("product123");

        saleInput.setProduct(productRef);

        assertEquals("product123", saleInput.getProduct().getId(), "Product ID should match the one set");
    }

    @Test
    void getClient() {

        SaleInput saleInput = new SaleInput();
        SaleInput.ClientRef clientRef = new SaleInput.ClientRef();
        clientRef.setId("client123");
        saleInput.setClient(clientRef);

        SaleInput.ClientRef result = saleInput.getClient();

        assertNotNull(result, "Client should not be null");
        assertEquals("client123", result.getId(), "Client ID should match");
    }

    @Test
    void setClient() {

        SaleInput saleInput = new SaleInput();
        SaleInput.ClientRef clientRef = new SaleInput.ClientRef();
        clientRef.setId("client123");

        saleInput.setClient(clientRef);

        assertEquals("client123", saleInput.getClient().getId(), "Client ID should match the one set");
    }

    @Test
    void getQuantity() {
        SaleInput saleInput = new SaleInput();
        saleInput.setQuantity(5);

        int result = saleInput.getQuantity();

        assertEquals(5, result, "Quantity should match the one set");
    }

    @Test
    void setQuantity() {
        SaleInput saleInput = new SaleInput();

        saleInput.setQuantity(10);

        assertEquals(10, saleInput.getQuantity(), "Quantity should match the one set");
    }
}