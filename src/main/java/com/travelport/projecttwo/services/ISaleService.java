package com.travelport.projecttwo.services;

import com.travelport.projecttwo.controllers.dtos.sale.SaleRequestDto;

public interface ISaleService {

    void createSale(SaleRequestDto saleRequest);
}
