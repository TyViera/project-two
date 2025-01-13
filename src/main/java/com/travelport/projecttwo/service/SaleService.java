package com.travelport.projecttwo.service;

import com.travelport.projecttwo.dto.ProductReportResponse;
import com.travelport.projecttwo.dto.SaleRequest;
import com.travelport.projecttwo.dto.SaleResponse;

import java.util.List;

public interface SaleService {
    List<ProductReportResponse> getMostSoldProducts();

    void addSale(SaleRequest saleRequest);

    List<SaleResponse> getSalesByClientId(String id);
}

