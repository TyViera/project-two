package com.travelport.projecttwo.service.impl;

import com.travelport.projecttwo.jpa.ProductJpaRepository;
import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository productDao;
    public ProductServiceImpl(ProductJpaRepository productDao) { this.productDao = productDao; }

    @Override
    public List<Product> getAllProducts() { return productDao.findAll(); }

    @Override
    public Optional<Product> getProductById(UUID id) {
        var product = productDao.findById(id);
        if (product.isEmpty()) {
            return Optional.empty();
        }
        return product;
    }

    @Override
    public Product createProduct(Product product) {
        Product savedProduct = productDao.save(product);
        return savedProduct;
    }

    @Override
    public Optional<Product> updateProduct(UUID id, Product product) {
        var Product = productDao.findById(id);
        if (Product.isEmpty()) {
            return Optional.empty();
        }
        var clientUpdate = Product.get();
        if (product.getName() == null || product.getCode() == null) {
            return Optional.empty();
        } else {
            clientUpdate.setName(product.getName());
            clientUpdate.setCode(product.getCode());
            clientUpdate.setStock(product.getStock());
        }
        var clientUpdated = productDao.save(clientUpdate);
        return Optional.of(clientUpdated);
    }

    @Override
    public void deleteProduct(UUID id) {
        var coincidentClient = productDao.existsById(id);
        if (coincidentClient){
            productDao.deleteById(id);
        } else {
            throw new IllegalArgumentException("Product doesn't exists");
        }
    }
}
