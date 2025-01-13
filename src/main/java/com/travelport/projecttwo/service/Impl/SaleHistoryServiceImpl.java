package com.travelport.projecttwo.service.Impl;

import com.travelport.projecttwo.model.Sale;
import com.travelport.projecttwo.model.Response.SaleHistoryResponse;
import com.travelport.projecttwo.repository.SaleRepository;
import com.travelport.projecttwo.service.SaleHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleHistoryServiceImpl implements SaleHistoryService {

    private final SaleRepository saleRepository;

    public SaleHistoryServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<SaleHistoryResponse> getSalesHistoryByClientId(String clientId) {

        List<Sale> sales = saleRepository.findByClientId(clientId);

        return sales.stream()
                .map(sale -> {
                    SaleHistoryResponse response = new SaleHistoryResponse();
                    response.setId(sale.getId());

                    SaleHistoryResponse.ProductInfo productInfo = new SaleHistoryResponse.ProductInfo();
                    productInfo.setProductId(sale.getProduct().getId());
                    productInfo.setName(sale.getProduct().getName());
                    response.setProducts(List.of(productInfo));
                    response.setQuantity(sale.getQuantity());
                    return response;
                })
                .collect(Collectors.toList());
    }
}