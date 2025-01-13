package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.entities.ClientEntity;
import com.travelport.projecttwo.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io. swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(
            operationId = "getAllClients",
            summary = "Get all clients",
            description = "Get a list of all clients stored in the database.",
            tags = {"clients"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of clients retrieved successfully")
            }
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<ClientEntity> getClients() {
        return clientService.getClients();
    }

    @Operation(
            operationId = "getClientById",
            summary = "Retrieve a client by ID",
            description = "Get details of a specific client by their ID.",
            tags = {"clients"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client found"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getClient(@PathVariable String id) {
        Optional<ClientEntity> client = clientService.getClientById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new client",
            description = "Create a new client entry",
            tags = {"clients"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully")
    })
    @PostMapping
    public ResponseEntity<ClientEntity> createClient(@RequestBody ClientEntity client) {
        ClientEntity createdClient = clientService.createClient(client);
        return ResponseEntity
                .status(HttpStatus.CREATED) // Return 201 Created
                .body(createdClient);
    }

    @Operation(
            operationId = "updateClient",
            summary = "Update an existing client",
            description = "Update the details of an existing client by ID.",
            tags = {"clients"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Client not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<ClientEntity> updateClient(@PathVariable String id, @RequestBody ClientEntity client) {
        Optional<ClientEntity> updatedClient = clientService.updateClient(id, client);
        return updatedClient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            operationId = "deleteClient",
            summary = "Delete a client by ID",
            description = "Delete a client by ID. Returns 404 if not found, 422 if the client has associated sales.",
            tags = {"clients"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Client deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Client not found"),
                    @ApiResponse(responseCode = "422", description = "Client has associated sales")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable String id) {
        try {
            clientService.deleteClient(id);
        } catch (NullPointerException e) { // 404  if client not found
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) { // 422 if client has sales
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok().build();
    }
}
