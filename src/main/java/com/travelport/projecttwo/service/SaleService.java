package com.travelport.projecttwo.service;

import com.travelport.projecttwo.dto.SaleRequest;
import com.travelport.projecttwo.entity.Sale;
import com.travelport.projecttwo.entity.SaleItem;
import com.travelport.projecttwo.entity.Client;
import com.travelport.projecttwo.entity.Product;
import com.travelport.projecttwo.repository.SaleRepository;
import com.travelport.projecttwo.repository.ClientRepository;
import com.travelport.projecttwo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository, ClientRepository clientRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
    }

    public void sellProduct(SaleRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        if (product.getStock() < request.getQuantity()) {
            throw new IllegalArgumentException("Insufficient stock for product");
        }

        product.setStock(product.getStock() - request.getQuantity());
        Sale sale = new Sale();
        sale.setClient(client);

        SaleItem saleItem = new SaleItem();
        saleItem.setProduct(product);
        saleItem.setQuantity(request.getQuantity());
        saleItem.setSale(sale);

        sale.setItems(List.of(saleItem));
        saleRepository.save(sale);
    }
}
