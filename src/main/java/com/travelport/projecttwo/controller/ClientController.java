package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.controller.model.ClientRequest;
import com.travelport.projecttwo.dto.ErrorResponse;
import com.travelport.projecttwo.exception.ClientNotFoundException;
import com.travelport.projecttwo.exception.DeletingClientException;
import com.travelport.projecttwo.repository.entity.ClientEntity;
import com.travelport.projecttwo.service.ClientService;
import com.travelport.projecttwo.service.SaleService;
import com.travelport.projecttwo.service.model.PastSaleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@Tag(name = "clients", description = "Operations about clients")
public class ClientController {

  private final ClientService clientService;
  private final SaleService saleService;

  public ClientController(ClientService clientService, SaleService saleService) {
    this.clientService = clientService;
    this.saleService = saleService;
  }

  @Operation(
      operationId = "getClients",
      summary = "Get all clients",
      description = "Get all clients in a List",
      tags = { "clients" },
      responses = {
          @ApiResponse(responseCode = "200", description = "successful operation - clients list", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ClientEntity.class)))
          })
      }
  )
  @GetMapping
  public ResponseEntity<List<ClientEntity>> getAll () {
    var clientList = clientService.getAll();
    return ResponseEntity.ok(clientList);
  }

  @Operation(
      operationId = "createClient",
      summary = "Create a client",
      description = "",
      tags = { "clients" },
      responses = {
          @ApiResponse(responseCode = "201", description = "successful operation", content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = ClientEntity.class))
          })
      }
  )
  @PostMapping
  public ResponseEntity<ClientEntity> save (@RequestBody ClientRequest inputClient) {
    var createdClient = clientService.save(inputClient);
    return ResponseEntity.status(201).body(createdClient);
  }

  @Operation(
      operationId = "getClientById",
      summary = "Get client by id",
      description = "Get one client by id",
      tags = { "clients" },
      responses = {
          @ApiResponse(responseCode = "200", description = "successful operation", content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = ClientEntity.class))
          }),
          @ApiResponse(responseCode = "404", description = "Client not found")
      }
  )
  @GetMapping("/{id}")
  public ResponseEntity<ClientEntity> getById (@PathVariable("id") String id) {
    var foundClient = clientService.getById(id);
    if (foundClient.isEmpty()) return ResponseEntity.notFound().build();

    return ResponseEntity.ok(foundClient.get());
  }

  @Operation(
      operationId = "updateClient",
      summary = "Update client",
      description = "Update a client by id",
      tags = { "clients" },
      responses = {
          @ApiResponse(responseCode = "200", description = "successful operation - returns the upated client", content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = ClientEntity.class))
          }),
          @ApiResponse(responseCode = "404", description = "client not found")
      }
  )
  @PutMapping("/{id}")
  public ResponseEntity<ClientEntity> updateById (@PathVariable("id") String id, @RequestBody ClientRequest inputClient) {
    var updatedClient = clientService.updateById(id, inputClient);
    if (updatedClient.isEmpty()) return ResponseEntity.notFound().build();

    return ResponseEntity.ok(updatedClient.get());
  }

  @Operation(
      operationId = "deleteClient",
      summary = "Delete client",
      description = "Delete a client by id",
      tags = { "clients" },
      responses = {
          @ApiResponse(responseCode = "200", description = "successful operation"),
          @ApiResponse(responseCode = "404", description = "Client not found"),
          @ApiResponse(responseCode = "422", description = "Client has orders, cannot be deleted")
      }
  )
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteById (@PathVariable("id") String id) {
    try {
      var isClientDeleted = clientService.deleteById(id);
      if (!isClientDeleted) return ResponseEntity.notFound().build();
      return ResponseEntity.ok().build();
    } catch (DeletingClientException e) {
      return ResponseEntity.status(422).body(new ErrorResponse(e.getMessage()));
    }
  }

  @Operation(
      operationId = "getClientPastSales",
      summary = "See past sales",
      description = "This operation must be protected with basic auth.",
      tags = { "clients" },
      responses = {
          @ApiResponse(responseCode = "200", description = "list of past sales of the given client", content = {
              @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PastSaleResponse.class)))
          })
      },
      security = {
          @SecurityRequirement(name = "basicAuth")
      }
  )
  @GetMapping("/{id}/sales")
  public ResponseEntity<?> getSalesByClientId (@PathVariable("id") String id) {
    try {
      var sales = saleService.getSalesByClientId(id);
      return ResponseEntity.ok(sales);
    } catch (ClientNotFoundException e) {
      return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage()));
    }
  }

}
