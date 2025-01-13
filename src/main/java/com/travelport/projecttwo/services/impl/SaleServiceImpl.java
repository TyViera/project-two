package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.entities.*;
import com.travelport.projecttwo.model.PurchaseProduct;
import com.travelport.projecttwo.model.Sale;
import com.travelport.projecttwo.repository.SaleDao;
import com.travelport.projecttwo.services.SaleService;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleDao saleDao;

    public SaleServiceImpl(SaleDao saleDao) {
        this.saleDao = saleDao;
    }

    @Override
    public Sale addSale(Sale sale) {
        SaleEntity saleEntity = new SaleEntity();
        saleEntity.setId(sale.getId());
        saleEntity.setClientId(sale.getClientId());
        saleDao.addSale(saleEntity);

        for (PurchaseProduct product : sale.getProducts()) {
            SaleProductEntity saleProductEntity = new SaleProductEntity();
            SaleProductId saleProductId = new SaleProductId(sale.getId(), product.getProductId());
            saleProductEntity.setId(saleProductId);
            saleProductEntity.setQuantity(product.getQuantity());
            saleDao.addSaleProduct(saleProductEntity);
        }
        return sale;
    }
}
