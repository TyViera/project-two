package com.travelport.projecttwo.service.Impl;

import com.travelport.projecttwo.model.Response.MostSoldProductResponse;
import com.travelport.projecttwo.repository.SaleRepository;
import com.travelport.projecttwo.model.Product;
import com.travelport.projecttwo.service.IncomeReportService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeReportServiceImpl implements IncomeReportService {

    private final SaleRepository saleRepository;

    public IncomeReportServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<MostSoldProductResponse> getMostSoldProducts() {
        List<Object[]> results = saleRepository.findTop5MostSoldProducts();
        List<MostSoldProductResponse> response = new ArrayList<>();

        for (Object[] result : results) {
            Product product = (Product) result[0];
            long totalQuantity = (long) result[1];

            MostSoldProductResponse responseItem = new MostSoldProductResponse();
            MostSoldProductResponse.ProductInfo productInfo = new MostSoldProductResponse.ProductInfo();
            productInfo.setId(product.getId());
            productInfo.setName(product.getName());

            responseItem.setProduct(productInfo);
            responseItem.setQuantity(totalQuantity);

            response.add(responseItem);
        }

        return response;
    }
}
