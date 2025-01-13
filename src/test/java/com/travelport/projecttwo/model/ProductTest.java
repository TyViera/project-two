package com.travelport.projecttwo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void getId() {
        Product product = new Product();
        product.setId("uuid12345");
        assertEquals("uuid12345", product.getId());
    }

    @Test
    void setId() {
        Product product = new Product();
        product.setId("uuid67890");
        assertEquals("uuid67890", product.getId());
    }

    @Test
    void getName() {
        Product product = new Product();
        product.setName("Laptop");
        assertEquals("Laptop", product.getName());
    }

    @Test
    void setName() {
        Product product = new Product();
        product.setName("Smartphone");
        assertEquals("Smartphone", product.getName());
    }

    @Test
    void getCode() {
        Product product = new Product();
        product.setCode("LAP12345");
        assertEquals("LAP12345", product.getCode());
    }

    @Test
    void setCode() {
        Product product = new Product();
        product.setCode("PHN67890");
        assertEquals("PHN67890", product.getCode());
    }

    @Test
    void getStock() {
        Product product = new Product();
        product.setStock(50);
        assertEquals(50, product.getStock());
    }

    @Test
    void setStock() {
        Product product = new Product();
        product.setStock(100);
        assertEquals(100, product.getStock());
    }
}