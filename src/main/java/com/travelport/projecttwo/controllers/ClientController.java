package com.travelport.projecttwo.controllers;

import com.travelport.projecttwo.controllers.dtos.ClientResponseDto;
import com.travelport.projecttwo.repository.entities.ClientEntity;
import com.travelport.projecttwo.services.IClientService;
import com.travelport.projecttwo.services.domainModels.ClientDomain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientResponseDto> getClients() {
        List<ClientDomain> clientDomainList = clientService.getAllClients();
        List<ClientResponseDto> clientDtoList = new java.util.ArrayList<>();

        clientDomainList.forEach(clientDomain -> {
            var clientDto = new ClientResponseDto();

            clientDto.setNif(clientDomain.getNif());
            clientDto.setName(clientDomain.getName());
            clientDto.setAddress(clientDomain.getAddress());

            clientDtoList.add(clientDto);
        });

        return clientDtoList;
    }
}
