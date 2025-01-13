package com.travelport.projecttwo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema
public class SaleRequest {
    private ClientRequest client;
    private List<ProductRequest> products;

    public ClientRequest getClient() {
        return client;
    }

    public void setClient(ClientRequest client) {
        this.client = client;
    }

    public List<ProductRequest> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRequest> products) {
        this.products = products;
    }

    public static class ClientRequest {
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class ProductRequest {
        private String id;
        private Integer quantity;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
