package com.travelport.projecttwo.services.impl;

import com.travelport.projecttwo.controllers.dtos.past_sales.ClientPastSalesDto;
import com.travelport.projecttwo.controllers.dtos.past_sales.ProductInPastSalesDto;
import com.travelport.projecttwo.controllers.dtos.past_sales.ProductsBoughtByClientDto;
import com.travelport.projecttwo.repository.IClientRepository;
import com.travelport.projecttwo.repository.ISaleRepository;
import com.travelport.projecttwo.repository.entities.ClientEntity;
import com.travelport.projecttwo.services.IClientService;
import com.travelport.projecttwo.services.domainModels.ClientDomain;
import com.travelport.projecttwo.services.mappings.ClientMappings;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements IClientService {

    private final IClientRepository clientRepository;
    private final ISaleRepository saleRepository;

    public ClientServiceImpl(IClientRepository clientRepository, ISaleRepository saleRepository) {
        this.clientRepository = clientRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public List<ClientDomain> getClients() {
        List<ClientEntity> clientEntityList = clientRepository.findAll();
        List<ClientDomain> clientDomainList = new ArrayList<>(List.of());

        clientEntityList.forEach(clientEntity -> {
            var clientDomain = ClientMappings.toDomain(clientEntity);
            clientDomainList.add(clientDomain);
        });

        return clientDomainList;
    }

    @Override
    public Optional<ClientDomain> getClientById(String id) {
        Optional<ClientEntity> clientEntity = clientRepository.findById(id);
        return clientEntity.map(ClientMappings::toDomain);
    }

    @Override
    public ClientDomain createClient(ClientDomain clientDomain) {
        clientRepository.save(ClientMappings.toEntity(clientDomain));
        return clientDomain;
    }

    @Override
    public ClientDomain updateClient(String id, ClientDomain clientDomain) {
        var findClient = clientRepository.findById(id);
        if (findClient.isEmpty()) {
            throw new IllegalArgumentException("Client not found");
        }
        findClient.get().setNif(clientDomain.getNif());
        findClient.get().setName(clientDomain.getName());
        findClient.get().setAddress(clientDomain.getAddress());
        clientRepository.save(findClient.get());
        return findClient.map(ClientMappings::toDomain).get();
    }

    @Override
    public void deleteClient(String id) {
        var findClient = clientRepository.findById(id);
        if (findClient.isEmpty()) {
            throw new IllegalArgumentException("Client not found");
        }
        clientRepository.delete(findClient.get());
    }

    @Override
    public List<ClientPastSalesDto> getClientSales(String clientId) {
        var client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            throw new IllegalArgumentException("Client not found");
        }

        var sales = saleRepository.findAllByClientId(clientId);

        return sales.stream().map(sale -> {
            List<ProductsBoughtByClientDto> products = List.of(
                    new ProductsBoughtByClientDto(
                            new ProductInPastSalesDto(sale.getProduct().getId(), sale.getProduct().getName()),
                            sale.getQuantity()
                    )
            );
            var dto = new ClientPastSalesDto();
            dto.setId(sale.getId());
            dto.setProducts(products);

            return dto;
        }).toList();
    }
}
