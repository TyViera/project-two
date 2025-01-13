package com.travelport.projecttwo.service.Impl;

import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.model.Client;
import com.travelport.projecttwo.model.Sale;
import com.travelport.projecttwo.model.request.SaleInput;
import com.travelport.projecttwo.repository.ProductRepository;
import com.travelport.projecttwo.repository.SaleRepository;
import com.travelport.projecttwo.service.SaleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;

    public SaleServiceImpl(ProductRepository productRepository, SaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public void sellProduct(SaleInput saleInput) {
        Optional<Product> productOpt = productRepository.findById(saleInput.getProduct().getId());
        if (productOpt.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = productOpt.get();

        if (product.getStock() < saleInput.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock");
        }

        product.setStock(product.getStock() - saleInput.getQuantity());
        productRepository.save(product);

        Sale sale = new Sale();
        sale.setProduct(product);

        Client client = new Client();
        client.setId(saleInput.getClient().getId());
        sale.setClient(client);

        sale.setQuantity(saleInput.getQuantity());
        saleRepository.save(sale);
    }
}