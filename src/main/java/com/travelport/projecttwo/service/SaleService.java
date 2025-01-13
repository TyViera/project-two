package com.travelport.projecttwo.service;

import com.travelport.projecttwo.entities.Product;
import com.travelport.projecttwo.entities.Sale;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public List<Sale> findByClientId(String clientId) {
        return saleRepository.findByClientId(clientId);
    }

    public Sale save(Sale sale) {
        Optional<Product> productOptional = productRepository.findById(sale.getProduct().getId());
        if (productOptional.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = productOptional.get();
        if (product.getStock() < sale.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
        }

        product.setStock(product.getStock() - sale.getQuantity());
        productRepository.save(product);

        return saleRepository.save(sale);
    }
}
