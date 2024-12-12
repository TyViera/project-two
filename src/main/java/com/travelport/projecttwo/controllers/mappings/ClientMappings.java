package com.travelport.projecttwo.controllers.mappings;

import com.travelport.projecttwo.controllers.dtos.ClientResponseDto;

public class ClientMappings {

    public static ClientResponseDto toDto(ClientResponseDto client) {
        var clientDto = new ClientResponseDto();

        clientDto.setNif(client.getNif());
        clientDto.setName(client.getName());
        clientDto.setAddress(client.getAddress());

        return clientDto;
    }
}
