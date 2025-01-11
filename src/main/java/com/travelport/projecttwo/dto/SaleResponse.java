package com.travelport.projecttwo.dto;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.SaleDetail;

import java.util.ArrayList;
import java.util.List;

public class SaleResponse {
    private String id;
    private List<ProductDetail> products;

    public SaleResponse(String id, List<SaleDetail> saleDetails, List<Product> boughtProducts) {
        this.id = id;
        this.products = getProductDetailFromData(boughtProducts, saleDetails);
    }

    public static class ProductDetail {
        private Product product;
        private String quantity;

        public ProductDetail(Product product, String quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ProductDetail> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDetail> products) {
        this.products = products;
    }

    private List<ProductDetail> getProductDetailFromData(List<Product> products, List<SaleDetail> saleDetails) {
        List<ProductDetail> productDetails = new ArrayList<>();

        for (Product product : products) {
            String quantity = getQuantityForProduct(saleDetails, product);
            if (quantity != null) {
                productDetails.add(new ProductDetail(product, quantity));
            }
        }

        return productDetails;
    }

    private String getQuantityForProduct(List<SaleDetail> saleDetails, Product product) {
        for (SaleDetail saleDetail : saleDetails) {
            if (saleDetail.getProduct().getId().equals(product.getId())) {
                return String.valueOf(saleDetail.getQuantity());
            }
        }
        return null;
    }
}
