package com.travelport.projecttwo.controllers.mappings;

import com.travelport.projecttwo.controllers.dtos.ClientResponseDto;
import com.travelport.projecttwo.services.domainModels.ClientDomain;

import java.util.List;

public class ClientMappings {

    public static ClientResponseDto toDto(ClientDomain client) {
        var clientDto = new ClientResponseDto();

        clientDto.setNif(client.getNif());
        clientDto.setName(client.getName());
        clientDto.setAddress(client.getAddress());

        return clientDto;
    }

    public static List<ClientResponseDto> toDto(List<ClientDomain> clients) {
        List<ClientResponseDto> clientDtoList = new java.util.ArrayList<>();

        clients.forEach(client -> {
            var clientDto = new ClientResponseDto();

            clientDto.setNif(client.getNif());
            clientDto.setName(client.getName());
            clientDto.setAddress(client.getAddress());

            clientDtoList.add(clientDto);
        });

        return clientDtoList;
    }
}
