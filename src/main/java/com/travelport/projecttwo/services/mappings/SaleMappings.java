package com.travelport.projecttwo.services.mappings;

import com.travelport.projecttwo.controllers.dtos.sale.SaleRequestDto;
import com.travelport.projecttwo.repository.embeddables.SalesDetId;
import com.travelport.projecttwo.repository.entities.ClientEntity;
import com.travelport.projecttwo.repository.entities.ProductEntity;
import com.travelport.projecttwo.repository.entities.SaleCabEntity;
import com.travelport.projecttwo.repository.entities.SaleDetEntity;
import com.travelport.projecttwo.services.domainModels.SaleCabDomain;
import com.travelport.projecttwo.services.domainModels.SaleDetDomain;

import java.util.List;

public class SaleMappings {

    public static SaleCabDomain toDomain(SaleRequestDto dto) {

        var clientId = dto.getClient().getId();

        var details = dto.getProducts().stream()
                .map(p -> new SaleDetDomain(p.getId(), p.getQuantity()))
                .toList();

        var saleCab = new SaleCabDomain();
        saleCab.setClientId(clientId);
        saleCab.setDetails(details);

        return saleCab;
    }
    
    public static SaleCabEntity toEntity(SaleCabDomain domain) {
        var cabEntity = new SaleCabEntity();
        cabEntity.setId(domain.getId());
        cabEntity.setClientId(domain.getClientId());
        
        List<SaleDetEntity> detEntities = domain.getDetails().stream().map(detDomain -> {
            var detEntity = new SaleDetEntity();
            detEntity.setId(new SalesDetId(domain.getId(), detDomain.getId()));
            detEntity.setQuantity(detDomain.getQuantity());
            return detEntity;
        }).toList();


        cabEntity.setDetails(detEntities);

        detEntities.forEach(detEntity -> detEntity.setSaleCab(cabEntity));
        
        return cabEntity;
    }
}
