package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.controllers.dtos.sale.MostSoldProductsDto;
import com.travelport.projecttwo.controllers.dtos.sale.ProductInMostSoldProducts;
import com.travelport.projecttwo.controllers.dtos.sale.SaleRequestDto;
import com.travelport.projecttwo.repository.IClientRepository;
import com.travelport.projecttwo.repository.IProductRepository;
import com.travelport.projecttwo.repository.ISalesCabRepository;
import com.travelport.projecttwo.repository.ISalesDetRepository;
import com.travelport.projecttwo.services.ISaleService;
import com.travelport.projecttwo.services.domainModels.SaleCabDomain;
import com.travelport.projecttwo.services.domainModels.SaleDetDomain;
import com.travelport.projecttwo.services.mappings.SaleMappings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SaleServiceImpl implements ISaleService {

    private final IProductRepository productRepository;
    private final IClientRepository clientRepository;
    private final ISalesCabRepository salesCabRepository;
    private final ISalesDetRepository salesDetRepository;

    public SaleServiceImpl(IProductRepository productRepository, IClientRepository clientRepository, ISalesCabRepository salesCabRepository, ISalesDetRepository salesDetRepository) {
        this.productRepository = productRepository;
        this.clientRepository = clientRepository;
        this.salesCabRepository = salesCabRepository;
        this.salesDetRepository = salesDetRepository;
    }

    @Override
    @Transactional
    public void createSale(SaleRequestDto saleRequest) {

        var saleCabDomain = SaleMappings.toDomain(saleRequest);

        var clientOpt = clientRepository.findById(saleCabDomain.getClientId());
        if (clientOpt.isEmpty()) {
            throw new IllegalArgumentException("Client not found");
        }

        for (SaleDetDomain detail : saleCabDomain.getDetails()) {
            var productOpt = productRepository.findById(detail.getId());
            if (productOpt.isEmpty()) {
                throw new IllegalArgumentException("Product not found");
            }

            var product = productOpt.get();

            if (product.getStock() < detail.getQuantity()) {
                throw new IllegalArgumentException("Product out of stock");
            }

            product.setStock(product.getStock() - detail.getQuantity());
            productRepository.save(product);
        }

        saleCabDomain.setId(UUID.randomUUID().toString());

        var saleCabEntity = SaleMappings.toEntity(saleCabDomain);

        salesCabRepository.save(saleCabEntity);

    }

    @Override
    public List<MostSoldProductsDto> getMostSoldProducts() {
        var top5SoldProducts = salesDetRepository.findMostSoldProducts();

        return top5SoldProducts.stream()
                .map(row -> {
                    var id = (String) row[0];
                    var name = (String) row[1];
                    var quantity = ((Number) row[2]).intValue() ;

                    var product = new ProductInMostSoldProducts(id, name);
                    var mostSoldProductsDto = new MostSoldProductsDto();
                    mostSoldProductsDto.setProduct(product);
                    mostSoldProductsDto.setQuantity(quantity);

                    return mostSoldProductsDto;
                }).toList();

    }
}
