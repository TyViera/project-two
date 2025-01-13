package com.travelport.projecttwo.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

    @Test
    void getId() {
        Sale sale = new Sale();
        assertNotNull(sale.getId(), "ID should not be null after initialization");
    }

    @Test
    void getProduct() {
        Sale sale = new Sale();
        Product product = new Product();
        product.setId("product123");
        sale.setProduct(product);
        assertEquals(product, sale.getProduct(), "The product should match the one set");
    }

    @Test
    void setProduct() {
        Sale sale = new Sale();
        Product product = new Product();
        product.setId("product456");
        sale.setProduct(product);
        assertEquals(product, sale.getProduct(), "The product should be updated correctly");
    }

    @Test
    void getClient() {
        Sale sale = new Sale();
        Client client = new Client();
        client.setId("client123");
        sale.setClient(client);
        assertEquals(client, sale.getClient(), "The client should match the one set");
    }

    @Test
    void setClient() {
        Sale sale = new Sale();
        Client client = new Client();
        client.setId("client456");
        sale.setClient(client);
        assertEquals(client, sale.getClient(), "The client should be updated correctly");
    }

    @Test
    void getQuantity() {
        Sale sale = new Sale();
        sale.setQuantity(10);
        assertEquals(10, sale.getQuantity(), "The quantity should match the value set");
    }

    @Test
    void setQuantity() {
        Sale sale = new Sale();
        sale.setQuantity(20);
        assertEquals(20, sale.getQuantity(), "The quantity should be updated correctly");
    }
}