package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.service.ClientService;
import com.travelport.projecttwo.model.Client;
import com.travelport.projecttwo.model.dto.Error;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clients")
@Tag(name = "clients", description = "the clients API")
public class ClientController {

    private final ClientService clientService;
    public ClientController(ClientService clientService) { this.clientService = clientService; }

    @GetMapping
    @Operation (
            tags = "clients",
            description = "List all clients",
            operationId = "getAllClients"
    )
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    @Operation (
            tags = "clients",
            description = "Get a client by id",
            operationId = "getClientById",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The client id",
                            in = ParameterIn.PATH,
                            schema = @Schema(implementation = UUID.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Client not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Error.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Client> getClientById(@PathVariable UUID id) {
        Optional<Client> client = clientService.getClientByID(id);
        if(client.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(client.get());
        }
    }

    @PostMapping
    @Operation (
            tags = "clients",
            description = "Create a new Client",
            operationId = "createClient",
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Client.class)
                            )
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Client Created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Client.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(client));
    }

    @PutMapping("/{id}")
    @Operation (
            tags = "clients",
            description = "Update a Client",
            operationId = "updateClient",
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Client.class)
                            )
                    }),
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description =  "Client Updated",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Client.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Client> updateClient(@PathVariable UUID id, @RequestBody Client client) {
        var searchedClient = clientService.getClientByID(id);
        if (searchedClient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientService.updateClient(id, client).get());
    }

    @DeleteMapping("/{id}")
    @Operation (
            tags = "clients",
            description = "Delete Client by id",
            operationId = "deleteClient",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The client id",
                            in = ParameterIn.PATH,
                            schema = @Schema(implementation = UUID.class)
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Client not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = Error.class)
                                    )
                            }
                    )
            }
    )
    public ResponseEntity<Client> deleteClient(@PathVariable UUID id) {
        Optional<Client> searchedClient = clientService.getClientByID(id);
        if (searchedClient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        clientService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
