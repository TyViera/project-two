package com.travelport.projecttwo.controller;

import com.travelport.projecttwo.dto.SaleResponse;
import com.travelport.projecttwo.entities.Client;
import com.travelport.projecttwo.service.ClientService;
import com.travelport.projecttwo.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Clients")
@RestController//TODO: maybe this could be only @Controller
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final SaleService saleService;

    public ClientController(ClientService clientService, SaleService saleService) {
        this.clientService = clientService;
        this.saleService=saleService;
    }

    @Operation(summary = "Get clients", description = "Returns all clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved",
                    content = @Content(
                            schema = @Schema(implementation = Client.class))), //TODO: hacer que retorne un array de Clients en la documentacion
    })
    @GetMapping
    public ResponseEntity<List<Client>> getClients() {
        return new ResponseEntity<>(clientService.getClients(), HttpStatus.OK);
    }

    @Operation(summary = "Find client", description = "Finds client by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful", content = @Content(
                    schema = @Schema(implementation = Client.class))),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content())
    })
   @GetMapping("/{id}")
    public ResponseEntity<Object> findById( @Parameter(name = "id") @PathVariable("id") String id){
        Client client;
        try{
            client = clientService.getClientById(id);

        }catch(EntityNotFoundException e) {
            return new ResponseEntity<>(
                    "Client not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                client,
                HttpStatus.OK
        );
    }

    @Operation(summary = "Add client", description = "Adds client given its data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "406", description = "Client not found")
    })
    @PostMapping
    public ResponseEntity<Object> postClient(@RequestBody Client client){
        try {
            Client newClient=clientService.addClient(client);
            return new ResponseEntity<>(
                    newClient,
                    HttpStatus.CREATED
            );
        }catch(Exception e){
            return new ResponseEntity<>(
                    "Client not saved",
                    HttpStatus.NOT_ACCEPTABLE
            );
        }
    }

    @Operation(summary = "Update client", description = "Updates client by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateClientById(@PathVariable("id") String id, @RequestBody Client client){
        try {
            Client clientUpdated=clientService.updateClient(id, client);
            return new ResponseEntity<>(
                    clientUpdated,
                    HttpStatus.OK
            );
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(
                    "Client not found",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Operation(summary = "Delete client", description = "Deletes client by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "422", description = "Sales in the system")

    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClientById(@PathVariable("id") String id){
        try{
            clientService.deleteClient(id);
            return new ResponseEntity<>("Operation successful", HttpStatus.OK);
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>("Sales in the system", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Operation(summary = "Get client sales", description = "Gets sales from client by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @GetMapping("/{id}/sales") //TODO: comprobar que estos sean lo errores que retorna
    public ResponseEntity<Object> getClientSales(@PathVariable("id") String id){
        try{
            List<SaleResponse> sales =saleService.getSalesByClientId(id);
            return new ResponseEntity<>(sales, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
