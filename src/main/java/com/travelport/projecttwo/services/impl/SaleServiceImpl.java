package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.controllers.dtos.sale.SaleRequestDto;
import com.travelport.projecttwo.repository.IClientRepository;
import com.travelport.projecttwo.repository.IProductRepository;
import com.travelport.projecttwo.repository.ISaleRepository;
import com.travelport.projecttwo.services.ISaleService;
import com.travelport.projecttwo.services.domainModels.SaleDomain;
import com.travelport.projecttwo.services.mappings.SaleMappings;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SaleServiceImpl implements ISaleService {

    private final IProductRepository productRepository;
    private final IClientRepository clientRepository;
    private final ISaleRepository saleRepository;

    public SaleServiceImpl(IProductRepository productRepository, IClientRepository clientRepository, ISaleRepository saleRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public void createSale(SaleRequestDto saleRequest) {
        var product = productRepository.findById(saleRequest.getProduct().getId());

        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        var client = clientRepository.findById(saleRequest.getClient().getId());

        if (client.isEmpty()) {
            throw new IllegalArgumentException("Client not found");
        }

        if (product.get().getStock() < saleRequest.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock");
        }

        product.get().setStock(product.get().getStock() - saleRequest.getQuantity());

        productRepository.save(product.get());

        var sale = new SaleDomain();
        sale.setId(UUID.randomUUID().toString());
        sale.setProductId(product.get().getId());
        sale.setClientId(client.get().getId());
        sale.setQuantity(saleRequest.getQuantity());

        saleRepository.save(SaleMappings.toEntity(sale));

    }
}
