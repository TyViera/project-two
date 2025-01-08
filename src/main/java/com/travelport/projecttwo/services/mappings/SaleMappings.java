package com.travelport.projecttwo.services.mappings;

import com.travelport.projecttwo.repository.entities.ClientEntity;
import com.travelport.projecttwo.repository.entities.ProductEntity;
import com.travelport.projecttwo.repository.entities.SaleEntity;
import com.travelport.projecttwo.services.domainModels.SaleDomain;

public class SaleMappings {

    public static SaleEntity toEntity(SaleDomain saleDomain) {
        var saleEntity = new SaleEntity();

        saleEntity.setId(saleDomain.getId());

        var clientEntity = new ClientEntity();
        clientEntity.setId(saleDomain.getClientId());
        saleEntity.setClient(clientEntity);

        var productEntity = new ProductEntity();
        productEntity.setId(saleDomain.getProductId());
        saleEntity.setProduct(productEntity);

        saleEntity.setQuantity(saleDomain.getQuantity());

        return saleEntity;
    }
}
