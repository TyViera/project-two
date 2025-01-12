package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.entities.ProductEntity;
import com.travelport.projecttwo.repository.ProductDao;
import com.travelport.projecttwo.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<ProductEntity> getProducts() {
        return productDao.getProducts();
    }

    @Override
    public Optional<ProductEntity> getProductById(String id) {
        return productDao.getProduct(id);
    }

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        productDao.createProduct(product);
        return product;
    }

    @Override
    public Optional<ProductEntity> updateProduct(String id, ProductEntity product) {
        return productDao.updateProduct(id, product);
    }

    @Override
    public void deleteProduct(String id) throws NullPointerException, IllegalArgumentException {
        int salesCount = productDao.getAssociatedSalesCount(id);
        if (salesCount > 0) {
            throw new IllegalStateException("Product cannot be deleted as it has been sold.");
        }
        int rowsDeleted = productDao.deleteProduct(id);
        if (rowsDeleted == 0) {
            throw new NullPointerException("Product not found.");
        }
    }
}
